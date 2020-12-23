package com.jbi.advent2020.day3

import android.content.Context
import com.jbi.advent2020.DailySolution
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/3/20.
 */

object Day3 : DailySolution() {

    private lateinit var matrix: List<CharArray>

    override val expectedResultP1: Int
        get() = 7
    override val expectedResultP2: Long
        get() = 336

    override fun prerunInput(reader: BufferedReader) {
        matrix = buildMatrix(reader)
    }
    override fun prerunSample(reader: BufferedReader) {
        matrix = buildMatrix(reader)
    }
    override fun part1(reader: BufferedReader): Any = analyzeTree(matrix, 1, 3)

    override fun part2(reader: BufferedReader): Any {
        var trees = 1L
        trees *= analyzeTree(matrix, 1, 1)
        trees *= analyzeTree(matrix, 1, 3)
        trees *= analyzeTree(matrix, 1, 5)
        trees *= analyzeTree(matrix, 1, 7)
        trees *= analyzeTree(matrix, 2, 1)
        return trees
    }

    private fun buildMatrix(reader: BufferedReader): List<CharArray> {
        var list = mutableListOf<CharArray>()
        reader.useLines { sequence: Sequence<String> ->
                sequence.forEach {
                    list.add(it.toCharArray())
                }
            }
        return list
    }

    private fun analyzeTree(matrix: List<CharArray>, incVertical: Int, incHorizontal: Int): Int {
        var currentPos = 0
        var trees = 0
        for (i in matrix.indices step incVertical) {
            if (matrix[i][currentPos] == '#')
                trees++
            currentPos =
                if (currentPos + incHorizontal >= matrix[i].size) currentPos + incHorizontal - matrix[i].size else currentPos + incHorizontal
        }
        return trees
    }

}