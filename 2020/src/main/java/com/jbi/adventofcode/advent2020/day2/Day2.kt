package com.jbi.adventofcode.advent2020.day2

import android.content.Context
import android.util.Log

private const val TAG = "DAY2"

/**
 * @author Julien BACZYNSKI on 12/2/20.
 */

private const val FILENAME = "2020/day2_input.txt"

object Day2 {

    fun part1(context: Context) {
        val validCount = context.resources.assets.open(FILENAME).bufferedReader()
            .useLines { sequence: Sequence<String> ->
                sequence.fold(0) { a, b -> if (isValidPart1(b)) a + 1 else a }
            }
        Log.d(TAG, "valid count : $validCount")
    }

    fun part2(context: Context) {
        val validCount = context.resources.assets.open(FILENAME).bufferedReader()
            .useLines { sequence: Sequence<String> ->
                sequence.fold(0) { a, b -> if (isValidPart2(b)) a + 1 else a }
            }
        Log.d(TAG, "valid count : $validCount")
    }

    private fun isValidPart1(line: String): Boolean {
        val splits = line.split('-', ' ', ':')
        val min = Integer.parseInt(splits[0])
        val max = Integer.parseInt(splits[1])
        val char = splits[2].toCharArray()[0]
        val pwd = splits[4]
        var countChar = 0
        for (c in pwd) {
            if (c == char)
                countChar++
            if (countChar > max)
                return false
        }
        return countChar >= min
    }

    private fun isValidPart2(line: String): Boolean {
        val splits = line.split('-', ' ', ':')
        val first = Integer.parseInt(splits[0])
        val last = Integer.parseInt(splits[1])
        val char = splits[2].toCharArray()[0]
        val pwd = splits[4]
        var valid1 = false
        var valid2 = false
        try {
            valid1 = pwd[first - 1] == char
            valid2 = pwd[last - 1] == char
        } catch (e: Exception) {
        }
        return valid1.xor(valid2)
    }
}