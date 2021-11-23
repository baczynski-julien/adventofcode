package com.jbi.adventofcode.advent2020.day8

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader
import java.lang.Exception
import java.util.*

/**
 * @author Julien BACZYNSKI on 12/8/20.
 */
object Day8 : DailySolution2020() {

    private lateinit var data: List<Instruction>
    override val expectedResultP1: Any
        get() = 5
    override val expectedResultP2: Any
        get() = 8

    override fun prerunInput(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun part1(reader: BufferedReader): Any = executeBootSequence(data, false)

    override fun part2(reader: BufferedReader): Any {
        var culprit = 0
        var iterations = 0
        var acc = 0
        val suspiciousIndexes = extractSuspectsFromBootSequence(data)
        while (suspiciousIndexes.isNotEmpty()) {
            iterations++
            data[suspiciousIndexes.peek()].revert()
            try{
                acc =  executeBootSequence(data)
                data[suspiciousIndexes.peek()].revert()
                culprit = suspiciousIndexes.pop()
                log("culprit $culprit found after $iterations iterations")
                return acc
            }catch(exception : BootSequenceException){
                data[suspiciousIndexes.peek()].revert()
                suspiciousIndexes.pop()
            }
        }
        return -1
    }

    private fun executeBootSequence(bootSequence: List<Instruction>, strict : Boolean = true): Int {
        val visitedIndexes = mutableListOf<Int>()
        var acc = 0
        var current = 0
        var currentInstruction: Instruction
        do {
            currentInstruction = bootSequence[current]
            visitedIndexes.add(current)
            when (currentInstruction.op) {
                Operation.NOP -> current++
                Operation.ACC -> {
                    acc += currentInstruction.value
                    current++
                }
                Operation.JMP -> current += currentInstruction.value
            }
        } while (current < bootSequence.size && !visitedIndexes.contains(current))
        if(strict&& current < bootSequence.size)
            throw BootSequenceException("Incorrect boot sequence")
        return acc
    }


    private fun extractSuspectsFromBootSequence(bootSequence: List<Instruction>): Stack<Int> {
        val visitedIndexes = mutableListOf<Int>()
        var current = 0
        var currentInstruction: Instruction
        val suspiciousIndexes = Stack<Int>()
        do {
            currentInstruction = bootSequence[current]
            visitedIndexes.add(current)
            when (currentInstruction.op) {
                Operation.NOP -> {
                    suspiciousIndexes.add(current)
                    current++
                }
                Operation.ACC -> {
                    current++
                }
                Operation.JMP -> {
                    suspiciousIndexes.add(current)
                    current += currentInstruction.value
                }
            }
        } while (current < bootSequence.size && !visitedIndexes.contains(current))
        return suspiciousIndexes
    }

    private fun buildData(reader: BufferedReader) = reader.useLines { seq ->
        seq.fold(mutableListOf<Instruction>()) { list, line ->
            val split = line.split(" ")
            list.add(
                Instruction(
                    Operation.valueOf(split[0].uppercase()),
                    Integer.parseInt(split[1])
                )
            )
            list
        }
    }

    data class Instruction(var op: Operation, val value: Int) {
        fun revert() {
            op = when (op) {
                Operation.NOP -> Operation.JMP
                Operation.ACC -> Operation.ACC
                Operation.JMP -> Operation.NOP
            }
        }
    }

    enum class Operation {
        NOP,
        ACC,
        JMP
    }
}

class BootSequenceException(message: String) : Exception()
