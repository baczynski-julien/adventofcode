package com.jbi.adventofcode.advent2020.day22

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader
import java.util.*

/**
 * @author Julien BACZYNSKI on 12/22/20.
 */
object Day22 : DailySolution2020() {

    override val expectedResultP1: Any
        get() = 306
    override val expectedResultP2: Any
        get() = 291

    private lateinit var p1Deck: LinkedList<Int>
    private lateinit var p2Deck: LinkedList<Int>

    override fun part1(reader: BufferedReader): Any =
        score(playCombat(copyDeck(p1Deck), copyDeck(p2Deck)))

    override fun part2(reader: BufferedReader): Any =
        score(playRecursiveCombat(p1Deck, p2Deck))

    private fun score(deck: Queue<Int>) = deck.run {
        withIndex().sumOf { (i, card) -> (this.size - i) * card }
    }

    private fun playCombat(deck1: LinkedList<Int>, deck2: LinkedList<Int>): Queue<Int> {
        while (deck1.isNotEmpty() && deck2.isNotEmpty())
            if (deck1.peek() > deck2.peek()) {
                deck1.add(deck1.remove())
                deck1.add(deck2.remove())
            } else {
                deck2.add(deck2.remove())
                deck2.add(deck1.remove())
            }
        return deck1.takeIf { deck1.isNotEmpty() } ?: deck2
    }

    private fun playRecursiveCombat(deck1: LinkedList<Int>, deck2: LinkedList<Int>): Queue<Int> {
        val memory = mutableListOf<Pair<LinkedList<Int>, LinkedList<Int>>>()
        var card1: Int
        var card2: Int
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {//normal winning condition
            if (checkMemory(memory, deck1, deck2)) //memory winning condition before any card is drawn
                return deck1
            memory.add(Pair(copyDeck(deck1), copyDeck(deck2))) // add the current state to the memory so we don't run it again
            card1 = deck1.remove() //player 1 draw his card
            card2 = deck2.remove() //player 2 draw his card
            if (deck1.size >= card1 && deck2.size >= card2) { // both have enough cards, launch another recursive game
                val newDeck1 = copyDeck(deck1, card1) //copy of p1's deck
                val newDeck2 = copyDeck(deck2, card2) //copy of p2's deck
                if (playRecursiveCombat(newDeck1, newDeck2) === newDeck1) {//p1 wins sub game
                    deck1.add(card1)
                    deck1.add(card2)
                } else { // p2 wins sub game
                    deck2.add(card2)
                    deck2.add(card1)
                }
            } else { // normal round
                if (card1 > card2) { //p1 wins round
                    deck1.add(card1)
                    deck1.add(card2)
                } else { // p2 wins round
                    deck2.add(card2)
                    deck2.add(card1)
                }
            }
        }
        return deck1.takeIf { it.isNotEmpty() } ?: deck2 // winner is the not empty deck
    }

    private fun copyDeck(deck: LinkedList<Int>, size: Int): LinkedList<Int> =
        LinkedList<Int>().apply {
            for (i in 0 until size)
                add(deck[i])
        }

    private fun copyDeck(deck: LinkedList<Int>): LinkedList<Int> =
        LinkedList<Int>().apply { addAll(deck) }

    private fun checkMemory(
        memory: MutableList<Pair<LinkedList<Int>, LinkedList<Int>>>,
        deck1: Queue<Int>,
        deck2: Queue<Int>
    ): Boolean = memory.any { it.first == deck1 && it.second == deck2 }


    private fun readData(reader: BufferedReader) {
        p1Deck = LinkedList()
        p2Deck = LinkedList()
        var currentDeck = p1Deck
        reader.useLines { sequence ->
            sequence.forEach { line ->
                if (line.isNotEmpty()) {
                    if (line.startsWith("Player")) {
                        if (line.startsWith("Player 2"))
                            currentDeck = p2Deck
                    } else {
                        currentDeck.add(line.toInt())
                    }
                }
            }
        }
    }

    override fun prerunInput(reader: BufferedReader) {
        readData(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        readData(reader)
    }
}
