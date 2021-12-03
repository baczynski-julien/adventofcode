package com.jbi.adventofcode.advent2021.day2

import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day2 : DailySolution2021() {

    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 150
    override val expectedResultP2: Long
        get() = 900

    /*
    forward 5
    down 5
    forward 8
    up 3
    */

    override fun part1(reader: BufferedReader): Any {
        var depth = 0
        var horizontal = 0
        reader.useLines { sequence: Sequence<String> ->
            sequence.forEach {
                when (it[0]) {
                    'f' -> horizontal += it[8].digitToInt()
                    'd' -> depth += it[5].digitToInt()
                    'u' -> depth -= it[3].digitToInt()
                }
            }
        }
        return depth * horizontal
    }

    override fun part2(reader: BufferedReader): Any {
        var depth = 0L
        var horizontal = 0L
        var aim = 0L
        reader.useLines { sequence: Sequence<String> ->
            sequence.forEach {
                when (it[0]) {
                    'f' -> {
                        horizontal += it[8].digitToInt()
                        depth += aim * it[8].digitToInt()
                    }
                    'd' -> aim += it[5].digitToInt()
                    'u' -> aim -= it[3].digitToInt()
                }
            }
        }
        return depth * horizontal
    }
}