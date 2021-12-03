package com.jbi.adventofcode.advent2020.day1

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/1/20.
 */
object Day1 : DailySolution2020() {

    private lateinit var data: List<Int>
    override val expectedResultP1: Any
        get() = 514579
    override val expectedResultP2: Any
        get() = 241861950

    override fun prerunInput(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        data = buildData(reader)
    }

    private fun buildData(reader: BufferedReader): List<Int> =
        mutableListOf<Int>().apply {
            reader.useLines { sequence: Sequence<String> ->
                sequence.forEach {
                    add(Integer.parseInt(it))
                }
            }
        }


    override fun part1(reader: BufferedReader): Any {
        for (i in data.indices) {
            for (j in i + 1 until data.size) {
                for (k in j + 1 until data.size)
                    if (data[i] + data[j] == 2020) {
                        return data[i] * data[j]
                    }
            }
        }
        return 0
    }

    override fun part2(reader: BufferedReader): Any {
        for (i in data.indices) {
            for (j in i + 1 until data.size) {
                for (k in j + 1 until data.size)
                    if (data[i] + data[j] + data[k] == 2020) {
                        return data[i] * data[j] * data[k]
                    }
            }
        }
        return 0
    }
}