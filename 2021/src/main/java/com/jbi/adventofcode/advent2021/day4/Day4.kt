package com.jbi.adventofcode.advent2021.day4

import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day4 : DailySolution2021() {

    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 4512
    override val expectedResultP2: Int
        get() = 1924
    lateinit var numbers: List<Int>
    lateinit var grids: MutableList<Grid>

    override fun prerunSample(reader: BufferedReader) {
        buildData(reader)
    }

    override fun prerunInput(reader: BufferedReader) {
        buildData(reader)
    }

    private fun buildData(reader: BufferedReader) {
        grids = mutableListOf()
        var currentGrid: Grid? = null
        var modIndex: Int
        reader.useLines { sequence ->
            sequence.forEachIndexed { index, line ->
                if (line.isNotBlank()) {
                    if (index == 0) {
                        numbers = mutableListOf<Int>().apply {
                            line.split(',').forEach {
                                add(Integer.parseInt(it))
                            }
                        }
                    } else {
                        if (currentGrid == null)
                            currentGrid = Grid()

                        currentGrid!!.add(
                            line.split(' ').filter { it.isNotBlank() }.map {
                                GridNumber(Integer.parseInt(it))
                            })
                        if (currentGrid!!.size() == 5) {
                            grids.add(currentGrid!!)
                            currentGrid = null
                        }
                    }
                }
            }
        }
    }

    override fun part1(reader: BufferedReader): Any {
        numbers.forEach { number ->
            grids.forEach { grid ->
                grid.checkNumber(number)
                if (grid.isWinningGrid())
                    return number * grid.sumNonChecked()
            }
        }
        return 0
    }

    override fun part2(reader: BufferedReader): Any {
        numbers.forEach { number ->
            val itr = grids.iterator()
            while (itr.hasNext()) {
                val grid = itr.next()
                grid.checkNumber(number)
                if (grid.isWinningGrid()) {
                    if (grids.size == 1)
                        return number * grid.sumNonChecked()
                    itr.remove()
                }
            }
        }
        return 0
    }

    class Grid {
        private val numbers: MutableList<List<GridNumber>> = mutableListOf()

        fun checkNumber(number: Int) {
            numbers.forEach { line ->
                line.forEach { gridNumber ->
                    if (gridNumber.number == number) gridNumber.checked = true
                }
            }
        }

        fun isWinningGrid(): Boolean =
            numbers.any { it.all { number -> number.checked } } || (0 until 5).any { index -> numbers.all { it[index].checked } }

        fun sumNonChecked(): Int =
            numbers.sumOf {
                it.filter { gridNumber -> !gridNumber.checked }
                    .sumOf { gridNumber -> gridNumber.number }
            }

        fun add(line: List<GridNumber>) {
            numbers.add(line)
        }

        fun size(): Int = numbers.size
    }

    data class GridNumber(val number: Int, var checked: Boolean = false)
}


