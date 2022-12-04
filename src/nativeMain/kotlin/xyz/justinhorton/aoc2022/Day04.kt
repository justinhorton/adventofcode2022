package xyz.justinhorton.aoc2022

/**
 * https://adventofcode.com/2022/day/4
 */
class Day04(override val input: String) : Day() {
    override fun part1(): String {
        return input.trim()
            .lines()
            .count { line ->
                val (e1Range, e2Range) = line.split(",")
                    .map { elf -> elf.split("-").map { it.toInt() } }
                    .map { (start, end) -> start..end }
                e1Range.first in e2Range && e1Range.last in e2Range ||
                    e2Range.first in e1Range && e2Range.last in e1Range
            }.toString()
    }

    override fun part2(): String {
        return input.trim()
            .lines()
            .count { line ->
                val (e1Range, e2Range) = line.split(",")
                    .map { elf -> elf.split("-").map { it.toInt() } }
                    .map { (start, end) -> start..end }
                e1Range.first in e2Range || e1Range.last in e2Range ||
                    e2Range.first in e1Range || e2Range.last in e1Range
            }.toString()
    }
}
