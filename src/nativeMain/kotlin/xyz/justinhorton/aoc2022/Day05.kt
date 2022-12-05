package xyz.justinhorton.aoc2022

/**
 * https://adventofcode.com/2022/day/5
 */
class Day05(override val input: String) : Day() {
    private val inputStacks: Map<Int, List<Char>> = run {
        val stacks = mutableMapOf<Int, MutableList<Char>>()

        input.split("\n\n")[0].let { initialStacksInput ->
            initialStacksInput.lines()
                .dropLast(1)
                .forEach { line ->
                    var slot = 0
                    line.forEachIndexed { i, ch ->
                        // every 4th char is the start of the next slot column
                        // [X] [Y] => chars 0-3 = slot 1, 4-6 = slot 2
                        if (i % 4 == 0) {
                            slot++
                        }

                        if (ch.isUpperCase()) {
                            stacks.getOrPut(slot) { mutableListOf() }.add(ch)
                        }
                    }
                }
            stacks
        }
    }

    private val inputMoves: List<Triple<Int, Int, Int>> = run {
        input.split("\n\n")[1].trim()
            .lines()
            .map { line ->
                line.split(" ")
                    .mapNotNull { s -> s.toIntOrNull() }
                    .let { Triple(it[0], it[1], it[2]) }
            }
    }

    override fun part1(): String {
        val stacks = inputStacks.entries.associate { it.key to it.value.toMutableList() }

        inputMoves.forEach { (count, src, dst) ->
            repeat(count) {
                val ch = stacks.getValue(src).removeFirst()
                stacks.getValue(dst).add(0, ch)
            }
        }

        return getStackTops(stacks)
    }

    override fun part2(): String {
        val stacks = inputStacks.entries.associate { it.key to it.value.toMutableList() }

        inputMoves.forEach { (count, src, dst) ->
            val removed = stacks.getValue(src).run {
                (1..count).map { removeFirst() }
            }
            stacks.getValue(dst).addAll(0, removed)
        }

        return getStackTops(stacks)
    }

    private fun getStackTops(stacks: Map<Int, List<Char>>) =
        stacks.entries
            .sortedBy { it.key }
            .map { it.value.first() }
            .toCharArray()
            .concatToString()
}
