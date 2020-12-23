package com.jbi.advent2020.day4

import com.jbi.advent2020.DailySolution
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/3/20.
 */
object Day4 : DailySolution() {
    private lateinit var data: List<Map<String, String>>
    override val expectedResultP1: Any
        get() = 2
    override val expectedResultP2: Any
        get() = 2

    private val validationMap = mutableMapOf<String, Pair<Int,(String) -> Boolean>>().apply {
        put("byr", Pair(0x00000001, ::validateBYR))
        put("iyr", Pair(0x00000010, ::validateIYR))
        put("eyr", Pair(0x00000100, ::validateEYR))
        put("hgt", Pair(0x00001000, ::validateHGT))
        put("hcl", Pair(0x00010000, ::validateHCL))
        put("ecl", Pair(0x00100000, ::validateECL))
        put("pid", Pair(0x01000000, ::validatePID))
        put("cid", Pair(0x10000000, ::validateCID))
    }.toMap()

    override fun tests() {
        log("pid test : ${PIDRegex.matches("123456789")}")
        log("ecl test : ${ECLRegex.matches("amb")}")
        log("ecl test2 : ${ECLRegex.matches("blu")}")
        log("ecl test3 : ${!ECLRegex.matches("caca")}")
        log("hcl test1 : ${HCLRegex.matches("#123456")}")
        log("hcl test2 : ${!HCLRegex.matches("#12547P")}")
    }

    override fun prerunSample(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun prerunInput(reader: BufferedReader) {
        data = buildData(reader)
    }

    override fun part1(reader: BufferedReader): Any {

        return data.fold(0) { a, b ->
            if (isValidPart1(b)) a + 1 else a
        }
    }

    private fun isValidPart1(passData: Map<String, String>): Boolean {
        if (passData.keys.size < 7) {
            return false
        }

        val validationMask = 0x11111111 xor validationMap["cid"]!!.first
        var validator = 0x00000000
        for (key in passData.keys) {
            if (validationMap.containsKey(key))
                validator = validator or validationMap[key]!!.first
            else
                log("unknown  key : $key")
        }
        return (validator and validationMask) == validationMask
    }

    override fun part2(reader: BufferedReader): Any {
        return data.fold(0) { a, b ->
            if (isValidPart2(b)) a + 1 else a
        }
    }

    private fun isValidPart2(passData: Map<String, String>): Boolean {
        if (passData.keys.size < 7) {
            return false
        }
        val validationMask = 0x11111111 xor validationMap["cid"]!!.first
        var validator = 0x00000000
        for (key in passData.keys) {
            if (validationMap.containsKey(key) && validationMap[key]!!.second(passData[key]!!))
                validator = validator or validationMap[key]!!.first
        }
        return (validator and validationMask) == validationMask
    }

    private fun buildData(reader: BufferedReader): List<Map<String, String>> {
        val list = mutableListOf<MutableMap<String, String>>()
        list.add(mutableMapOf())
        reader.useLines { sequence: Sequence<String> ->
            sequence.forEach {
                if (it.isBlank()) {
                    list.add(mutableMapOf())
                } else {
                    val splits = it.split(' ', ':')
                    for (i in splits.indices step 2)
                        list.last().put(splits[i], splits[i + 1])
                }
            }
        }
        return list
    }

    private fun validateBYR(value: String): Boolean {
        return validateYear(value, 1920, 2002)
    }

    private fun validateIYR(value: String): Boolean {
        return validateYear(value, 2010, 2020)
    }

    private fun validateEYR(value: String): Boolean {
        return validateYear(value, 2020, 2030)
    }

    /**
     * (Height) - a number followed by either cm or in:
    If cm, the number must be at least 150 and at most 193.
    If in, the number must be at least 59 and at most 76.
     */
    private fun validateHGT(value: String): Boolean {

        if (value.endsWith("cm")) {
            try {
                val intValue = Integer.parseInt(value.removeSuffix("cm"))
                return intValue in 150..193
            }catch(e:Exception){

            }
            return false

        } else if (value.endsWith("in")) {
            try {
                val intValue = Integer.parseInt(value.removeSuffix("in"))
                return intValue in 59..76
            }catch(e:Exception){

            }
            return false
        }
        return false
    }

    /**
     * hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
     */
    private val HCLRegex = Regex("#[a-f0-9]{6}")
    private fun validateHCL(value: String): Boolean {
        return value.matches(HCLRegex)
    }

    /**
     * amb blu brn gry grn hzl oth
     */
    private val ECLRegex = Regex("amb|blu|brn|gry|grn|hzl|oth")
    private fun validateECL(value: String): Boolean {
        return value.matches(ECLRegex)
    }

    /**
     * a nine-digit number, including leading zeroes.
     */
    private val PIDRegex = Regex("\\d{9}")
    private fun validatePID(value: String): Boolean {
        return value.matches(PIDRegex)
    }

    private fun validateCID(value: String): Boolean {
        return true
    }

    private fun validateYear(value: String, min: Int, max: Int): Boolean {
        try {
            val intValue = Integer.parseInt(value)
            return intValue in min..max
        } catch (e: Exception) {
        }
        return false
    }
}
