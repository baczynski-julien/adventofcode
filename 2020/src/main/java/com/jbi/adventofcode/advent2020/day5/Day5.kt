package com.jbi.adventofcode.advent2020.day5

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader
import kotlin.math.max

/**
 * @author Julien BACZYNSKI on 12/4/20.
 */
object Day5 : DailySolution2020() {

    override val expectedResultP1: Any
        get() = 820
    override val expectedResultP2: Any
        get() = 0

    override fun part1(reader: BufferedReader): Any {
        return reader.useLines { sequence: Sequence<String> ->
            sequence.fold(0) { a, b -> max(getSeatId(b), a) }
        }
    }

    private fun getSeatId(data: String): Int {
        /*var row = 0
        var seat = 0

        for (i in 0..6) {
            if (data[i] == 'B')
                row = row or (1 shl 6 - i)
        }
        for (j in 7..9) {
            if (data[j] == 'R')
                seat = seat or (1 shl (9 - j))
        }*/
        var row = Integer.parseInt(data.substring(0..6).replace('F', '0').replace('B', '1'), 2)
        var seat = Integer.parseInt(data.substring(7..9).replace('L', '0').replace('R', '1'), 2)
        //log("$data -> row : $row || seat : $seat || id : ${row * 8 + seat}")
        return row * 8 + seat
    }

    override fun part2(reader: BufferedReader): Any {
        var array = reader.useLines { sequence: Sequence<String> ->
            sequence.fold(Array<Int>(1024) { 0 }) { a, b ->
                a[getSeatId(b)] = 1
                a
            }
        }
        array.forEachIndexed () { index, b ->
            if(b == 0){
                log("empty seat : $index")
            }
        }
        return 0
    }
}