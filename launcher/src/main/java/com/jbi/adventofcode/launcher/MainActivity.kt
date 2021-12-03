package com.jbi.adventofcode.launcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jbi.adventofcode.advent2021.day1.Day1
import com.jbi.adventofcode.advent2021.day2.Day2

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Day2.run(this)
		finish()
	}
}