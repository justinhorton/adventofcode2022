package xyz.justinhorton.aoc2022

/**
 * https://adventofcode.com/2022/day/6
 */
class Day06(override val input: String) : Day() {
    override fun part1(): String {
        val str = input.trim()
        return str.windowedSequence(4, 1)
            .first { it.toCharArray().distinct().size == 4 }
            .let { (str.indexOf(it) + 4).toString() }
    }

    override fun part2(): String {
        val str = input.trim()
        return str.windowedSequence(14, 1)
            .first { it.toCharArray().distinct().size == 14 }
            .let { (str.indexOf(it) + 14).toString() }
    }
}
