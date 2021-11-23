package com.jbi.adventofcode.launcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jbi.adventofcode.advent2021.day1.Day1

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Day1.run(this)
		finish()
	}
}