package xyz.justinhorton.aoc2022

/**
 * https://adventofcode.com/2022/day/11
 */
class Day11(override val input: String) : Day() {
    override fun part1(): String {
        val monkeys = parseMonkeyInput()
        return calcMonkeyBizLevel(monkeys, 20) { it / 3 }.toString()
    }

    override fun part2(): String {
        val monkeys = parseMonkeyInput()
        val divisorProduct = monkeys.map { it.testDivisor }.reduce { acc, it -> acc * it }
        return calcMonkeyBizLevel(monkeys, 10_000) { it % divisorProduct }.toString()
    }

    private fun parseMonkeyInput(): List<Monkey> {
        return input.trim().split("\n\n")
            .map { monkeyInput ->
                val lines = monkeyInput.lines()

                val startingItems = lines[1].replace(",", "")
                    .split(" ")
                    .mapNotNull { it.toLongOrNull() }

                val expression = lines[2].substringAfter("= old ")
                val operator = when (expression.first()) {
                    '*' -> Operator.Mul
                    '+' -> Operator.Add
                    else -> throw UnsupportedOperationException()
                }
                val term2: Term = when (val rawTerm2 = expression.split(" ").last()) {
                    "old" -> Term.Old
                    else -> Term.Literal(rawTerm2.toLong())
                }

                val divisor = lines[3].split(" ").last().toInt()
                val ifTrueMonkey = lines[4].split(" ").last().toInt()
                val ifFalseMonkey = lines[5].split(" ").last().toInt()

                Monkey(
                    startingItems.map { Item(it) }.toMutableList(),
                    BinaryExpression(Term.Old, term2, operator),
                    divisor,
                    ifTrueMonkey,
                    ifFalseMonkey
                )
            }
    }

    private fun calcMonkeyBizLevel(monkeys: List<Monkey>, numRounds: Int, bizControlFn: (Long) -> Long): Long {
        val monkeyBizLevels = mutableMapOf<Int, Long>().withDefault { 0 }

        for (round in 1..numRounds) {
            for ((i: MonkeyId, m: Monkey) in monkeys.withIndex()) {
                for (item in m.items) {
                    item.worryLevel = bizControlFn(m.operation.eval(item.worryLevel))

                    if (item.worryLevel % m.testDivisor == 0L) {
                        monkeys[m.ifTrueMonkey].items += item
                    } else {
                        monkeys[m.ifFalseMonkey].items += item
                    }
                }
                monkeyBizLevels[i] = monkeyBizLevels.getValue(i) + m.items.size
                m.items.clear()
            }
        }

        val topBusinessmen = monkeyBizLevels.entries.sortedByDescending { it.value }
        return topBusinessmen.take(2).map { it.value }.reduce { acc, it -> acc * it }
    }
}

private typealias MonkeyId = Int

private data class Monkey(
    val items: MutableList<Item>,
    val operation: BinaryExpression,
    val testDivisor: Int,
    val ifTrueMonkey: MonkeyId,
    val ifFalseMonkey: MonkeyId
)

private class Item(var worryLevel: Long) {
    override fun toString(): String = "Item(w=$worryLevel)"
}

private data class BinaryExpression(val first: Term, val second: Term, val operator: Operator) {
    fun eval(old: Long): Long {
        val t1 = first.resolve(old)
        val t2 = second.resolve(old)
        return when (operator) {
            is Operator.Mul -> t1 * t2
            is Operator.Add -> t1 + t2
        }
    }
}

private sealed class Term {
    fun resolve(old: Long): Long = when (this) {
        is Literal -> value
        is Old -> old
    }

    data class Literal(val value: Long) : Term()

    object Old : Term()
}

private sealed class Operator {
    object Mul : Operator()
    object Add : Operator()
}
