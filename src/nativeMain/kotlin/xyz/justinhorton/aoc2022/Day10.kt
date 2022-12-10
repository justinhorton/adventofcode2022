package xyz.justinhorton.aoc2022

private typealias Cycle = Int
private typealias Operand = Int

/**
 * https://adventofcode.com/2022/day/10
 */
class Day10(override val input: String) : Day() {
    override fun part1(): String {
        val cycleToAddOperand = cycleToAddOperand()
        var regx = 1
        var sum = 0

        for (c in 1..INTERESTING_CYCLES.last()) {
            if (c in INTERESTING_CYCLES) {
                sum += regx * c
            }
            cycleToAddOperand[c]?.let { regx += it }
        }

        return sum.toString()
    }

    override fun part2(): String {
        val crt = Array(6) { CharArray(40) }
        var x = 0
        var y = 0

        val cycleToAddOperand = cycleToAddOperand()
        var regx = 1

        for (c in 1..240) {
            crt[y][x] = if (x in (regx - 1)..(regx + 1)) {
                LIT_PIXEL
            } else {
                DARK_PIXEL
            }

            x = (x + 1) % 40
            y = if (x == 0) y + 1 else y
            cycleToAddOperand[c]?.let { regx += it }
        }

        return "\n" + crt.joinToString(separator = "\n") { it.concatToString() }
    }

    /**
     * Return a map where the cycle to operand pairing `c : o` indicates that _after_ cycle `c`, `o` is added to the
     * register.
     */
    private fun cycleToAddOperand(): Map<Cycle, Operand> {
        var cycle = 0
        val cycleToAddOperand = mutableMapOf<Cycle, Operand>()

        for (ins in input.trim().lines()) {
            when {
                ins.startsWith("noop") -> {
                    cycle += 1
                }
                ins.startsWith("addx") -> {
                    val operand = ins.split(" ")[1].toInt()
                    cycleToAddOperand[cycle + 2] = operand
                    cycle += 2
                }
            }
        }

        return cycleToAddOperand
    }

    companion object {
        private val INTERESTING_CYCLES = setOf(20, 60, 100, 140, 180, 220)
        private const val LIT_PIXEL = '#'
        private const val DARK_PIXEL = '.'
    }
}
