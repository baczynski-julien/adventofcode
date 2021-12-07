package com.jbi.adventofcode.launcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jbi.adventofcode.advent2021.day6.Day6

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Day6.run(this)
		finish()
	}
}