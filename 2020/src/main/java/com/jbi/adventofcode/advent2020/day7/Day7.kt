package com.jbi.adventofcode.advent2020.day7

import android.util.Pair
import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/6/20.
 */
object Day7 : DailySolution2020() {

    private lateinit var memCount: MutableMap<String, Int>
    private lateinit var memContains: MutableMap<String, Boolean>
    private lateinit var dataMap: MutableMap<String, List<Pair<String, Int>>>
    override val expectedResultP1: Any
        get() = 4
    override val expectedResultP2: Any
        get() = 32

    override fun prerunInput(reader: BufferedReader) {
        dataMap = buildData(reader)
        memContains = mutableMapOf()
        memCount = mutableMapOf()
    }

    override fun prerunSample(reader: BufferedReader) {
        dataMap = buildData(reader)
        memContains = mutableMapOf()
        memCount = mutableMapOf()
    }

    override fun part1(reader: BufferedReader): Any = dataMap.count { canHoldBag(dataMap, "shiny gold", it.value) }

    override fun part2(reader: BufferedReader): Any = countBagsInside(dataMap, "shiny gold") - 1

    private fun countBagsInside(
        dataMap: MutableMap<String, List<Pair<String, Int>>>,
        s: String
    ): Int {
        return dataMap[s]!!.fold(1) { acc, pair ->
            if(!memCount.containsKey(pair.first)){
                memCount[pair.first] = countBagsInside(dataMap, pair.first)
            }
            acc + pair.second * memCount[pair.first]!!
        }
    }

    private fun canHoldBag(
        rulesOfHolding: MutableMap<String, List<Pair<String, Int>>>,
        bagToSearch: String,
        bagContent: List<Pair<String, Int>>
    ): Boolean {
        return bagContent.any {
            if (it.first == bagToSearch)
                true
            else {
                if(!memContains.containsKey(it.first)){
                    memContains[it.first] = canHoldBag(rulesOfHolding, bagToSearch, rulesOfHolding[it.first]!!)
                }
                memContains[it.first]!!
            }
        }
    }

    private fun buildData(reader: BufferedReader): MutableMap<String, List<Pair<String, Int>>> {
        val map = mutableMapOf<String, List<Pair<String, Int>>>()
        reader.useLines { sequence ->
            sequence.forEach { line ->
                readLine()
                val splits = line.split(" bags contain ")
                val content = splits[1].split(", ")
                val list = mutableListOf<Pair<String, Int>>()
                try {
                    for (split in content) {
                        list.add(
                            Pair(
                                split.split(" bag")[0].substring(2),
                                Integer.parseInt(split[0].toString())
                            )
                        )
                    }
                } catch (e: Exception) {

                }
                map[splits[0]] = list
            }
        }
        return map
    }
}
