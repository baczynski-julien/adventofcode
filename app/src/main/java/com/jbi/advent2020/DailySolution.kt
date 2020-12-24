package com.jbi.advent2020

import android.content.Context
import android.util.Log
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/3/20.
 */
abstract class DailySolution {
    protected open val runWithInput: Boolean = true
    private val sampleFile: String
        get() = "2020/${this::class.java.simpleName.toLowerCase()}_sample.txt"
    private val inputFile: String
        get() = "2020/${this::class.java.simpleName.toLowerCase()}_input.txt"

    protected abstract val expectedResultP1: Any
    protected abstract val expectedResultP2: Any

    private val TAG: String
        get() = this::class.java.simpleName.toUpperCase()

    fun run(context: Context) {
        prerunSample(context.resources.assets.open(sampleFile).bufferedReader())
        val part1Test = part1(context.resources.assets.open(sampleFile).bufferedReader())
        if (part1Test == expectedResultP1)
            log("Test part1 over sample data : $part1Test == $expectedResultP1 TEST OK")
        else
            log("Test part1 over sample data : $part1Test != $expectedResultP1 TEST KO")
        val part2Test = part2(context.resources.assets.open(sampleFile).bufferedReader())
        if (part2Test == expectedResultP2)
            log("Test part2 over sample data : $part2Test == $expectedResultP2 TEST OK")
        else
            log("Test part2 over sample data : $part2Test != $expectedResultP2 TEST KO")

        if(runWithInput) {
            prerunInput(context.resources.assets.open(inputFile).bufferedReader())
            var ms = System.nanoTime()
            var result = part1(context.resources.assets.open(inputFile).bufferedReader())
            log("part1: $result")
            log("took: ${ (System.nanoTime() - ms) /1000000f } ms")

            ms = System.nanoTime()
            result = part2(context.resources.assets.open(inputFile).bufferedReader())
            log("part2: $result")
            log("took: ${ (System.nanoTime() - ms) /1000000f } ms")
        }
    }
    protected open fun tests() {
    }
    protected open fun prerunSample(reader: BufferedReader) {
    }
    protected open fun prerunInput(reader: BufferedReader) {
    }

    protected abstract fun part1(reader: BufferedReader): Any
    protected abstract fun part2(reader: BufferedReader): Any


    fun log(text: String) {
        Log.d(TAG, text)
    }
}