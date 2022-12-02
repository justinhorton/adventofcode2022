package xyz.justinhorton.aoc2022

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.io.path.readText
import kotlin.test.assertEquals

class Day02Test {
    @Nested
    inner class Samples {
        @Test
        fun part1Sample() {
            val r = Day02(SAMPLE1).part1()
            assertEquals("15", r)
        }

        @Test
        fun part2Sample() {
            val r = Day02(SAMPLE1).part2()
            assertEquals("12", r)
        }
    }

    @Nested
    inner class Answers {
        private lateinit var day: Day

        @BeforeEach
        fun beforeEach() {
            day = Day02(defaultInputPath(2).readText())
        }

        @Test
        fun part1() {
            assertEquals("12276", day.part1())
        }

        @Test
        fun part2() {
            assertEquals("9975", day.part2())
        }
    }
}

private const val SAMPLE1 = """A Y
B X
C Z"""
