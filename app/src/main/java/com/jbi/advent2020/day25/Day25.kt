package com.jbi.advent2020.day25

import com.jbi.advent2020.DailySolution
import java.io.BufferedReader
import kotlin.math.max
import kotlin.math.min

/**
 * @author Julien BACZYNSKI on 12/25/20.
 */
object Day25 : DailySolution() {

    override val expectedResultP1: Any
        get() = 14897079L
    override val expectedResultP2: Any
        get() = 0

    private const val MOD = 20201227
    private const val DEFAULT_SUBJECT = 7

    private var cardKey: Long = 0
    private var doorKey: Long = 0

    override fun part1(reader: BufferedReader): Any {
        val key = min(cardKey, doorKey)
        val altKey = max(cardKey, doorKey)
        val loopSize = findLoopSize(key, DEFAULT_SUBJECT)
        log("loop size for key : $key => $loopSize")
        return runLoop(altKey, loopSize)
    }

    override fun part2(reader: BufferedReader): Any {
        return 0
    }

    override fun prerunInput(reader: BufferedReader) {
        readData(reader)
        log(
            "test loop size : 5764801 -> loop size found ${
                findLoopSize(
                    5764801,
                    DEFAULT_SUBJECT
                )
            } == 8"
        )
        log(
            "test loop size : 17807724 -> loop size found ${
                findLoopSize(
                    17807724,
                    DEFAULT_SUBJECT
                )
            } == 11"
        )

        log(
            "test apply loop size 11 to key 5764801 -> encryption found ${
                runLoop(
                    5764801,
                    11
                )
            } == 14897079"
        )
        log(
            "test apply loop 8 to key 17807724 -> encryption found ${
                runLoop(
                    17807724,
                    8
                )
            } == 14897079"
        )
    }

    override fun prerunSample(reader: BufferedReader) {
        readData(reader)
    }

    private fun runLoop(subjectNumber: Long, loopSize: Int): Long {
        var value = 1L
        for (i in 0 until loopSize)
            value = (value * subjectNumber) % MOD
        return value
    }

    private fun findLoopSize(key: Long, subjectNumber: Int): Int {
        var i = 0
        var value = 1L
        while (value != key) {
            value = (value * subjectNumber) % MOD
            i++
        }
        return i
    }

    private fun readData(reader: BufferedReader) {
        reader.useLines {
            it.forEachIndexed { i, line ->
                if (i == 0)
                    cardKey = line.toLong()
                else
                    doorKey = line.toLong()
            }
        }
    }
}
