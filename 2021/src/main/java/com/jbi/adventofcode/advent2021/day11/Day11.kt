package com.jbi.adventofcode.advent2021.day11

import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day11 : DailySolution2021() {

    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 1656
    override val expectedResultP2: Long
        get() = 195

    override fun part1(reader: BufferedReader): Any {
        var flashes = 0
        val data = buildData(reader)
        for (i in 0 until 100) {
            //increment all
            for (x in 0 until 10)
                for (y in 0 until 10)
                    data[x][y]++
            var flashed : Boolean
            do {
                flashed = false
                for (x in 0 until 10)
                    for (y in 0 until 10)
                        if (data[x][y] > 9) {
                            flashes++
                            flashed = true
                            data[x][y] = 0
                            flash(data, x, y)
                        }
            } while (flashed)
        }
        return flashes
    }

    override fun part2(reader: BufferedReader): Any {
        log("Initial state")
        val data = buildData(reader)
        printGrid(data)
        for (i in 0 until 10000) {
            var localFlashes = 0
            //increment all
            for (x in 0 until 10)
                for (y in 0 until 10)
                    data[x][y]++
            var flashed : Boolean
            do {
                flashed = false
                for (x in 0 until 10)
                    for (y in 0 until 10)
                        if (data[x][y] > 9) {
                            localFlashes++
                            flashed = true
                            data[x][y] = 0
                            flash(data, x, y)
                        }
            } while (flashed)
            if(localFlashes == 100)
                return i+1
            //log("After ${i+1} step")
            //printGrid(data)
        }
        return 0
    }

    private fun printGrid(data: Array<Array<Int>>) {
        for (x in 0 until 10)
            log("$x : ${data[x][0]}${data[x][1]}${data[x][2]}${data[x][3]}${data[x][4]}${data[x][5]}${data[x][6]}${data[x][7]}${data[x][8]}${data[x][9]}")

    }

    private fun flash(data: Array<Array<Int>>, x: Int, y: Int) {
        //top line
        if (x > 0) {
            if (y > 0)
                increaseIfNotFlashed(data, x - 1, y - 1)
            increaseIfNotFlashed(data, x - 1, y)
            if (y < 9)
                increaseIfNotFlashed(data, x - 1, y + 1)
        }
        //same line
        if (y > 0)
            increaseIfNotFlashed(data, x, y - 1)
        if (y < 9)
            increaseIfNotFlashed(data, x, y + 1)
        //bottom line
        if (x < 9) {
            if (y > 0)
                increaseIfNotFlashed(data, x + 1, y - 1)
            increaseIfNotFlashed(data, x + 1, y)
            if (y < 9)
                increaseIfNotFlashed(data, x + 1, y + 1)
        }
    }

    private fun increaseIfNotFlashed(data: Array<Array<Int>>, x: Int, y: Int) {
        if (data[x][y] != 0)
            data[x][y]++
    }

    private fun buildData(reader: BufferedReader): Array<Array<Int>> {
        val data = Array(10) { Array(10) { 0 } }
        reader.useLines { sequence ->
            sequence.forEachIndexed { i, line ->
                line.forEachIndexed { j, c ->
                    data[i][j] = c.digitToInt()
                }
            }
        }
        return data
    }
}