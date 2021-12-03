package com.jbi.adventofcode.advent2021.day3

import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day3 : DailySolution2021() {

    private lateinit var data: List<String>
    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 198
    override val expectedResultP2: Long
        get() = 230

    override fun prerunInput(reader: BufferedReader) {
        data = buildInput(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        data = buildInput(reader)
    }

    override fun part1(reader: BufferedReader): Any {
        var gamma = ""
        var epsilon = ""
        reader.useLines { sequence ->
            var work: Array<Int>? = null
            sequence.forEach { line ->
                if (work == null)
                    work = Array(line.length) { 0 }
                line.forEachIndexed { i, bit ->
                    work?.let {
                        if (bit == '1')
                            it[i]++
                        else
                            it[i]--
                    }
                }
            }

            work?.forEach {
                if (it > 0) {
                    gamma += "1"
                    epsilon += "0"
                } else {
                    gamma += "0"
                    epsilon += "1"
                }
            }

        }
        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2)
    }

    override fun part2(reader: BufferedReader): Any {
        return findOxygen() * findCo2()
    }

    private fun findOxygen(): Int {
        var mem = data.toMutableList()
        var i=0
        do{
            mem = if(mem.count { it[i] == '1' }>= mem.size /2f)
                mem.filter { it[i] == '1' }.toMutableList()
            else
                mem.filter { it[i] == '0' }.toMutableList()
            i++
        }while(mem.size>1)

        return Integer.parseInt(mem[0], 2)
    }

    private fun findCo2(): Int {
        var mem = data.toMutableList()
        var i=0
        do{
            mem = if(mem.count { it[i] == '0' }<= mem.size /2f)
                mem.filter { it[i] == '0' }.toMutableList()
            else
                mem.filter { it[i] == '1' }.toMutableList()
            i++
        }while(mem.size>1)

        return Integer.parseInt(mem[0], 2)
    }

    private fun buildInput(reader: BufferedReader): List<String> =
        mutableListOf<String>().apply {
            reader.useLines { sequence: Sequence<String> ->
                sequence.forEach {
                    add(it)
                }
            }
        }

}