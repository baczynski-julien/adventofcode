package com.jbi.adventofcode.advent2021.day8

import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day8 : DailySolution2021() {

    private lateinit var data: List<Display>
    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 26
    override val expectedResultP2: Long
        get() = 61229

    private val easyDigits = listOf(2, 4, 3, 7)

    override fun part1(reader: BufferedReader): Any =
        data.sumOf { display -> display.outputs.count { easyDigits.contains(it.length) } }

    override fun part2(reader: BufferedReader): Any = data.sumOf { it.solve() }

    override fun prerunInput(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        data = buildData(reader)
    }

    private fun buildData(reader: BufferedReader): List<Display> =
        mutableListOf<Display>().apply {
            reader.useLines { sequence ->
                sequence.forEach { line ->
                    val split = line.split(" | ")
                    add(
                        Display(
                            split[0].split(' '),
                            split[1].split(' ')
                        )
                    )
                }
            }
        }

    val matching: Map<Int, List<Char>> = mutableMapOf<Int, List<Char>>().apply {
        put(0, listOf('a', 'b', 'c', 'e', 'f', 'g'))
        put(1, listOf('c', 'f'))
        put(2, listOf('a', 'c', 'd', 'e', 'g'))
        put(3, listOf('a', 'c', 'd', 'f', 'g'))
        put(4, listOf('b', 'c', 'd', 'f'))
        put(5, listOf('a', 'b', 'd', 'f', 'g'))
        put(6, listOf('a', 'b', 'd', 'e', 'f', 'g'))
        put(7, listOf('a', 'c', 'f'))
        put(8, listOf('a', 'b', 'c', 'd', 'e', 'f', 'g'))
        put(9, listOf('a', 'b', 'c', 'd', 'f', 'g'))
    }
    val allChars = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g')

    class Display(val digits: List<String>, val outputs: List<String>) {
        fun solve(): Int {
            val mapping: MutableMap<Char, MutableList<Char>> = mutableMapOf<Char, MutableList<Char>>().apply{
                put('a', allChars.toMutableList())
                put('b', allChars.toMutableList())
                put('c', allChars.toMutableList())
                put('d', allChars.toMutableList())
                put('e', allChars.toMutableList())
                put('f', allChars.toMutableList())
                put('g', allChars.toMutableList())
            }
            val digit1 = digits.first { it.length == 2 }
            val digit7 = digits.first { it.length == 3 }
            val digit4 = digits.first { it.length == 4 }

            //targetting a
            mapping['a'] = mutableListOf(digit7.first{ !digit1.contains(it)})
            removeMapping(mapping, 'a')
            //candidates from 1
            mapping['c']!!.removeAll { !digit1.contains(it) }
            mapping['f']!!.removeAll { !digit1.contains(it) }
            //candidates from 4
            mapping['b'] = mutableListOf<Char>().apply{
                addAll(digit4.filter {!digit1.contains(it) }.toList())
            }
            mapping['d'] = mutableListOf<Char>().apply{
                addAll(digit4.filter {!digit1.contains(it) }.toList())
            }

            //targeting d and g
            val cdigits = digits.filter { it.length == 5 } // we got our 2, 3 and 5
            val candidates = allChars.filter { cdigits.all { dig -> dig.contains(it) }  && it != mapping['a']!![0]}
            mapping['d']!!.removeAll { !candidates.contains(it) }
            mapping['g'] = candidates.filter { it != mapping['d']!![0] }.toMutableList()
            removeMapping(mapping, 'd')
            removeMapping(mapping, 'g')
            removeMapping(mapping, 'b')

            //targeting c

            mapping['e']!!.removeAll { mapping['c']!!.contains(it) }
            removeMapping(mapping,'e')

            val digit6 = digits.filter { it.length == 6 }.filter {
                it.contains(mapping['a']!![0]) &&
                        it.contains(mapping['b']!![0]) &&
                        it.contains(mapping['d']!![0]) &&
                        it.contains(mapping['e']!![0]) &&
                        it.contains(mapping['g']!![0])
            }[0] // we got our 6

            mapping['f'] = digit6.filter { mapping['f']!!.contains(it) }.toMutableList()
            removeMapping(mapping, 'f')
           /* val finalMap = mutableMapOf<Char, Char>().apply{
                mapping.forEach{ entry ->
                    put(entry.value[0], entry.key)
                }
            }*/
            val mMatchings = mutableMapOf<String, Int>().apply{
                matching.forEach { matching ->
                    put(matching.value.map { mapping[it]!![0] }.sorted().joinToString(""), matching.key )
                }
            }

            val result = StringBuilder()
            outputs.forEach {
                result.append(mMatchings[it.toSortedSet().joinToString("")])
            }
            return Integer.parseInt(result.toString() )
        }

        private fun removeMapping(mapping: MutableMap<Char, MutableList<Char>>, char: Char) {
            mapping.forEach { entry ->
                if(entry.key != char)
                    entry.value.remove(mapping[char]!![0])
            }
        }
    }


}