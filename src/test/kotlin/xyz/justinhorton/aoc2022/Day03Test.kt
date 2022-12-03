package xyz.justinhorton.aoc2022

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.io.path.readText
import kotlin.test.assertEquals

class Day03Test {
    @Nested
    inner class Samples {
        @Test
        fun part1Sample() {
            val r = Day03(SAMPLE1).part1()
            assertEquals("157", r)
        }

        @Test
        fun part2Sample() {
            val r = Day03(SAMPLE1).part2()
            assertEquals("70", r)
        }
    }

    @Nested
    inner class Answers {
        private lateinit var day: Day

        @BeforeEach
        fun beforeEach() {
            day = Day03(defaultInputPath(3).readText())
        }

        @Test
        fun part1() {
            assertEquals("7746", day.part1())
        }

        @Test
        fun part2() {
            assertEquals("2604", day.part2())
        }
    }
}

private const val SAMPLE1 = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw"""
