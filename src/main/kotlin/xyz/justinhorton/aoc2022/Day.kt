package xyz.justinhorton.aoc2022

sealed class Day {
    protected abstract val input: String

    abstract fun part1(): String

    abstract fun part2(): String
}
