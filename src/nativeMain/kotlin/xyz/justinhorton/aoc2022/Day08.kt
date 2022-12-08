package xyz.justinhorton.aoc2022

import platform.posix.abs

/**
 * https://adventofcode.com/2022/day/8
 */
class Day08(override val input: String) : Day() {
    override fun part1(): String {
        val grid = Grid.fromChars(input) { it.digitToInt() }
        return grid.enumerateCoordinates()
            .count { (x, y) -> isVisible(grid, x, y) }
            .toString()
    }

    override fun part2(): String {
        val grid = Grid.fromChars(input) { it.digitToInt() }
        return grid.enumerateCoordinates()
            .map { (x, y) -> scenicScore(grid, x, y) }
            .max()
            .toString()
    }

    private fun isVisible(grid: Grid<Int>, x: Int, y: Int): Boolean {
        val thisHeight = grid[x to y]
        return Direction.values().any { dir ->
            grid.adjacentCoordinates(x, y, dir)
                .all { (x1, y1) -> grid[x1 to y1] < thisHeight }
        }
    }

    private fun scenicScore(grid: Grid<Int>, x: Int, y: Int): Int {
        val thisHeight = grid[x to y]
        val visibleDistances = Direction.values().map { dir ->
            val coords = grid.adjacentCoordinates(x, y, dir)
            if (coords.none()) {
                0
            } else {
                val i = coords.indexOfFirst { (x1, y1) -> grid[x1 to y1] >= thisHeight }
                if (i == -1) {
                    // can see to edge; only one of x1 - x or y1 - y is nonzero as x or y is fixed
                    // the final pair of coordinates gives the max diff in this direction
                    coords.last().let { (x1, y1) -> abs(x1 - x + y1 - y) }
                } else {
                    i + 1
                }
            }
        }
        return visibleDistances.fold(1) { acc, v -> acc * v }
    }
}
