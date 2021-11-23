package com.jbi.adventofcode.advent2020.day24

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader
import java.util.*
import java.util.regex.Pattern
import kotlin.math.absoluteValue

/**
 * @author Julien BACZYNSKI on 12/24/20.
 */
object Day24 : DailySolution2020() {

    private lateinit var initialState: Array<Array<Boolean>>
    override val runWithInput: Boolean
        get() = true
    override val expectedResultP1: Any
        get() = 10
    override val expectedResultP2: Any
        get() = 2208

    private var pattern = Pattern.compile("(se|sw|w|nw|ne|e)")
    private lateinit var transformations: List<List<Direction>>

    override fun prerunInput(reader: BufferedReader) {
        transformations = readData(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        transformations = readData(reader)
    }

    private fun readData(reader: BufferedReader): List<List<Direction>> =
        reader.useLines {
            it.map { line ->
                mutableListOf<Direction>().apply {
                    val matcher = pattern.matcher(line)
                    while (matcher.find())
                        add(Direction.valueOf(matcher.group().toUpperCase(Locale.ROOT)))
                }
            }.toList()
        }

    /*
    se se nw ne ne ne w se e sw w sw sw w ne ne w se w sw
     */
    override fun part1(reader: BufferedReader): Any {
        initialState = Array(300) { Array(300) { false } }
        transformations.forEach { transfo ->
            var i = 150
            var j = 150
            transfo.forEach { direction ->
                when (direction) {
                    Direction.E -> i++
                    Direction.SE -> {
                        i += j.absoluteValue % 2
                        j++
                    }
                    Direction.SW -> {
                        i -= (j.absoluteValue + 1) % 2
                        j++
                    }
                    Direction.W -> i--
                    Direction.NW -> {
                        i -= (j.absoluteValue + 1) % 2
                        j--
                    }
                    Direction.NE -> {
                        i += j.absoluteValue % 2
                        j--
                    }
                }
            }
            initialState[i][j] = !initialState[i][j]
        }
        return countOn(initialState)
    }

    override fun part2(reader: BufferedReader): Any {
        var newState = initialState
        for (i in 1..100) {
            newState = runCycle(newState)
        }
        return countOn(newState)
    }

    private fun runCycle(floor: Array<Array<Boolean>>): Array<Array<Boolean>> {
        val newFloor = Array(floor.size) { Array(floor.size) { false } }
        for (x in newFloor.indices)
            for (y in newFloor.indices)
                newFloor[x][y] = when (floor[x][y]) {
                    true -> applyActiveRule(floor, x, y)
                    false -> applyInactiveRule(floor, x, y)
                }
        return newFloor
    }

    private fun applyActiveRule(
        floor: Array<Array<Boolean>>,
        x: Int,
        y: Int
    ): Boolean {
        val activeNeigh = countActiveNeighbors(floor, x, y)
        return !(activeNeigh == 0 || activeNeigh > 2)
    }

    private fun applyInactiveRule(
        floor: Array<Array<Boolean>>,
        x: Int,
        y: Int
    ): Boolean = countActiveNeighbors(floor, x, y) == 2

    private fun countActiveNeighbors(
        floor: Array<Array<Boolean>>,
        x: Int,
        y: Int
    ): Int {
        var count = 0
        val max = floor.size - 1
        //E
        var testX: Int = x + 1
        var testY: Int = y
        if (testX in 0..max && testY in 0..max && floor[testX][testY]) count++

        //W
        testX = x - 1
        testY = y
        if (testX in 0..max && testY in 0..max && floor[testX][testY]) count++

        //SE
        testX = x + (y.absoluteValue % 2)
        testY = y + 1
        if (testX in 0..max && testY in 0..max && floor[testX][testY]) count++

        //SW
        testX = x - ((y.absoluteValue + 1) % 2)
        testY = y + 1
        if (testX in 0..max && testY in 0..max && floor[testX][testY]) count++

        //NW
        testX = x - ((y.absoluteValue + 1) % 2)
        testY = y - 1
        if (testX in 0..max && testY in 0..max && floor[testX][testY]) count++

        //NE
        testX = x + (y.absoluteValue % 2)
        testY = y - 1
        if (testX in 0..max && testY in 0..max && floor[testX][testY]) count++

        return count
    }

    private fun countOn(tiles: Array<Array<Boolean>>): Any {
        var count = 0
        tiles.forEach { line ->
            line.forEach { color -> if (color) count++ }
        }
        return count
    }


    enum class Direction {
        E,
        SE,
        SW,
        W,
        NW,
        NE
    }
}
