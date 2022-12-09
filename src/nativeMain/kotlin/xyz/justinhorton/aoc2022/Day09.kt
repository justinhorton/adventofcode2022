package xyz.justinhorton.aoc2022

import kotlin.math.abs
/**
 * https://adventofcode.com/2022/day/9
 */
class Day09(override val input: String) : Day() {
    override fun part1(): String {
        var headPos = Point(0, 0)
        var tailPos = Point(0, 0)
        val seenTailPos = mutableSetOf<Point>().apply { add(tailPos) }

        input.trim()
            .lines()
            .forEach { line ->
                val split = line.split(" ")
                val delta = split[1].toInt()
                val dir = split[0]

                repeat(delta) {
                    headPos = computeUpdatedHead(headPos, dir)
                    tailPos = computeUpdatedTail(headPos, tailPos)
                    seenTailPos.add(tailPos)
                }
            }

        return seenTailPos.size.toString()
    }

    override fun part2(): String {
        val knots = (0..9).associateWithTo(mutableMapOf()) { Point(0, 0) }
        val seenTailPos = mutableSetOf<Point>().apply { add(Point(0, 0)) }

        input.trim()
            .lines()
            .forEach { line ->
                val split = line.split(" ")
                val delta = split[1].toInt()
                val dir = split[0]

                repeat(delta) {
                    knots[0] = computeUpdatedHead(knots.getValue(0), dir)
                    for (i in 1..9) {
                        knots[i] = computeUpdatedTail(knots.getValue(i - 1), knots.getValue(i))
                    }
                    seenTailPos.add(knots.getValue(9))
                }
            }

        return seenTailPos.size.toString()
    }

    private fun computeUpdatedHead(headPos: Point, dir: String) = headPos.copy(
        x = headPos.x + when (dir) {
            "L" -> -1
            "R" -> 1
            else -> 0
        },
        y = headPos.y + when (dir) {
            "D" -> -1
            "U" -> 1
            else -> 0
        }
    )

    private fun computeUpdatedTail(
        headPos: Point,
        tailPos: Point
    ): Point {
        // (0, 0) = bottom left = start
        val dx = headPos.xdiff(tailPos)
        val dy = headPos.ydiff(tailPos)

        return if (headPos == tailPos || (abs(dx) + abs(dy) == 1) || (abs(dx) == 1 && abs(dy) == 1)) {
            // "touching"
            tailPos
        } else if (abs(dx) == 2 && abs(dy) == 0 || abs(dy) == 2 && abs(dx) == 0) {
            // directly above, below, left, right (and not touching)
            tailPos.copy(
                x = tailPos.x + if (dx == 0) 0 else dx / abs(dx),
                y = tailPos.y + if (dy == 0) 0 else dy / abs(dy)
            )
        } else {
            // diagonal
            tailPos.copy(x = tailPos.x + dx / abs(dx), y = tailPos.y + dy / abs(dy))
        }
    }
}

private data class Point(val x: Int, val y: Int) {
    fun xdiff(other: Point) = this.x - other.x
    fun ydiff(other: Point) = this.y - other.y
}
