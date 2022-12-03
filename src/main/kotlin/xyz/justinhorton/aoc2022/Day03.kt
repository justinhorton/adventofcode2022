package xyz.justinhorton.aoc2022

/**
 * https://adventofcode.com/2022/day/3
 */
class Day03(override val input: String) : Day() {
    override fun part1(): String {
        return input.trim()
            .split("\n")
            .asSequence()
            .map { it.chunked(it.length / 2) }
            .map { (half1, half2) ->
                val seen = Array(52) { false }
                half1.forEach { ch -> seen[ch.priority() - 1] = true }
                half2.first { ch -> seen[ch.priority() - 1] }
            }
            .sumOf { it.priority() }
            .toString()
    }

    override fun part2(): String {
        return input.trim()
            .split("\n")
            .asSequence()
            .chunked(3)
            .sumOf { group ->
                val freqs = Array(52) { 0 }

                group.forEach { str ->
                    val thisFreqs = Array(52) { 0 }
                    str.forEach { thisFreqs[it.priority() - 1]++ }
                    thisFreqs.withIndex().filter { it.value != 0 }.forEach { freqs[it.index]++ }
                }

                val foundIndex = freqs.withIndex().first { it.value == 3 }.index
                foundIndex + 1 // as priority
            }.toString()
    }

    private fun Char.priority(): Int {
        val isLower = isLowerCase()
        return if (isLower) code - 'a'.code + 1 else code - 'A'.code + 27
    }
}
