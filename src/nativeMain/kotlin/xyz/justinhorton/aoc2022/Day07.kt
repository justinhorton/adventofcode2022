package xyz.justinhorton.aoc2022

import okio.Path
import okio.Path.Companion.toPath

/**
 * https://adventofcode.com/2022/day/7
 */
class Day07(override val input: String) : Day() {
    override fun part1(): String {
        val fileSystem = buildFileSystem()
        return fileSystem.keys
            .map { dir -> totalSize(dir, fileSystem) }
            .filter { it <= 100000 }
            .sum()
            .toString()
    }

    override fun part2(): String {
        val fileSystem = buildFileSystem()
        val spaceAvail = TOTAL_DRIVE_SPACE - totalSize("/".toPath(), fileSystem)
        return fileSystem.keys
            .map { dir -> totalSize(dir, fileSystem) }
            .filter { it >= FREE_SPACE_NEEDED - spaceAvail }
            .min()
            .toString()
    }

    private fun buildFileSystem(): MutableMap<Path, MutableList<FileMetadata>> {
        // keys are directories, values represent contained files/directories
        val fileMap = mutableMapOf<Path, MutableList<FileMetadata>>()
        val lines = input.trim().lines()

        var pwd: Path = "/".toPath()
        val firstCmd = lines[0].trim()
        require(firstCmd == "$ cd /") { "Unexpected first command: $firstCmd" }

        for (line in input.trim().lines().drop(1)) {
            val split = line.trim().split(" ")
            when (split[0]) {
                "$" -> {
                    when (val cmd = split[1]) {
                        "ls" -> continue // multi-line output follows
                        "cd" -> { // change pwd
                            val nextDir = split[2]
                            pwd = if (nextDir == "..") {
                                pwd.parent!!
                            } else {
                                pwd.resolve(nextDir)
                            }
                        }
                        else -> throw IllegalArgumentException("Unknown command: $cmd")
                    }
                }
                else -> { // multi-line `ls` output
                    val lsDir = fileMap.getOrPut(pwd) { mutableListOf() }
                    val lsSplit = line.trim().split(" ")
                    if (lsSplit[0] == "dir") {
                        lsDir.add(DirectoryMetadata(pwd.resolve(lsSplit[1])))
                    } else {
                        lsDir.add(SimpleFileMetadata(lsSplit[1], lsSplit[0].toLong()))
                    }
                }
            }
        }

        return fileMap
    }
}

private const val FREE_SPACE_NEEDED = 30000000L
private const val TOTAL_DRIVE_SPACE = 70000000L

private fun totalSize(initialDir: Path, fs: Map<Path, List<FileMetadata>>): Long {
    return fs.getValue(initialDir).sumOf { f ->
        when (f) {
            is SimpleFileMetadata -> f.size
            is DirectoryMetadata -> totalSize(f.path, fs)
        }
    }
}

private sealed class FileMetadata
private data class SimpleFileMetadata(val name: String, val size: Long) : FileMetadata()
private data class DirectoryMetadata(val path: Path) : FileMetadata()
