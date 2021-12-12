package com.jbi.adventofcode.advent2021.day7

import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader
import kotlin.math.abs

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day7 : DailySolution2021() {

    private lateinit var data: List<Int>
    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 37
    override val expectedResultP2: Long
        get() = 168

    override fun part1(reader: BufferedReader): Any {
        val range = data.minOf { it } .. data.maxOf { it }
        val sums = Array(range.last - range.first +1){0}
        for(i in range){
            for(j in data){
                sums[i]+= abs(i-j)
            }
        }
        return sums.filter { it !=0 }.minOf { it }
    }

    /**
     * 1 -> 1
     * 2 -> 3
     * 3 -> 5
     * 4 -> 9
     * 5 -> 14
     * 6 -> 20
     */
    override fun part2(reader: BufferedReader): Any {
        val range = data.minOf { it } .. data.maxOf { it }
        val sums = Array(range.last - range.first +1){0}
        for(i in range){
            for(j in data){
                var diff = abs(i-j)
                sums[i]+= (diff*(diff+1))/2
            }
        }
        return sums.filter { it !=0 }.minOf { it }
    }


    override fun prerunInput(reader: BufferedReader) {
        data = readData(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        data = readData(reader)
    }

    private fun readData(reader: BufferedReader): List<Int> =
        reader.readLine().split(',').map { Integer.parseInt(it) }


}