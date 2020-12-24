package com.jbi.advent2020.day16

import com.jbi.advent2020.DailySolution
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/16/20.
 */
object Day16 : DailySolution() {

    override val runWithInput: Boolean
        get() = true
    override val expectedResultP1: Any
        get() = 71
    override val expectedResultP2: Any
        get() = 0

    private lateinit var rules: MutableList<Rule>
    private lateinit var flatRanges: MutableList<IntRange>
    private lateinit var myTicket: List<Int>
    private lateinit var nearTicket: MutableList<List<Int>>

    override fun part1(reader: BufferedReader): Any {
        readData(reader)
        var sum = 0
        nearTicket.forEach {
            it.forEach { value ->
                if (flatRanges.any { range -> value in range }.not())
                    sum += value
            }
        }
        return sum
    }

    /*
    class: 0-1 or 4-19
    row: 0-5 or 8-19
    seat: 0-13 or 16-19

    your ticket:
    11,12,13

    nearby tickets:
    3,9,18
    15,1,5
    5,14,9
     */
    override fun part2(reader: BufferedReader): Any {
        rules.forEach {
            it.candidates = rules.indices.toMutableList()
        }

        nearTicket.removeAll{
            it.any { value -> flatRanges.any { range -> value in range }.not() }
        }

        nearTicket.forEach { ticket ->
            rules.forEach { rule ->
                rule.candidates.removeAll { candidate ->
                    rule.ranges.any { range -> ticket[candidate] in range }.not()
                }
            }
        }
        val treated: MutableList<String> = mutableListOf()
        //now we simply check our candidates, if any rule has only one candidate we remove it from all other
        while (treated.size != rules.size) {
            rules.forEach { rule ->
                if (!treated.contains(rule.name) && rule.candidates.size == 1) {
                    rules.forEach { rule2 ->
                        if (rule2 != rule)
                            rule2.candidates.remove(rule.candidates[0])
                    }
                    treated.add(rule.name)
                }
            }
        }
        var result = 1L
        if(rules.size > 5){ //not running this part on our sample
            rules.forEach { rule ->
                if(rule.name.startsWith("departure")){
                    result *= myTicket[rule.candidates[0]]
                }
            }
        }
        return result
    }

    private fun readData(reader: BufferedReader) {
        var state = 0
        rules = mutableListOf()
        flatRanges = mutableListOf()
        nearTicket = mutableListOf()
        reader.useLines { sequence ->
            sequence.forEach {
                if (it.isBlank())
                    state++
                else
                    when (state) {
                        0 -> readRule(it)
                        1 -> readMyTicket(it)
                        2 -> readNearTicket(it)
                    }
            }
        }
    }

    /*
    class: 1-3 or 5-7
    row: 6-11 or 33-44
    seat: 13-40 or 45-50

    your ticket:
    7,1,14

    nearby tickets:
    7,3,47
    40,4,50
    55,2,20
    38,6,12
    */

    private fun readRule(rule: String) {
        val splits = rule.split(": ", " or ")
        val ranges = mutableListOf<IntRange>()
        var range = splits[1].split('-')
        ranges.add(range[0].toInt()..range[1].toInt())
        range = splits[2].split('-')
        ranges.add(range[0].toInt()..range[1].toInt())
        rules.add(Rule(splits[0], ranges, mutableListOf()))
        flatRanges.addAll(ranges)
    }

    private fun readMyTicket(ticket: String) {
        if (ticket.startsWith('y'))
            return
        myTicket = ticket.split(',').map { it.toInt() }
    }

    private fun readNearTicket(ticket: String) {
        if (ticket.startsWith('n'))
            return
        nearTicket.add(ticket.split(',').map { it.toInt() })
    }

    private data class Rule(
        val name: String,
        val ranges: List<IntRange>,
        var candidates: MutableList<Int>
    )
}
