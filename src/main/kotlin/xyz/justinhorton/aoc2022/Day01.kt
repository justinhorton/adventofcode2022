package xyz.justinhorton.aoc2022

/**
 * https://adventofcode.com/2022/day/1
 */
class Day01(override val input: String) : Day() {
    override fun part1(): String {
        return input.trim()
            .split("\n\n")
            .maxOf { calsForElf(it) }
            .toString()
    }

    override fun part2(): String {
        return input.trim()
            .split("\n\n")
            .asSequence()
            .map { calsForElf(it) }
            .sortedDescending()
            .take(3)
            .sum()
            .toString()
    }

    private fun calsForElf(elfText: String) = elfText.split("\n").sumOf { line -> line.toInt() }
}
