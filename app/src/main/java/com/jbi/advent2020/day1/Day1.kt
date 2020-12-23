package com.jbi.advent2020.day1

/**
 * @author Julien BACZYNSKI on 12/1/20.
 */
object Day1 {

    fun part1(data: List<Int>){
        for (i in data.indices) {
            for (j in i + 1 until data.size) {
                for (k in j + 1 until data.size)
                    if (data[i] + data[j] == 2020) {
                        System.out.println("${data[i]} + ${data[j]} == 2020 -> ${data[i] * data[j]}")
                        return
                    }
            }
        }
    }

    fun part2(data: List<Int>){
        for (i in data.indices) {
            for (j in i + 1 until data.size) {
                for (k in j + 1 until data.size)
                    if (data[i] + data[j] + data[k] == 2020) {
                        System.out.println("${data[i]} + ${data[j]} + ${data[k]} == 2020 -> ${data[i] * data[j] * data[k]}")
                        return
                    }
            }
        }
    }
}