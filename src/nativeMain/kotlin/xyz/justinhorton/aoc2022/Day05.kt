package xyz.justinhorton.aoc2022

/**
 * https://adventofcode.com/2022/day/5
 */
class Day05(override val input: String) : Day() {
    private val inputStacks: Map<Int, List<Char>> = run {
        input.split("\n\n")[0].let { initialStacksInput ->
            val stackLabels = initialStacksInput.trim().lines().last()

            // all lines in the first section are the same length...find the indices for the columns with item chars
            val stackCols = stackLabels.mapIndexedNotNull { col, ch ->
                if (ch.isDigit()) col else null
            }
            val itemLines = initialStacksInput.trim()
                .lines()
                .dropLast(1)

            // map stack # to chars in stack
            stackCols.mapIndexed { i, col ->
                (i + 1) to itemLines.map { l -> l[col] }.filter { it.isUpperCase() }
            }
        }.toMap()
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
