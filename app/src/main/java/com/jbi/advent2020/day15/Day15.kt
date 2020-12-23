package com.jbi.advent2020.day15

import com.jbi.advent2020.DailySolution
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 12/15/20.
 */
object Day15 : DailySolution() {

    private lateinit var data: Array<Int>
    override val expectedResultP1: Any
        get() = 436
    override val expectedResultP2: Any
        get() = 0

    override fun prerunSample(reader: BufferedReader) {
        var numbers = listOf(1, 3, 2).toTypedArray()
        var res = runGame(numbers, 2020)
        log("Test 1 : $res // 1")

        numbers = listOf(2, 1, 3).toTypedArray()
        res = runGame(numbers, 2020)
        log("Test 2 : $res // 10")

        numbers = listOf(1, 2, 3).toTypedArray()
        res = runGame(numbers, 2020)
        log("Test 3 : $res // 27")

        numbers = listOf(2, 3, 1).toTypedArray()
        res = runGame(numbers, 2020)
        log("Test 4 : $res // 78")

        numbers = listOf(3, 2, 1).toTypedArray()
        res = runGame(numbers, 2020)
        log("Test 5 : $res // 438")

        numbers = listOf(3, 1, 2).toTypedArray()
        res = runGame(numbers, 2020)
        log("Test 6 : $res // 1836")

        data = readData(reader)
    }

    override fun prerunInput(reader: BufferedReader) {
        data = readData(reader)
    }

    override fun part1(reader: BufferedReader): Any = runGame(data, 2020)


    override fun part2(reader: BufferedReader): Any =runGame(data, 30000000)


    /*
    Turn 1: The 1st number spoken is a starting number, 0.
    Turn 2: The 2nd number spoken is a starting number, 3.
    Turn 3: The 3rd number spoken is a starting number, 6.
    Turn 4: Now, consider the last number spoken, 6. Since that was the first time the number had been spoken, the 4th number spoken is 0.
    Turn 5: Next, again consider the last number spoken, 0. Since it had been spoken before, the next number to speak is the difference between the turn number when it was last spoken (the previous turn, 4) and the turn number of the time it was most recently spoken before then (turn 1). Thus, the 5th number spoken is 4 - 1, 3.
    Turn 6: The last number spoken, 3 had also been spoken before, most recently on turns 5 and 2. So, the 6th number spoken is 5 - 2, 3.
    Turn 7: Since 3 was just spoken twice in a row, and the last two turns are 1 turn apart, the 7th number spoken is 1.
    Turn 8: Since 1 is new, the 8th number spoken is 0.
    Turn 9: 0 was last spoken on turns 8 and 4, so the 9th number spoken is the difference between them, 4.
    Turn 10: 4 is new, so the 10th number spoken is 0.
     */
    private fun runGame(numbers: Array<Int>, turns : Int): Int {
        val map = IntArray(turns){-1}
        var number = -1
        var oldIndex =-1
        for (i in 1 .. turns) {
            if(i <= numbers.size){
                number = numbers[i-1]
                map[number] = i
            }else{
                number = if(oldIndex == -1){
                    0
                }else{
                    i -1 - oldIndex
                }
                oldIndex = map[number]
                map[number] = i
            }
        }
        return number
    }

    private fun readData(reader: BufferedReader): Array<Int> {
        return reader.useLines { seq ->
            seq.elementAt(0).split(',').map { number -> number.toInt() }.toTypedArray()
        }
    }
}
