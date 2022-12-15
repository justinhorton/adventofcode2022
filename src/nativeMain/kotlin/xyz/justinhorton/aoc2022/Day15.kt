package xyz.justinhorton.aoc2022

import kotlin.math.abs

/**
 * https://adventofcode.com/2022/day/15
 */
class Day15(override val input: String) : Day() {
    override fun part1(): String {
        val fixedy = if (input.startsWith(SAMPLE_START)) 10 else 2_000_000

        val sd = parseSensorData()

        val lowx = sd.minBy { it.closestBeacon.x }.let { it.closestBeacon.x - it.manhattan }
        val highx = sd.maxBy { it.closestBeacon.x }.let { it.closestBeacon.x + it.manhattan }

        return (lowx..highx).count { x ->
            sd.any {
                val point = GPoint(x, fixedy)
                point != it.closestBeacon && it.isInManhattanTrapezoid(point)
            }
        }.toString()
    }

    override fun part2(): String {
        val upperBound = if (input.startsWith(SAMPLE_START)) 20 else 4_000_000

        val sd = parseSensorData()

        val beaconPos = sd.asSequence()
            .flatMap { it.trapBorder() }
            .filter { (x, y) -> x in 0..upperBound && y in 0..upperBound }
            .first { borderPos -> sd.none { it.isInManhattanTrapezoid(borderPos) } }

        return (beaconPos.x * TUNING_MULTIPLIER + beaconPos.y).toString()
    }

    private fun parseSensorData() = input.trim().lines()
        .map { line ->
            val coords = line.split(',', ':', '=')
                .mapNotNull { it.toIntOrNull() }
                .zipWithNext()
            SensorData(GPoint(coords.first()), GPoint(coords.last()))
        }
}

private data class SensorData(val pos: GPoint, val closestBeacon: GPoint) {
    val manhattan = manhattanDistance(pos, closestBeacon)

    fun trapBorder(): Sequence<GPoint> {
        val manPlusOne = manhattan + 1
        return sequence {
            yield(GPoint(pos.x, pos.y + manPlusOne))
            yield(GPoint(pos.x, pos.y - manPlusOne))

            var xoff = 1
            for (y in (pos.y - manPlusOne + 1)..(pos.y + manPlusOne - 1)) {
                yield(GPoint(pos.x + xoff, y))
                yield(GPoint(pos.x - xoff, y))
                if (y > pos.y) {
                    xoff++
                } else {
                    xoff--
                }
            }
        }
    }

    fun isInManhattanTrapezoid(other: GPoint): Boolean = manhattanDistance(pos, other) <= manhattan
}

private const val SAMPLE_START = "Sensor at x=2"
private const val TUNING_MULTIPLIER = 4_000_000L

private fun manhattanDistance(p1: GPoint, p2: GPoint): Int = abs(p1.x - p2.x) + abs(p1.y - p2.y)
