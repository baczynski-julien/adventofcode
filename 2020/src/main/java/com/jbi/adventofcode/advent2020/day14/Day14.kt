package com.jbi.adventofcode.advent2020.day14

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader
import kotlin.math.max

/**
 * @author Julien BACZYNSKI on 12/13/20.
 */
object Day14 : DailySolution2020() {

    override val runWithInput: Boolean
        get() = true
    override val expectedResultP1: Any
        get() = 51L
    override val expectedResultP2: Any
        get() = 208L

    override fun part1(reader: BufferedReader): Any {
        val memory = mutableMapOf<Long, Long>()
        var maskOr = 0L
        var maskAnd = 1L
        var maxX = 0
        reader.useLines { sequence ->
            sequence.forEach {
                if (it[1] == 'e') {
                    var indexOfClosing = it.indexOf(']')
                    memory[it.substring(4, indexOfClosing).toLong()] =
                        it.substring(indexOfClosing + 4).toLong() and maskAnd or maskOr
                } else {
                    val mask = it.substring(7)
                    maxX = max(maxX, mask.count { c-> c == 'X' })
                    maskAnd = mask.replace('X', '1').toLong(2)
                    maskOr = mask.replace('X', '0').toLong(2)
                }
            }
        }
        log("maxX: $maxX")
        return memory.values.sum()
    }

    override fun part2(reader: BufferedReader): Any {
        val memory = mutableMapOf<Long, Long>()
        var mask = ""
        reader.useLines { sequence ->
            sequence.forEach {
                if (it[1] == 'e') {
                    //apply mask to index
                    val indexOfClosing = it.indexOf(']')
                    val indexString =it.substring(4, indexOfClosing).toLong().toString(2)
                    val sb = StringBuilder(mask)
                    sb.forEachIndexed { index, char ->
                        if(char == '0') {
                            sb[index] = if(36- index > indexString.length) '0' else indexString[indexString.length-(36- index)]
                        }
                    }
                    val numberToWrite = it.substring(indexOfClosing + 4).toLong()
                    buildPermutations(sb.toString()).forEach { address ->
                        memory[address.toLong(2)] = numberToWrite
                    }

                    /*memory[] =
                        it.substring(indexOfClosing + 4).toLong() and maskAnd or maskOr*/
                } else {
                    mask = it.substring(7)
                }
            }
        }
        return memory.values.sum()
    }

    private fun buildPermutations(number: String): List<String> {
        val list = mutableListOf<String>()
        val indexOfX = number.indexOf('X')
        if(indexOfX != -1) {
            list.addAll(buildPermutations(number.replaceFirst('X', '1')))
            list.addAll(buildPermutations(number.replaceFirst('X', '0')))
        }else{
            list.add(number)
        }
        return list
    }


}
