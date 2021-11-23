package com.jbi.adventofcode.advent2020.day10

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/10/20.
 */
object Day10 : DailySolution2020() {

    private lateinit var memory: MutableMap<Int, Long>
    private lateinit var data: MutableList<Int>
    override val expectedResultP1: Any
        get() = 220
    override val expectedResultP2: Any
        get() = 19208L

    override fun prerunSample(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun prerunInput(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun part1(reader: BufferedReader): Any {
        val map = mutableMapOf<Int, Int>()
        map[0] = 0
        map[1] = 0
        map[2] = 0
        map[3] = 0
        var currentOutput = 0
        for (adapter in data) {
            map[adapter - currentOutput] = map[adapter - currentOutput]!! + 1
            currentOutput = adapter
        }
        return map[1]!! * map[3]!!
    }

    private fun buildData(reader: BufferedReader): MutableList<Int> {
        return reader.useLines { seq ->
            seq.fold(mutableListOf<Int>()) { list, line ->
                list.add(line.toInt())
                list
            }
        }.apply {
            sort()
            add(last() + 3)
        }
    }

    override fun part2(reader: BufferedReader): Any {
        memory = mutableMapOf()
        return countVariations(data, 0)
    }

    /**
     * recursively making possible choice, retaining already computed possibilities to avoid stack explosion
     */
    private fun countVariations(data: List<Int>, currentPower: Int): Long {
        if (data.isEmpty())
            return 1
        var i = 0
        var count = 0L
        while (i < data.size && data[i] - currentPower <= 3) {
            if (!memory.containsKey(data[i])) {
                memory[data[i]] = countVariations(data.subList(i + 1, data.size), data[i])
            }
            count += memory[data[i]]!!
            i++
        }
        return count
    }
    /**
     * taking into account that diffs are 1 or 3, so successive 1 can be permuted, and a diff of 3 mean there is no choice for this point in the series
     * so counting successive 1 and multiplying by known permutations data
     * Having diffs of 2 would invalidate this algorithm but not the 1st one
     */
    private fun countVariations2(data: MutableList<Int>, output: Int): Long {
        val list = mutableListOf<Int>()
        var currentOutput: Int = output

        data.forEach { adapter ->
            list.add(adapter - currentOutput)
            currentOutput = adapter
        }
        var total = 1L
        var count = 0
        list.forEach {
            if (it == 3) {
                total *= getPermutations(count)
                count = 0
            } else {
                count++
            }
        }
        return total
    }

    private fun getPermutations(count: Int): Int {
        return when (count) {
            0 -> 1
            1 -> 1
            2 -> 2
            3 -> 4
            4 -> 7
            5 -> 13
            else -> 0
        }
    }


}
