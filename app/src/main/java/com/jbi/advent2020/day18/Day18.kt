package com.jbi.advent2020.day18

import com.jbi.advent2020.DailySolution
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/18/20.
 */
object Day18 : DailySolution() {

    private lateinit var data: List<String>

    override val expectedResultP1: Any
        get() = 452L
    override val expectedResultP2: Any
        get() = 668L

    override fun tests() {
        log("------TESTS PART 1-----")
        log("test 1 = ${compute("2 * 3 + (4 * 5)") == 26L}")
        log("test 2 = ${compute("5 + (8 * 3 + 9 + 3 * 4 * 3)") == 437L}")
        log("test 3 = ${compute("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))") == 12240L}")
        log("test 4 = ${compute("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2") == 13632L}")
        log("------TESTS PART 2-----")
        log("test 1 = ${compute2("1 + (2 * 3) + (4 * (5 + 6))") == 51L}")
        log("test 2 = ${compute2("2 * 3 + (4 * 5)") == 46L}")
        log("test 3 = ${compute2("5 + (8 * 3 + 9 + 3 * 4 * 3)") == 1445L}")
        log("test 4 = ${compute2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))") == 669060L}")
        log("test 5 = ${compute2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2") == 23340L}")
    }

    override fun prerunSample(reader: BufferedReader) {
        data = readData(reader)
    }

    override fun prerunInput(reader: BufferedReader) {
        data = readData(reader)
    }

    override fun part1(reader: BufferedReader): Any {
        return data.sumOf { compute(it) }
    }

    private fun compute(s: String): Long {
        var operand = StringBuilder()
        var state: State = State.OPERAND
        var parenthesisCount = 0
        val splits = mutableListOf<Any>()
        s.forEach { c ->
            if (c != ' ') {
                when (state) {
                    State.OPERAND -> {
                        operand.append(c)
                        if (c == '(')
                            parenthesisCount++
                        else if (c == ')')
                            parenthesisCount--
                        if (parenthesisCount == 0) {//no parenthesis found or all closed
                            state = State.OPERATOR
                            if (operand.length > 1) {
                                splits.add(compute(operand.substring(1, operand.length - 1)))
                            } else {
                                splits.add(operand.toString().toLong())
                            }
                            operand.clear()
                        }
                    }
                    State.OPERATOR -> {
                        splits.add(c)
                        state = State.OPERAND
                    }
                }
            }
        }
        var index = splits.indexOfFirst { it is Char }
        var operator: Char
        while (index != -1) {
            operator = splits.removeAt(index) as Char
            splits[index - 1] = when (operator) {
                '+' -> splits[index - 1] as Long + splits.removeAt(index) as Long
                else -> splits[index - 1] as Long * splits.removeAt(index) as Long
            }
            index = splits.indexOfFirst { it is Char }
        }
        return splits[0] as Long
    }

    override fun part2(reader: BufferedReader): Any {
        return data.sumOf { compute2(it) }
    }

    private fun compute2(s: String): Long {
        var operand = StringBuilder()
        var state: State = State.OPERAND
        var parenthesisCount = 0
        val splits = mutableListOf<Any>()
        s.forEach { c ->
            if (c != ' ') {
                when (state) {
                    State.OPERAND -> {
                        operand.append(c)
                        if (c == '(')
                            parenthesisCount++
                        else if (c == ')')
                            parenthesisCount--
                        if (parenthesisCount == 0) {//no parenthesis found or all closed
                            state = State.OPERATOR
                            if (operand.length > 1) {
                                splits.add(compute2(operand.substring(1, operand.length - 1)))
                            } else {
                                splits.add(operand.toString().toLong())
                            }
                            operand.clear()
                        }
                    }
                    State.OPERATOR -> {
                        splits.add(c)
                        state = State.OPERAND
                    }
                }
            }
        }
        var index = splits.indexOf('+')
        while (index != -1) {
            splits.removeAt(index)
            splits[index - 1] = splits[index - 1] as Long + splits.removeAt(index) as Long
            index = splits.indexOf('+')
        }

        index = splits.indexOf('*')
        while (index != -1) {
            splits.removeAt(index)
            splits[index - 1] = splits[index - 1] as Long * splits.removeAt(index) as Long
            index = splits.indexOf('*')
        }
        return splits[0] as Long
    }

    private fun readData(reader: BufferedReader): List<String> =
        reader.useLines { sequence -> sequence.toList() }

    private enum class State {
        OPERAND,
        OPERATOR
    }
}
