package xyz.justinhorton.aoc2022

/**
 * https://adventofcode.com/2022/day/13
 */
class Day13(override val input: String) : Day() {
    override fun part1(): String {
        return input.split("\n\n")
            .map {
                val lines = it.lines()
                parseList(lines[0].asTokenDeque()) to parseList(lines[1].asTokenDeque())
            }.mapIndexedNotNull { index, pair ->
                if (pairsInRightOrder(pair.first, pair.second)) {
                    index + 1
                } else {
                    null
                }
            }.sum().toString()
    }

    private fun pairsInRightOrder(p1: List<*>, p2: List<*>): Boolean {
        val inOrder = inRightOrder(p1, p2)
        requireNotNull(inOrder) { "Comparison of input pair should not be null" }
        return inOrder
    }

    private fun inRightOrder(p1: List<*>, p2: List<*>): Boolean? {
        for ((item1, item2) in p1.zip(p2)) {
            if (item1 is Int && item2 is Int) {
                when {
                    item1 < item2 -> return true
                    item1 > item2 -> return false
                }
            } else {
                val l = (item1 as? List<*>) ?: listOf(item1)
                val r = (item2 as? List<*>) ?: listOf(item2)
                inRightOrder(l, r).let { if (it != null) return it }
            }
        }

        // ran out...was left or right shorter?
        return if (p1.size != p2.size) p1.size < p2.size else null
    }

    private fun String.asTokenDeque() = ArrayDeque<Char>().apply { this@asTokenDeque.forEach { add(it) } }

    override fun part2(): String {
        val sorted = input.trim()
            .lineSequence()
            .filter { it.isNotBlank() }
            .mapTo(mutableListOf()) { parseList(it.asTokenDeque()) }
            .also { it.addAll(dividerPackets) }
            .sortedWith { a, b ->
                when (inRightOrder(a, b)) {
                    true -> -1
                    false -> 1
                    null -> 0
                }
            }

        val two = sorted.indexOf(dividerPackets.first()) + 1
        val six = sorted.indexOf(dividerPackets.last()) + 1
        return (two * six).toString()
    }

    private fun parseList(q: ArrayDeque<Char>): List<Any> {
        require(q.removeFirst() == '[') { "Not the start of a list" }

        val resultList = mutableListOf<Any>()

        var nextCh: Char
        while (q.isNotEmpty()) {
            nextCh = q.removeFirst()

            when {
                nextCh.isDigit() -> {
                    var digitStr = "$nextCh"

                    while (q.isNotEmpty() && q.first().isDigit()) {
                        digitStr = "$digitStr${q.removeFirst()}"
                    }

                    resultList.add(digitStr.toInt())
                }
                nextCh == '[' -> {
                    q.addFirst(nextCh) // put back the '['
                    resultList.add(parseList(q))
                }
                nextCh == ',' -> continue
                else -> { // nextCh == ']
                    return resultList
                }
            }
        }

        return resultList
    }
}

private val dividerPackets = listOf(listOf(listOf(2)), listOf(listOf(6)))
