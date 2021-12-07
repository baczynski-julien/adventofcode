package com.jbi.adventofcode.advent2021.day5

import android.graphics.Point
import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader
import kotlin.math.max
import kotlin.math.min

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day5 : DailySolution2021() {
    private lateinit var data: List<Line>

    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 5
    override val expectedResultP2: Int
        get() = 12

    override fun prerunInput(reader: BufferedReader) {
        data = buildData(reader)
    }

    private fun buildData(reader: BufferedReader): List<Line> =
        mutableListOf<Line>().apply {
            reader.useLines { sequence ->
                sequence.forEach { line ->
                    val split = line.split(" -> ")
                    val p1 = split[0].split(',')
                    val p2 = split[1].split(',')
                    add(
                        Line(
                            Point(Integer.parseInt(p1[0]), Integer.parseInt(p1[1])),
                            Point(Integer.parseInt(p2[0]), Integer.parseInt(p2[1]))
                        )
                    )
                }
            }
        }

    override fun prerunSample(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun part1(reader: BufferedReader): Any {
        var matrix = Array(1000) { Array(1000) { 0 } }
        data.filter { line -> line.p1.x == line.p2.x || line.p1.y == line.p2.y }.forEach { line ->
            if (line.p1.x == line.p2.x) {
                for (y in min(line.p1.y, line.p2.y)..max(line.p1.y, line.p2.y)) {
                    matrix[line.p1.x][y]++
                }
            } else {
                for (x in min(line.p1.x, line.p2.x)..max(line.p1.x, line.p2.x)) {
                    matrix[x][line.p1.y]++
                }
            }
        }
        return matrix.sumOf { line -> line.count { value -> value >= 2 } }
    }

    override fun part2(reader: BufferedReader): Any {
        var matrix = Array(1000) { Array(1000) { 0 } }
        data.forEach { line ->
            line.apply {
                when {
                    p1.x == p2.x -> {
                        for (y in min(p1.y, p2.y)..max(p1.y, p2.y)) {
                            matrix[p1.x][y]++
                        }
                    }
                    p1.y == line.p2.y -> {
                        for (x in min(p1.x, p2.x)..max(p1.x, p2.x)) {
                            matrix[x][p1.y]++
                        }
                    }
                    else -> {
                        var firstPoint : Point
                        var secondPoint : Point
                        if(p1.x < p2.x){
                            firstPoint = p1
                            secondPoint = p2
                        }else{
                            firstPoint = p2
                            secondPoint = p1
                        }
                        var y = firstPoint.y
                        var yIncrement = if(firstPoint.y < secondPoint.y) 1 else -1
                        for(x in firstPoint.x .. secondPoint.x){
                            matrix[x][y]++
                            y+=yIncrement
                        }
                    }
                }
            }
        }
        return matrix.sumOf { line -> line.count { value -> value >= 2 } }
    }

    data class Line(val p1: Point, val p2: Point)
}