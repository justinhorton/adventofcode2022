package xyz.justinhorton.aoc2022

/**
 * https://adventofcode.com/2022/day/2
 */
class Day02(override val input: String) : Day() {
    override fun part1(): String = calcScore { theirCode: Char, myCode: Char ->
        val myMove = Move.fromCode(myCode)
        val theirMove = Move.fromCode(theirCode)
        myMove.score + Move.throwOutcome(myMove, theirMove)
    }

    override fun part2(): String = calcScore { theirCode: Char, myCode: Char ->
        val theirMove = Move.fromCode(theirCode)
        val myMove = Move.fromPt2Code(myCode, theirMove)
        myMove.score + Move.throwOutcome(myMove, theirMove)
    }

    private fun calcScore(scoreFn: (Char, Char) -> Int) = input.trim()
        .split("\n")
        .asSequence()
        .map { r -> r.split(" ").let { it[0].single() to it[1].single() } }
        .sumOf { scoreFn(it.first, it.second) }
        .toString()
}

private enum class Move(val score: Int, val losesTo: Int, val winsAgainst: Int) {
    Rock(1, 1, 2), // 0
    Paper(2, 2, 0), // 1
    Scissors(3, 0, 1); // 2

    companion object {
        fun fromCode(ch: Char): Move = when (ch) {
            in "AX" -> Rock
            in "BY" -> Paper
            else -> Scissors
        }

        fun fromPt2Code(ch: Char, theirMove: Move): Move = when (ch) {
            'X' -> Move.values()[theirMove.winsAgainst]
            'Y' -> theirMove
            else -> Move.values()[theirMove.losesTo]
        }

        fun throwOutcome(myMove: Move, theirMove: Move): Int = if (myMove == theirMove) {
            DRAW
        } else {
            if (myMove.losesTo == theirMove.ordinal) LOSE else WIN
        }
    }
}

private const val WIN = 6
private const val DRAW = 3
private const val LOSE = 0
