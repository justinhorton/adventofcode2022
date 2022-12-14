package xyz.justinhorton.aoc2022

/**
 * https://adventofcode.com/2022/day/14
 */
class Day14(override val input: String) : Day() {
    override fun part1(): String {
        val blockedPoints = initBlockedPoints()
        return dropSand(1, blockedPoints, blockedPoints.maxOf { it.y }).toString()
    }

    override fun part2(): String {
        val blockedPoints = initBlockedPoints()
        return dropSand(2, blockedPoints, blockedPoints.maxOf { it.y }).toString()
    }

    private fun initBlockedPoints(): MutableSet<GPoint> {
        return input.trim()
            .lineSequence()
            .flatMap { line ->
                line.split(" -> ")
                    .map { it.split(",").let { csv -> GPoint(csv[0].toInt(), csv[1].toInt()) } }
                    .zipWithNext()
            }
            .flatMap { sequenceOf(it.first, it.second) + pointsOnLine(it.first, it.second) }
            .toMutableSet()
    }

    private fun dropSand(part: Int, blockedPoints: MutableSet<GPoint>, maxRockY: Int): Int {
        var sandCount = 0
        var sandLoc = sandStart

        val continueDropping = {
            if (part == 1) {
                sandLoc.y < maxRockY
            } else {
                sandStart !in blockedPoints
            }
        }

        while (continueDropping()) {
            val candidates = listOf(
                sandLoc.copy(y = sandLoc.y + 1),
                GPoint(sandLoc.x - 1, sandLoc.y + 1),
                GPoint(sandLoc.x + 1, sandLoc.y + 1)
            )

            val firstOpen = if (part == 1) {
                candidates
            } else {
                candidates.filter { it.y <= maxRockY + 1 }
            }.firstOrNull { it !in blockedPoints }

            sandLoc = if (firstOpen == null) {
                blockedPoints.add(sandLoc)
                sandCount++
                sandStart
            } else {
                firstOpen
            }
        }

        return sandCount
    }

    private fun pointsOnLine(p1: GPoint, p2: GPoint): Sequence<GPoint> {
        return sequence {
            if (p1.x == p2.x) {
                val (lowy, highy) = if (p1.y < p2.y) {
                    p1.y to p2.y
                } else {
                    p2.y to p1.y
                }

                for (y in (lowy + 1) until highy) {
                    yield(GPoint(p1.x, y))
                }
            } else {
                val (lowx, highx) = if (p1.x < p2.x) {
                    p1.x to p2.x
                } else {
                    p2.x to p1.x
                }

                for (x in (lowx + 1) until highx) {
                    yield(GPoint(x, p1.y))
                }
            }
        }
    }
}

private val sandStart = GPoint(500, 0)
