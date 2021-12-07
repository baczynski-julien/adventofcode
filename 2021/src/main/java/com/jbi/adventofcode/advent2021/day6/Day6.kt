package com.jbi.adventofcode.advent2021.day6

import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day6 : DailySolution2021() {

    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 5934
    override val expectedResultP2: Long
        get() = 26984457539L

    override fun part1(reader: BufferedReader): Any {
        var data = reader.readLine().split(',').map { Integer.parseInt(it) }.toMutableList()
        return runGens(data, 80).sumOf { it }
    }

    override fun part2(reader: BufferedReader): Any {
        var data = reader.readLine().split(',').map { Integer.parseInt(it) }.toMutableList()
        return runGens(data, 256).sumOf { it }
    }

    private fun runGens(data: MutableList<Int>, gens: Int): Array<Long> {
        var array = Array(9) { 0L }
        data.forEach {
            array[it]++
        }
        for (i in 1..gens) {
            val eigths = array[0]
            array[0] = array[1]
            array[1] = array[2]
            array[2] = array[3]
            array[3] = array[4]
            array[4] = array[5]
            array[5] = array[6]
            array[6] = array[7] + eigths
            array[7] = array[8]
            array[8] = eigths
        }
        return array
    }
}