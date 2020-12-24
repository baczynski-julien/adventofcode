package com.jbi.advent2020.day17

import com.jbi.advent2020.DailySolution
import java.io.BufferedReader
import kotlin.math.max
import kotlin.math.min

/**
 * @author Julien BACZYNSKI on 12/17/20.
 */
object Day17 : DailySolution() {

    private lateinit var initialState4D: Array<Array<Array<Array<Boolean>>>>
    private lateinit var initialState: Array<Array<Array<Boolean>>>
    private const val ITERATIONS = 6
    override val runWithInput: Boolean
        get() = true
    override val expectedResultP1: Any
        get() = 112
    override val expectedResultP2: Any
        get() = 848

    override fun prerunInput(reader: BufferedReader) {
        readData(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        readData(reader)
    }

    override fun part1(reader: BufferedReader): Any {
        var newState = initialState
        for (i in 1..ITERATIONS) {
            newState = runCycle(newState)
        }
        return countActive(newState)
    }

    override fun part2(reader: BufferedReader): Any {
        var newState = initialState4D
        for (i in 1..ITERATIONS) {
            newState = runCycle4D(newState)
        }
        return countActive4D(newState)
    }

    private fun runCycle(cube: Array<Array<Array<Boolean>>>): Array<Array<Array<Boolean>>> {
        var newCube = Array(cube.size) { Array(cube.size) { Array(cube.size) { false } } }
        for (x in newCube.indices)
            for (y in newCube.indices)
                for (z in newCube.indices) {
                    newCube[x][y][z] = when (cube[x][y][z]) {
                        true -> applyActiveRule(cube, x, y, z)
                        false -> applyInactiveRule(cube, x, y, z)
                    }
                }
        return newCube
    }

    private fun runCycle4D(hyperCube: Array<Array<Array<Array<Boolean>>>>): Array<Array<Array<Array<Boolean>>>> {
        var newHyperCube =
            Array(hyperCube.size) { Array(hyperCube.size) { Array(hyperCube.size) { Array(hyperCube.size) { false } } } }
        for (x in newHyperCube.indices)
            for (y in newHyperCube.indices)
                for (z in newHyperCube.indices) {
                    for (w in newHyperCube.indices) {
                        newHyperCube[x][y][z][w] = when (hyperCube[x][y][z][w]) {
                            true -> applyActiveRule4D(hyperCube, x, y, z, w)
                            false -> applyInactiveRule4D(hyperCube, x, y, z, w)
                        }
                    }
                }
        return newHyperCube
    }

    /**
     *  If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
     */
    private fun applyActiveRule(
        cube: Array<Array<Array<Boolean>>>,
        x: Int,
        y: Int,
        z: Int
    ): Boolean {
        val activeNeigh = countActiveNeighbors(cube, x, y, z)
        return (activeNeigh == 2 || activeNeigh == 3)
    }

    private fun applyActiveRule4D(
        hyperCube: Array<Array<Array<Array<Boolean>>>>,
        x: Int,
        y: Int,
        z: Int,
        w: Int
    ): Boolean {
        val activeNeigh = countActiveNeighbors4D(hyperCube, x, y, z, w)
        return (activeNeigh == 2 || activeNeigh == 3)
    }

    /**
     *  If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.
     */
    private fun applyInactiveRule(
        cube: Array<Array<Array<Boolean>>>,
        x: Int,
        y: Int,
        z: Int
    ): Boolean = countActiveNeighbors(cube, x, y, z) == 3

    private fun applyInactiveRule4D(
        hyperCube: Array<Array<Array<Array<Boolean>>>>,
        x: Int,
        y: Int,
        z: Int,
        w: Int
    ): Boolean = countActiveNeighbors4D(hyperCube, x, y, z, w) == 3

    private fun countActiveNeighbors(
        cube: Array<Array<Array<Boolean>>>,
        x: Int,
        y: Int,
        z: Int
    ): Int {
        var count = 0
        for (nx in max(x - 1, 0)..min(x + 1, cube.size - 1))
            for (ny in max(y - 1, 0)..min(y + 1, cube.size - 1))
                for (nz in max(z - 1, 0)..min(z + 1, cube.size - 1)) {
                    if (cube[nx][ny][nz] && (nx != x || ny != y || nz != z)) {
                        count++
                    }
                }
        return count
    }

    private fun countActiveNeighbors4D(
        cube: Array<Array<Array<Array<Boolean>>>>,
        x: Int,
        y: Int,
        z: Int,
        w: Int
    ): Int {
        var count = 0
        for (nx in max(x - 1, 0)..min(x + 1, cube.size - 1))
            for (ny in max(y - 1, 0)..min(y + 1, cube.size - 1))
                for (nz in max(z - 1, 0)..min(z + 1, cube.size - 1))
                    for (nw in max(w - 1, 0)..min(w + 1, cube.size - 1)) {
                        if (cube[nx][ny][nz][nw] && (nx != x || ny != y || nz != z || nw != w)) {
                            count++
                        }
                    }
        return count
    }

    private fun countActive(cube: Array<Array<Array<Boolean>>>): Int {
        var count = 0
        for (x in cube.indices)
            for (y in cube.indices)
                for (z in cube.indices)
                    if (cube[x][y][z])
                        count++
        return count
    }

    private fun countActive4D(cube: Array<Array<Array<Array<Boolean>>>>): Int {
        var count = 0
        for (x in cube.indices)
            for (y in cube.indices)
                for (z in cube.indices)
                    for (w in cube.indices)
                        if (cube[x][y][z][w])
                            count++
        return count
    }

    private fun readData(reader: BufferedReader): Array<Array<Array<Array<Boolean>>>> {
        return reader.useLines { sequence ->
            var startX = 0
            var startY = 0
            var startZ = 0
            var startW = 0
            sequence.forEachIndexed { y, line ->
                if (y == 0) {
                    val size = line.length + ITERATIONS * 2
                    initialState = Array(size) { Array(size) { Array(size) { false } } }
                    initialState4D = Array(size) { Array(size) { Array(size) { Array(size) { false } } } }
                    startX = (size - 1) / 2 - 1
                    startY = (size - 1) / 2 - 1
                    startZ = (size - 1) / 2
                    startW = (size - 1) / 2
                }
                line.forEachIndexed { x, charac ->
                    initialState[startX + x][startY + y][startZ] = when (charac) {
                        '#' -> true
                        else -> false
                    }
                    initialState4D[startX + x][startY + y][startZ][startW] = when (charac) {
                        '#' -> true
                        else -> false
                    }
                }
            }
            initialState4D
        }
    }
}
