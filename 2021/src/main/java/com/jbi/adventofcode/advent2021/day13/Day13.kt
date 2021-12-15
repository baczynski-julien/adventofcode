package com.jbi.adventofcode.advent2021.day13

import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day13 : DailySolution2021() {

    private lateinit var data: Array<Array<Char>>
    private val folds = mutableListOf<FoldInstruction>()

    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 0
    override val expectedResultP2: Int
        get() = 0

    override fun part1(reader: BufferedReader): Any {
        buildData(reader)
        var array = fold(data, folds[0])
        return  array.sumOf { line -> line.count { c -> c == '#' }  }
    }

    private fun printArray(title: String, data: Array<Array<Char>>) {
        log(title)
        for (line in data) {
            val sb = StringBuilder()
            for (c in line)
                sb.append(c)
            log(sb.toString())
        }
    }

    override fun part2(reader: BufferedReader): Any {
        buildData(reader)
        var array = data
        folds.forEachIndexed { i, fold ->
            array = fold(array, fold)
        }
        printArray("code", array)
        return 0
    }

    private fun buildData(reader: BufferedReader) {
        data = Array(1400) { Array(1400) { '.' } }
        folds.clear()
        reader.useLines { sequence ->
            sequence.forEach {
                if (it.isNotBlank()) {
                    if (it[0] == 'f') {
                        val sub = it.substring(11).split('=')
                        if (sub[0][0] == 'x') {
                            folds.add(FoldInstruction(Axis.X, Integer.parseInt(sub[1])))
                        } else {
                            folds.add(FoldInstruction(Axis.Y, Integer.parseInt(sub[1])))
                        }
                    } else {
                        val sub = it.split(',')
                        data[Integer.parseInt(sub[1])][Integer.parseInt(sub[0])] = '#'
                    }
                }
            }
        }
    }

    fun fold(array: Array<Array<Char>>, foldInstruction: FoldInstruction): Array<Array<Char>> {
        var foldedArray =
            Array(if (foldInstruction.axis == Axis.Y) foldInstruction.value else array.size) {
                Array(if (foldInstruction.axis == Axis.X) foldInstruction.value else array[0].size) {
                    '.'
                }
            }
        if (foldInstruction.axis == Axis.Y) {
            for (y in foldedArray.indices) {
                for (x in foldedArray[0].indices) {
                    if (array[y][x] == '#' || array[foldedArray.size * 2 - y][x] == '#')
                        foldedArray[y][x] = '#'
                }
            }
        }else{
            for (y in foldedArray.indices) {
                for (x in foldedArray[0].indices) {
                    if (array[y][x] == '#' || array[y][foldedArray[0].size * 2 - x] == '#')
                        foldedArray[y][x] = '#'
                }
            }
        }
        return foldedArray
    }

    class FoldInstruction(val axis: Axis, val value: Int)

    enum class Axis {
        X,
        Y
    }
}