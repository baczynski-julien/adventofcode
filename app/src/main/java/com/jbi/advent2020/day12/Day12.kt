package com.jbi.advent2020.day12

import com.jbi.advent2020.DailySolution
import java.io.BufferedReader
import kotlin.math.absoluteValue

/**
 * @author Julien BACZYNSKI on 12/12/20.
 */
object Day12 : DailySolution() {

    override val expectedResultP1: Any
        get() = 25
    override val expectedResultP2: Any
        get() = 286

    private lateinit var input: Array<Pair<Char, Int>>

    override fun prerunInput(reader: BufferedReader) {
        input = readInput(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        input = readInput(reader)
    }
    override fun part1(reader: BufferedReader): Any {
        return extractManhattanDistance(input, 'E')
    }

    override fun part2(reader: BufferedReader): Any {
        return extractManhattanDistanceWithWaypoint(input, 10, 1)
    }

    private fun extractManhattanDistanceWithWaypoint(
        input: Array<Pair<Char, Int>>,
        statingWpEW: Int,
        startingWpNS: Int
    ): Any {
        var wpEW = statingWpEW
        var wpNS = startingWpNS
        var EW = 0
        var NS = 0
        var pair: Pair<Int, Int>
        input.forEach { action ->
            when (action.first) {
                'N' -> wpNS += action.second
                'S' -> wpNS -= action.second
                'E' -> wpEW += action.second
                'W' -> wpEW -= action.second
                'L' -> {
                    pair = rotateWaypoint(wpEW, wpNS, 360 - (action.second % 360))
                    wpEW = pair.first
                    wpNS = pair.second
                }
                'R' -> {
                    pair = rotateWaypoint(wpEW, wpNS, action.second % 360)
                    wpEW = pair.first
                    wpNS = pair.second
                }
                'F' -> {
                    EW += wpEW * action.second
                    NS += wpNS * action.second
                }
            }
        }
        return EW.absoluteValue + NS.absoluteValue
    }

    private fun rotateWaypoint(x: Int, y: Int, degrees: Int): Pair<Int, Int> {
        var resultX: Int = x
        var resultY: Int = y
        for (i in 0 until degrees / 90) {//each 90% we negate X and  invert values, we know we only rotate by steps of 90% so division is ok
            resultY = -resultX.also { resultX = resultY }
        }
        return Pair(resultX, resultY)
    }

    private fun extractManhattanDistance(
        input: Array<Pair<Char, Int>>,
        startingDirection: Char
    ): Int {
        var EW = 0
        var NS = 0
        var currentDirection = startingDirection

        input.forEach { action ->
            var direction = if (action.first == 'F') currentDirection else action.first
            when (direction) {
                'N' -> NS += action.second
                'S' -> NS -= action.second
                'E' -> EW += action.second
                'W' -> EW -= action.second
                'L' -> currentDirection = turnLeft(currentDirection, action.second % 360)
                'R' -> currentDirection = turnRight(currentDirection, action.second % 360)
            }
        }
        return EW.absoluteValue + NS.absoluteValue
    }


    private fun turnRight(currentDirection: Char, degrees: Int): Char =
        Directions.values()[((Directions.valueOf(currentDirection.toString()).ordinal + (degrees / 90)) % 4)].name[0]


    private fun turnLeft(currentDirection: Char, degrees: Int): Char =
        Directions.values()[((Directions.valueOf(currentDirection.toString()).ordinal + (4 - (degrees / 90))) % 4)].name[0]

    private fun readInput(reader: BufferedReader): Array<Pair<Char, Int>> =
        reader.useLines { sequence ->
            sequence.map { line ->
                Pair(
                    line[0],
                    line.substring(1).toInt()
                )
            }.toList().toTypedArray()
        }


    private enum class Directions {
        E,
        S,
        W,
        N
    }

}
