package com.jbi.adventofcode.advent2020.day13

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/13/20.
 */
object Day13 : DailySolution2020() {

    private lateinit var buses: List<Long>
    private var timestamp = 0L

    override val expectedResultP1: Any
        get() = 295
    override val expectedResultP2: Any
        get() = 1068781L

    override fun prerunInput(reader: BufferedReader) {
        readFile(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        readFile(reader)
    }

    override fun part1(reader: BufferedReader): Any {
        return buses.map { bus ->
            bus - timestamp % bus
        }.withIndex().minByOrNull { (_, diff) -> diff }!!.let {
            it.value * buses[it.index]
        }
    }

    private fun readFile(reader: BufferedReader) {
        reader.useLines { sequence ->
            sequence.forEachIndexed { i, line ->
                if (i == 0) {
                    timestamp = line.toLong()
                } else {
                    buses = line.split(',').filter { it[0] != 'x' }.map { it.toLong() }
                }
            }
        }
    }

    private fun readFile2(reader: BufferedReader) {
        reader.useLines { sequence ->
            sequence.forEachIndexed { i, line ->
                if (i == 0) {
                    timestamp = line.toLong()
                } else {
                    buses = line.split(',').map { if (it[0] == 'x') -1L else it.toLong() }
                }
            }
        }
    }

    override fun part2(reader: BufferedReader): Any {
        /**
         * By looking more closely at the data and the sample, it might occur to you that the topology around the solution is quite specific.
         * Indeed after the first bus complete his tour, you can notice that some other buses will start at the same time, 4 other buses can depart at this time (depend on you input)
         * So it mean that this round is necessarily a multiple of all the bus ids, allowing us to have a very large increment for our "brute force loop"
         * the we simple test every value with that increment, and the first one to validate the rule : (timestamp + index of bus in the list) mod bus id == 0 for every bus is the one
         * we could only test the buses that did not serve in our increment since the rule is always true for them
         */
        val indexedBuses = buses.withIndex()
        //taking each bus that is at the index matching first bus, or an index minus its own value equals bus[0], meaning each bus that will depart at the same time ou first bus will start his second round
        var increments = indexedBuses.filter { (i, value) -> value != -1L && (i.toLong() == buses[0] || i - value == buses[0]) }
        //multiplying all the chosen buses ids with the first one gives us an increment large enough so that our loop will not last a thousand year
        var increment = increments.fold(1L){ acc, item -> acc*item.value} * buses[0]
        log("increment $increment")
        timestamp = increment - buses[0]
        while (true) {
            if (indexedBuses.all {
                        (i, value) ->
                    value == -1L || (timestamp + i)  % value == 0L
            }) {
                log("iterations = ${timestamp / increment}")
                return timestamp
            } else
                timestamp += increment
        }
    }
}
