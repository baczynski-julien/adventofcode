package com.jbi.adventofcode.advent2021.day9

import android.graphics.Point
import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day9 : DailySolution2021() {


    private lateinit var data: MutableList<MutableList<Int>>
    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 15
    override val expectedResultP2: Long
        get() = 1134

    override fun part1(reader: BufferedReader): Any {
        var count = 0
        var lower: Boolean
        for (i in 0 until data.size) {
            for (j in 0 until data[i].size) {
                lower = true
                //left
                if (j > 0 && data[i][j] >= data[i][j - 1])
                    lower = false
                //top
                if (i > 0 && data[i][j] >= data[i - 1][j])
                    lower = false
                //right
                if (j < data[i].size - 1 && data[i][j] >= data[i][j + 1])
                    lower = false
                //bottom
                if (i < data.size - 1 && data[i][j] >= data[i + 1][j])
                    lower = false
                if (lower) {
                    // log("[$i,$j]")
                    count += (1 + data[i][j])
                }
            }
        }
        return count
    }

    override fun part2(reader: BufferedReader): Any {
        var lower: Boolean
        val basins: MutableList<Point> = mutableListOf()
        for (i in 0 until data.size) {
            for (j in 0 until data[i].size) {
                lower = true
                //left
                if (j > 0 && data[i][j] >= data[i][j - 1])
                    lower = false
                //top
                if (i > 0 && data[i][j] >= data[i - 1][j])
                    lower = false
                //right
                if (j < data[i].size - 1 && data[i][j] >= data[i][j + 1])
                    lower = false
                //bottom
                if (i < data.size - 1 && data[i][j] >= data[i + 1][j])
                    lower = false
                if (lower) {
                    // log("[$i,$j]")
                    basins.add(Point(i, j))
                }
            }
        }
        val basinSizes = mutableListOf<Int>()
        for (basin in basins) {
            val checkedPoints = mutableListOf<Point>()
            checkedPoints.add(Point(basin.x, basin.y))
            basinSizes.add(computeBasinSize(basin, checkedPoints))
        }
        basinSizes.sortDescending()
        return basinSizes[0] * basinSizes[1] * basinSizes[2]
    }

    private fun computeBasinSize(basin: Point, checkedPoints: MutableList<Point>): Int {
        if (data[basin.x][basin.y] == 9)
            return 0
        val toCheck = mutableListOf<Point>()

        //left
        var point = Point(basin.x, basin.y - 1)
        if (basin.y > 0 && !checkedPoints.contains(point))
            toCheck.add(point)

        //top
        point = Point(basin.x - 1, basin.y)
        if (basin.x > 0 && !checkedPoints.contains(point))
            toCheck.add(point)

        //right
        point = Point(basin.x, basin.y + 1)
        if (basin.y < data[basin.x].size - 1 && !checkedPoints.contains(point))
            toCheck.add(point)

        //bottom
        point = Point(basin.x + 1, basin.y)
        if (basin.x < data.size - 1 && !checkedPoints.contains(point))
            toCheck.add(point)

        checkedPoints.addAll(toCheck)
        return 1 + toCheck.sumOf { computeBasinSize(it, checkedPoints) }
    }

    override fun prerunInput(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        data = buildData(reader)
    }

    private fun buildData(reader: BufferedReader): MutableList<MutableList<Int>> {
        val arr: MutableList<MutableList<Int>> = mutableListOf()
        reader.useLines { sequence ->
            sequence.forEachIndexed { index, line ->
                val current = mutableListOf<Int>()
                arr.add(current)
                line.forEach {
                    current.add(it.digitToInt())
                }
            }
        }
        return arr
    }
}