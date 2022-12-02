package xyz.justinhorton.aoc2022

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.readText
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

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

    parser.parse(args)

    val inputPath = inputFileArg?.let { Paths.get(it) } ?: defaultInputPath(dayArg)

    require(dayArg in 1..25) { "Invalid day number ($dayArg)" }
    require(Files.exists(inputPath)) { "Invalid input file ($inputPath); path does not exist" }

    val paddedDayNum = dayArg.toString().padStart(2, '0')
    val dayClass: KClass<out Day>? = Day::class.sealedSubclasses.find { it.simpleName!!.endsWith(paddedDayNum) }

    println("${parser.programName} >> DAY $dayArg")
    dayClass?.let {
        val actualDay: Day = dayClass.primaryConstructor!!.call(inputPath.readText())
        println("--> $dayArg.1: ${actualDay.part1()}")
        println("--> $dayArg.2: ${actualDay.part2()}")
    } ?: println("--> No solution (yet)")
}

fun defaultInputPath(i: Int): Path = Paths.get(INPUTS_BASE).resolve("""${i.toString().padStart(2, '0')}.txt""")

private const val INPUTS_BASE = "inputs"
