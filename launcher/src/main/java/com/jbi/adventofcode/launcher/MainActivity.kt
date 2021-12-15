package com.jbi.adventofcode.launcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jbi.adventofcode.advent2021.day10.Day10
import com.jbi.adventofcode.advent2021.day11.Day11
import com.jbi.adventofcode.advent2021.day12.Day12
import com.jbi.adventofcode.advent2021.day13.Day13

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Day13.run(this)
		finish()
	}
}