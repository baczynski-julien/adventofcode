package com.jbi.adventofcode.launcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jbi.adventofcode.advent2021.day6.Day6
import com.jbi.adventofcode.advent2021.day7.Day7
import com.jbi.adventofcode.advent2021.day8.Day8

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Day8.run(this)
		finish()
	}
}