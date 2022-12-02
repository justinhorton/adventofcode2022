package xyz.justinhorton.aoc2022

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.io.path.readText
import kotlin.test.assertEquals

class Day01Test {
    @Nested
    inner class Samples {
        @Test
        fun part1Sample() {
            val r = Day01(SAMPLE1).part1()
            assertEquals("24000", r)
        }
    }

    @Nested
    inner class Answers {
        private lateinit var day: Day

        @BeforeEach
        fun beforeEach() {
            day = Day01(defaultInputPath(1).readText())
        }

        @Test
        fun part1() {
            assertEquals("68923", day.part1())
        }

        @Test
        fun part2() {
            assertEquals("200044", day.part2())
        }
    }
}

private const val SAMPLE1 = """1000
2000
3000

4000

5000
6000

7000
8000
9000

10000"""
