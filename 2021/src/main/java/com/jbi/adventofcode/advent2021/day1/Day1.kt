package com.jbi.adventofcode.advent2021.day1

import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day1 : DailySolution2021() {

    private lateinit var input: List<Int>
    override val runWithInput: Boolean
        get() = true
    override val expectedResultP1: Int
        get() = 7
    override val expectedResultP2: Long
        get() = 5

    override fun prerunInput(reader: BufferedReader) {
        input = buildInput(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        input = buildInput(reader)
    }

    override fun part1(reader: BufferedReader): Any =
        getIncrements(input)

    override fun part2(reader: BufferedReader): Any {
        val list = MutableList(input.size - 2) { 0 }
        for (i in input.indices) {
            if (i < list.size)
                list[i] += input[i]
            if (i - 1 >= 0 && i - 1 < list.size)
                list[i - 1] += input[i]
            if (i - 2 >= 0)
                list[i - 2] += input[i]
        }
        return getIncrements(list)
    }

    private fun getIncrements(list: List<Int>): Int {
        var value = 0
        for (i in 1 until list.size) {
            if (list[i] > list[i - 1])
                value++
        }
        return value
    }

    private fun buildInput(reader: BufferedReader): List<Int> =
        mutableListOf<Int>().apply {
            reader.useLines { sequence: Sequence<String> ->
                sequence.forEach {
                    add(Integer.parseInt(it))
                }
            }
        }

}