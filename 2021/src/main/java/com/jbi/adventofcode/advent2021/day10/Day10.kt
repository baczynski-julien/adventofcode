package com.jbi.adventofcode.advent2021.day10

import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader
import java.util.*

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day10 : DailySolution2021() {

    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 26397
    override val expectedResultP2: Long
        get() = 288957

    override fun part1(reader: BufferedReader): Any {
        var score = 0
        reader.useLines { sequence ->
            sequence.forEach { line ->
                val stack = Stack<Char>()
                for (c in line) {
                    if (isOpener(c))
                        stack.add(c)
                    else if (!isCloserOf(stack.pop(), c)) {
                        score += getScore(c)
                        break
                    }
                }
            }
        }
        return score
    }

    override fun part2(reader: BufferedReader): Any {
        val scores = mutableListOf<Long>()
        reader.useLines { sequence ->
            lineLoop@ for(line in sequence){
                val stack = Stack<Char>()
                for (c in line) {
                    if (isOpener(c))
                        stack.add(c)
                    else if (!isCloserOf(stack.pop(), c)) {
                        continue@lineLoop
                    }
                }

                if (stack.size > 0) {
                    stack.reverse()
                    var score = 0L
                    for (c in stack) {
                        score *= 5
                        score += getScoreP2(c)
                    }
                    scores.add(score)
                }
            }
        }
        return scores.sorted()[scores.size /2]
    }

    val openers = listOf('(', '[', '{', '<')
    val closers = listOf(')', ']', '}', '>')
    val scores = listOf(3, 57, 1197, 25137)
    fun isOpener(char: Char): Boolean = openers.contains(char)
    fun isCloserOf(opener: Char, closer: Char) = closers.indexOf(closer) == openers.indexOf(opener)
    fun getScore(closer: Char) = scores[closers.indexOf(closer)]
    fun getScoreP2(opener: Char) = openers.indexOf(opener) + 1
}