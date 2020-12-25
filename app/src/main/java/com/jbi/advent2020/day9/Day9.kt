package com.jbi.advent2020.day9

import com.jbi.advent2020.DailySolution
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/9/20.
 */
object Day9 : DailySolution() {

    private var wrongNumber: Long = 0
    private lateinit var data: List<Long>
    override val expectedResultP1: Any
        get() = 0
    override val expectedResultP2: Any
        get() = 0

    override fun prerunInput(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun part1(reader: BufferedReader): Any {
        wrongNumber = findWrongNumber(data)
        return wrongNumber
    }

    override fun part2(reader: BufferedReader): Any {
        return findSum(data, wrongNumber)
    }

    private fun findSum(data: List<Long>, wrongNumber: Long): Long {
        for (i in 0 until data.size - 1) {
            var sum = data[i]
            var max = data[i]
            var min = data[i]
            for (j in i + 1 until data.size) {
                sum += data[j]
                max = maxOf(max, data[j])
                min = minOf(min, data[j])
                if (sum == wrongNumber) {
                    log("sum of indices : $i to $j sums to $wrongNumber, max : $max, min : $min")
                    return max + min
                }
                if (sum > wrongNumber)
                    break
            }
        }
        return -1
    }

    private fun findWrongNumber(data: List<Long>): Long {
        for (i in 25 until data.size) {
            if (!checkSum(data[i], data.subList(i - 25, i))) {
                return data[i]
            }
        }
        return -1
    }

    private fun checkSum(number: Long, subList: List<Long>): Boolean {
        for (i in subList.indices) {
            for (j in i + 1 until subList.size)
                if (subList[i] + subList[j] == number)
                    return true
        }
        return false
    }

    private fun buildData(reader: BufferedReader): List<Long> =
        reader.useLines { seq -> seq.fold(mutableListOf()) { list, line -> list.add(line.toLong()); list } }


}
