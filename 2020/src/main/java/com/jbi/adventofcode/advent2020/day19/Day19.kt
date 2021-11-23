package com.jbi.adventofcode.advent2020.day19

import android.util.SparseArray
import androidx.core.util.set
import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/19/20.
 */
object Day19 : DailySolution2020() {

    private lateinit var memory: SparseArray<List<String>>
    override val runWithInput: Boolean
        get() = true
    private lateinit var rules: SparseArray<Rule>
    private var maxRecursion: SparseArray<Int> = SparseArray()
    private lateinit var messages: MutableList<String>
    override val expectedResultP1: Any
        get() = 3
    override val expectedResultP2: Any
        get() = 12


    override fun part1(reader: BufferedReader): Any {
        return buildPermutations("", rules.get(0)).intersect(messages).size
    }

    override fun part2(reader: BufferedReader): Any {
        var sb: String
        return messages.filter { message ->
            sb = message
            var count42 = 0
            var count31 = 0
            var removed: Boolean
            do {
                removed = false
                memory[42].forEach {
                    if (sb.startsWith(it)) {
                        sb = sb.removePrefix(it)
                        count42++
                        removed = true
                    }
                }
            } while (removed)
            do {
                removed = false
                memory[31].forEach {
                    if (sb.startsWith(it)) {
                        sb = sb.removePrefix(it)
                        count31++
                        removed = true
                    }
                }
            } while (removed)
            sb.isEmpty() && count42 >= 2 && count31 >= 1 && count31 < count42
        }.size
    }

    private fun buildPermutations(message: String, rule: Rule): List<String> {
        var list = mutableListOf<String>()
        if (rule.string == null) {
            rule.subRules.forEach { subsetOfRules ->
                //do not apply subset of rules if rule contains itself and maxrecursion is reached
                var shouldRun = true
                if (subsetOfRules.contains(rule.index)) {
                    maxRecursion[rule.index] = maxRecursion[rule.index] + 1
                    if (maxRecursion[rule.index] > 1) {
                        shouldRun = false
                        maxRecursion[rule.index] = 0
                    }
                }
                if (shouldRun) {
                    var buildingList = mutableListOf<String>()
                    subsetOfRules.forEach { subRule ->
                        val newList = mutableListOf<String>()
                        if (memory[subRule] == null)
                            memory[subRule] = buildPermutations("", rules[subRule])
                        if (buildingList.isEmpty()) {
                            newList.addAll(memory[subRule].map { mem ->
                                message.plus(mem)
                            })
                        } else {
                            buildingList.forEach {
                                newList.addAll(memory[subRule].map { mem ->
                                    it.plus(mem)
                                })
                            }
                        }
                        buildingList = newList
                    }
                    list.addAll(buildingList)
                }
            }
        } else {
            list.add(message.plus(rule.string))
        }
        return list
    }

    /**
    0: 4 1 5
    1: 2 3 | 3 2
    2: 4 4 | 5 5
    3: 4 5 | 5 4
    4: "a"
    5: "b"

    ababbb
    bababa
    abbbab
    aaabbb
    aaaabbb
     */
    private fun readData(reader: BufferedReader) {
        rules = SparseArray()
        messages = mutableListOf()
        reader.useLines { sequence ->
            sequence.forEach { line ->
                if (line.isNotBlank()) {
                    if (line[0] == 'a' || line[0] == 'b') {
                        messages.add(line)
                    } else {
                        readRule(line)
                    }
                }
            }
        }
    }

    private fun readRule(line: String) {
        val splitIndex = line.split(':')
        val index = splitIndex[0].toInt()
        val ruleString = splitIndex[1].trim()
        rules.put(index,
            if (ruleString[0] == '"') {
                Rule(index, ruleString[1].toString())
            } else {
                val rule = Rule(index)
                ruleString.split('|').forEach {
                    rule.subRules.add(it.trim().split(' ').map { index -> index.toInt() })
                }
                rule
            }
        )
    }

    private data class Rule(val index: Int, var string: String? = null) {
        val subRules: MutableList<List<Int>> = mutableListOf()
    }

    override fun prerunSample(reader: BufferedReader) {
        readData(reader)
        memory = SparseArray()
    }

    override fun prerunInput(reader: BufferedReader) {
        readData(reader)
        memory = SparseArray()
    }
}
