package com.jbi.adventofcode.advent2021.day12

import com.jbi.adventofcode.advent2021.DailySolution2021
import com.jbi.adventofcode.advent2021.day9.Day9
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day12 : DailySolution2021() {

    override val runWithInput: Boolean
        get() = false

    override val expectedResultP1: Int
        get() = 0
    override val expectedResultP2: Long
        get() = 0

    override fun part1(reader: BufferedReader): Any {
        return 0
    }

    override fun part2(reader: BufferedReader): Any {
        return 0
    }

    override fun prerunInput(reader: BufferedReader) {
      // data = buildData(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
      //  data = buildData(reader)
    }

    private fun buildData(reader: BufferedReader): Any{
        val arr: MutableList<MutableList<Int>> = mutableListOf()
        reader.useLines { sequence ->
            sequence.forEachIndexed { index, line ->

            }
        }
        return 0
    }

}