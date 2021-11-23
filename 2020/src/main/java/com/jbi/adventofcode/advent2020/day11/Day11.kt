package com.jbi.adventofcode.advent2020.day11

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader
import kotlin.math.max
import kotlin.math.min

/**
 * @author Julien BACZYNSKI on 12/11/20.
 */
object Day11 : DailySolution2020() {

    override val expectedResultP1: Any
        get() = 37
    override val expectedResultP2: Any
        get() = 26

    private lateinit var layout: Array<Array<Slot>>

    override fun prerunInput(reader: BufferedReader) {
        layout = readData(reader)
    }
    override fun prerunSample(reader: BufferedReader) {
        layout = readData(reader)
    }
    override fun part1(reader: BufferedReader): Any {
        var previouslyOccupied = 0
        var newLayout = applyRules(layout, Day11::applyEmptyRule, Day11::applyOccupiedRule)
        while (newLayout.second != previouslyOccupied) {
            previouslyOccupied = newLayout.second
            newLayout = applyRules(newLayout.first, Day11::applyEmptyRule, Day11::applyOccupiedRule)
        }
        return newLayout.second
    }

    override fun part2(reader: BufferedReader): Any {
        var previouslyOccupied = 0
        log("------ STATE 0 ------")
        logMatrix(layout)
        var newLayout = applyRules(layout, Day11::applyEmptyRule2, Day11::applyOccupiedRule2)
        log("------ STATE 1 ------")
        logMatrix(newLayout.first)
        newLayout = applyRules(newLayout.first, Day11::applyEmptyRule2, Day11::applyOccupiedRule2)
        log("------ STATE 2 ------")
        logMatrix(newLayout.first)
        newLayout = applyRules(newLayout.first, Day11::applyEmptyRule2, Day11::applyOccupiedRule2)
        log("------ STATE 3 ------")
        logMatrix(newLayout.first)
        newLayout = applyRules(newLayout.first, Day11::applyEmptyRule2, Day11::applyOccupiedRule2)
        log("------ STATE 4 ------")
        logMatrix(newLayout.first)
        while (newLayout.second != previouslyOccupied) {
            previouslyOccupied = newLayout.second
            newLayout = applyRules(newLayout.first, Day11::applyEmptyRule2, Day11::applyOccupiedRule2)
        }
        return newLayout.second
    }

    private fun applyRules(
        matrix: Array<Array<Slot>>,
        emptyRuleFunction: (Array<Array<Slot>>, Int, Int, Slot) -> Slot,
        occupiedRuleMethod: (Array<Array<Slot>>, Int, Int, Slot) -> Slot
    ): Pair<Array<Array<Slot>>, Int> {
        //val result = Array(matrix.size) { Array(matrix[0].size) { Slot.EMPTY } }
        var occupied = 0
        val matrixResult = matrix.mapIndexed { i, line ->
            line.mapIndexed { j, slot ->
                when (slot) {
                    Slot.FLOOR -> slot
                    Slot.EMPTY ->
                        emptyRuleFunction(matrix, i, j, slot).apply {
                            if (this == Slot.OCCUPIED)
                                occupied++
                        }
                    Slot.OCCUPIED ->
                        occupiedRuleMethod(matrix, i, j, slot).apply {
                            if (this == Slot.OCCUPIED)
                                occupied++
                        }
                }
            }.toTypedArray()
        }.toTypedArray()
        return Pair(matrixResult, occupied)
    }

    /**
     * If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
     */
    private fun applyEmptyRule(
        matrix: Array<Array<Slot>>,
        x: Int,
        y: Int,
        slot: Slot
    ): Slot = if (readOccupiedAround(matrix, x, y) == 0) Slot.OCCUPIED else slot


    /**
     * If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
     */
    private fun applyOccupiedRule(
        matrix: Array<Array<Slot>>,
        x: Int,
        y: Int,
        slot: Slot
    ): Slot = if (readOccupiedAround(matrix, x, y) >= 4) Slot.EMPTY else slot

    private fun readOccupiedAround(matrix: Array<Array<Slot>>, x: Int, y: Int): Int {
        //[x-1; y-1] [x-1; y] [x-1; y+1]
        //[x; y-1]            [x; y+1]
        //[x+1; y-1]  [x; y]  [x+1; y+1]
        val maxX = min(x + 1, matrix.size - 1)
        val maxY = min(y + 1, matrix[0].size - 1)
        val minX = max(0, x - 1)
        val minY = max(0, y - 1)
        var occupied = 0
        for (i in minX..maxX) {
            for (j in minY..maxY) {
                if (i == x && j == y)
                    continue
                if (matrix[i][j] == Slot.OCCUPIED)
                    occupied++
            }
        }
        return occupied
    }

    /**
     * If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
     */
    private fun applyEmptyRule2(
        matrix: Array<Array<Slot>>,
        x: Int,
        y: Int,
        slot: Slot
    ): Slot = if (readOccupiedAllDirection(matrix, x, y) == 0) Slot.OCCUPIED else slot


    /**
     * If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
     */
    private fun applyOccupiedRule2(
        matrix: Array<Array<Slot>>,
        x: Int,
        y: Int,
        slot: Slot
    ): Slot = if (readOccupiedAllDirection(matrix, x, y) >= 5) Slot.EMPTY else slot

    private fun readOccupiedAllDirection(matrix: Array<Array<Slot>>, x: Int, y: Int): Int {
        return findSeatDirection(matrix, x, y, 0, 1) +
                findSeatDirection(matrix, x, y, 0, -1) +
                findSeatDirection(matrix, x, y, 1, 0) +
                findSeatDirection(matrix, x, y, -1, 0) +
                findSeatDirection(matrix, x, y, 1, 1) +
                findSeatDirection(matrix, x, y, -1, -1) +
                findSeatDirection(matrix, x, y, -1, 1) +
                findSeatDirection(matrix, x, y, 1, -1)
    }

    private fun findSeatDirection(
        matrix: Array<Array<Slot>>,
        x: Int,
        y: Int,
        xInc: Int,
        yInc: Int
    ): Int {
        var i = x + xInc
        var j = y + yInc
        while (i in matrix.indices && j in matrix[0].indices) {
            if (matrix[i][j] == Slot.OCCUPIED)
                return 1
            if (matrix[i][j] == Slot.EMPTY)
                return 0
            i += xInc
            j += yInc
        }
        return 0
    }

    private fun logMatrix(matrix: Array<Array<Slot>>) =
        matrix.forEach { log(it.fold(StringBuilder()) { b, c -> b.append(c.toString()) }.toString()) }

    private fun readData(reader: BufferedReader): Array<Array<Slot>> {
        return reader.useLines { seq ->
            seq.map { line ->
                readLine(line)
            }.toList().toTypedArray()
        }
    }

    private fun readLine(line: String): Array<Slot> {
        return line.map { c ->
            when (c) {
                '.' -> Slot.FLOOR
                else -> Slot.EMPTY
            }
        }.toTypedArray()
    }

    enum class Slot {
        FLOOR,
        EMPTY,
        OCCUPIED;

        override fun toString(): String = when (this) {
            FLOOR -> "."
            EMPTY -> "L"
            OCCUPIED -> "#"
        }
    }
}
