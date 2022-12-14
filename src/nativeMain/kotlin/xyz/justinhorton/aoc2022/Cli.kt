package xyz.justinhorton.aoc2022

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.use

fun main(args: Array<String>) {
    val parser = ArgParser("aocrunner")
    val dayArg by parser.option(
        ArgType.Int,
        shortName = "d",
        fullName = "day",
        description = "Day number"
    ).required()
    val inputFileArg by parser.option(
        ArgType.String,
        shortName = "i",
        fullName = "in-file",
        description = "Input file path"
    )
    val expectedAns1 by parser.option(
        ArgType.String,
        shortName = "a1",
        fullName = "answer1",
        description = "Part 1 expected answer"
    )
    val expectedAns2 by parser.option(
        ArgType.String,
        shortName = "a2",
        fullName = "answer2",
        description = "Part 2 expected answer"
    )

    parser.parse(args)

    val inputPath = inputFileArg?.toPath() ?: defaultInputPath(dayArg)

    require(dayArg in 1..25) { "Invalid day number ($dayArg)" }
    require(FileSystem.SYSTEM.exists(inputPath)) { "Invalid input file ($inputPath); path does not exist" }

    val fileContents = FileSystem.SYSTEM.source(inputPath).use {
        it.buffer().readUtf8()
    }

    println("DAY $dayArg ($inputPath)")
    day(dayArg, fileContents)?.let { day ->
        val ans1 = day.part1()
        println("--> ${matchText(expectedAns1, ans1)}$dayArg.1: $ans1")

        val ans2 = day.part2()
        println("--> ${matchText(expectedAns2, ans2)}$dayArg.2: $ans2")
    } ?: println("--> No solution (yet)")
}

private fun matchText(expected: String?, actual: String): String {
    return when (expected) {
        null, UNKNOWN_ANSWER -> ""
        else -> {
            val expectedResolved = if (expected.endsWith(".txt")) {
                // read multi-line input from file
                FileSystem.SYSTEM.source(INPUTS_BASE.toPath().resolve(expected)).use { src ->
                    src.buffer().readUtf8()
                }
            } else {
                expected
            }

            if (expectedResolved.trim() == actual.trim()) {
                " ✅ "
            } else {
                " ❌ "
            }
        }
    }
}

fun day(dayNum: Int, input: String): Day? {
    val cons: ((String) -> Day)? = when (dayNum) {
        1 -> ::Day01
        2 -> ::Day02
        3 -> ::Day03
        4 -> ::Day04
        5 -> ::Day05
        6 -> ::Day06
        7 -> ::Day07
        8 -> ::Day08
        9 -> ::Day09
        10 -> ::Day10
        11 -> ::Day11
        12 -> ::Day12
        13 -> ::Day13
        14 -> ::Day14
        else -> null
    }
    return cons?.invoke(input)
}

fun defaultInputPath(i: Int): Path = INPUTS_BASE.toPath().resolve("""${i.toString().padStart(2, '0')}.txt""")

private const val INPUTS_BASE = "inputs"
private const val UNKNOWN_ANSWER = "?"
