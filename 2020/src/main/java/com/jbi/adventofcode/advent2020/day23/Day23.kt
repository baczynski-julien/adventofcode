package com.jbi.adventofcode.advent2020.day23

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/23/20.
 */
object Day23 : DailySolution2020() {

    override val expectedResultP1: Any
        get() = "67384529"
    override val expectedResultP2: Any
        get() = 149245887792L
    private lateinit var cups: List<Int>

    override fun prerunInput(reader: BufferedReader) {
        cups = listOf(7, 3, 9, 8, 6, 2, 5, 4, 1)
    }

    override fun prerunSample(reader: BufferedReader) {
        cups = listOf(3, 8, 9, 1, 2, 5, 4, 6, 7)
    }

    override fun part1(reader: BufferedReader): Any {
        var list = Node(cups[0])
        val first = list
        val max = 10
        val map = Array<Node?>(max){null}
        map[list.value]= list
        for (i in 1 until cups.size) {
            list.next = Node(cups[i])
            list.next.prev = list
            list = list.next
            map[list.value]= list
        }


        list.next = first
        first.prev = list
        runGame(first, map, max, 100)

        var current = map[1]!!.next
        var build = StringBuilder()
        while (current.value != 1) {
            build.append(current.value)
            current = current.next
        }
        return build.toString()
    }

    override fun part2(reader: BufferedReader): Any {
        var list = Node(cups[0])
        val first = list
        val max = 1000001
        val map = Array<Node?>(max){null}
        map[list.value]= list
        for (i in 1 until cups.size) {
            list.next = Node(cups[i])
            list.next.prev = list
            list = list.next
            map[list.value]= list
        }
        for (i in 10..1000000) {
            list.next = Node(i)
            list.next.prev = list
            list = list.next
            map[list.value]= list
        }

        val turns = 10000000
        list.next = first
        first.prev = list
        runGame(first, map, max, turns)

        return map[1]!!.next.value.toLong() * map[1]!!.next.next.value.toLong()
    }

    private fun runGame(first: Node, map: Array<Node?>, max: Int, turns: Int) {
        var current = first
        var cut: Node
        var destinationValue: Int
        var destination: Node
        for (i in 1..turns) {
            //remove 3 after current
            cut = current.next
            current.next = current.next.next.next.next
            current.next.prev = current
            destinationValue = current.value
            do {
                destinationValue = (max + destinationValue - 1) % max
            } while (destinationValue == 0 || destinationValue == current.value || destinationValue == cut.value || destinationValue == cut.next.value || destinationValue == cut.next.next.value)
            //select next cup going -- on current
            destination = map[destinationValue]!!
            //put back 3 removed after selected cup
            cut.next.next.next = destination.next
            destination.next.prev = cut.next.next
            destination.next = cut
            cut.prev = destination

            //select next
            current = current.next
        }
    }

    private class Node(val value: Int) {
        var prev: Node = this
        var next: Node = this
    }
}
