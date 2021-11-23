package com.jbi.adventofcode.advent2020.day6

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/6/20.
 */
object Day6 : DailySolution2020() {

    override val expectedResultP1: Any
        get() = 11
    override val expectedResultP2: Any
        get() = 6

    override fun part1(reader: BufferedReader): Any {
        return buildData(reader).sumOf { it.size }
    }

    override fun part2(reader: BufferedReader): Any {
        return buildData2(reader).sumOf { it.size }
    }

    private fun buildData(reader: BufferedReader): List<Set<Char>> {
        val list = mutableListOf<MutableSet<Char>>()
        list.add(mutableSetOf())
        reader.useLines { sequence: Sequence<String> ->
            sequence.forEach {
                if (it.isBlank()) {
                    list.add(mutableSetOf())
                } else {
                    list.last().addAll(it.toCharArray().toList())
                }
            }
        }
        return list
    }

    private fun buildData2(reader: BufferedReader): List<Set<Char>> {
        val list = mutableListOf<Set<Char>>()
        var currentSet : Set<Char>? = null
        reader.useLines { sequence: Sequence<String> ->
            sequence.forEach {
                currentSet = if (it.isBlank()) {
                    list.add(currentSet!!)
                    null
                } else {
                    if (currentSet == null)
                        it.toCharArray().toSet()
                    else
                        currentSet!!.intersect(it.toCharArray().toSet())
                }
            }
        }
        list.add(currentSet!!)
        return list
    }

}
