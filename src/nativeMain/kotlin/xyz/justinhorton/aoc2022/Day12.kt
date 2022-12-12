package xyz.justinhorton.aoc2022

import kotlin.math.min

/**
 * https://adventofcode.com/2022/day/11
 */
class Day12(override val input: String) : Day() {
    override fun part1(): String {
        val grid = Grid.fromChars(input) { it }
        val start = grid.enumeratePoints().first { grid[it] == 'S' }
        val goal = grid.enumeratePoints().first { grid[it] == 'E' }

        val parents = bfs(start, goal, grid)
        val path = buildPath(start, goal, parents)

        return numSteps(path).toString()
    }

    override fun part2(): String {
        val grid = Grid.fromChars(input) { it }
        val goal = grid.enumeratePoints().first { grid[it] == 'E' }
        val possibleStarts = grid.enumeratePoints().filter { grid[it] == 'a' }

        var minPathLen = part1().toInt() // starting from 'S'
        for (ps in possibleStarts) {
            val parents = bfs(ps, goal, grid)
            val path = buildPath(ps, goal, parents)

            if (path.isNotEmpty()) {
                minPathLen = min(minPathLen, numSteps(path))
            }
        }

        return minPathLen.toString()
    }

    private fun bfs(
        start: GPoint,
        goal: GPoint,
        grid: Grid<Char>
    ): Map<GPoint, GPoint> {
        val visited = mutableSetOf<GPoint>().apply { add(start) }
        val q = mutableListOf<GPoint>().apply { add(start) }
        val parents = mutableMapOf<GPoint, GPoint>()

        while (q.isNotEmpty()) {
            val next = q.removeFirst()
            if (next == goal) {
                return parents
            }

            val nextCh = grid[next].let { if (it == 'S') 'a' else it }
            val isAllowedTransition: (GPoint) -> Boolean = {
                (grid[it].let { ch -> if (ch == 'E') 'z' else ch } - nextCh) <= 1
            }

            for (a in grid.adjacentPoints(next).filter(isAllowedTransition).filterNot { it in visited }) {
                visited += a
                parents[a] = next
                q += a
            }
        }

        return emptyMap()
    }

    private fun buildPath(start: GPoint, goal: GPoint, parents: Map<GPoint, GPoint>): List<GPoint> {
        var p: GPoint? = goal
        val path = mutableListOf<GPoint>()

        while (p != null) {
            path += p
            p = parents[p]
        }

        return if (path.last() != start) { // not a complete path
            emptyList()
        } else {
            path.reversed()
        }
    }

    private fun numSteps(path: List<GPoint>) = path.size - 1
}
