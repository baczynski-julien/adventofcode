package com.jbi.advent2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jbi.advent2020.day10.Day10
import com.jbi.advent2020.day11.Day11
import com.jbi.advent2020.day12.Day12
import com.jbi.advent2020.day13.Day13
import com.jbi.advent2020.day14.Day14
import com.jbi.advent2020.day15.Day15
import com.jbi.advent2020.day16.Day16
import com.jbi.advent2020.day17.Day17
import com.jbi.advent2020.day18.Day18
import com.jbi.advent2020.day19.Day19
import com.jbi.advent2020.day20.Day20
import com.jbi.advent2020.day21.Day21
import com.jbi.advent2020.day22.Day22
import com.jbi.advent2020.day23.Day23
import com.jbi.advent2020.day24.Day24
import com.jbi.advent2020.day3.Day3
import com.jbi.advent2020.day4.Day4
import com.jbi.advent2020.day5.Day5
import com.jbi.advent2020.day6.Day6
import com.jbi.advent2020.day7.Day7
import com.jbi.advent2020.day8.Day8
import com.jbi.advent2020.day9.Day9

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Day9.run(this)
		finish()
	}
}