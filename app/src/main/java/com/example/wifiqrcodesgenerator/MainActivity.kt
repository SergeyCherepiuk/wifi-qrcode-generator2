package com.example.wifiqrcodesgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.wifiqrcodesgenerator.navigation.NavGraph
import com.example.wifiqrcodesgenerator.utils.ScreenResolution

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		ScreenResolution.windowManager = windowManager
		setContent {
			val navController = rememberNavController()
			NavGraph(navController)
		}
	}
}

// TODO: Improve dots indicator for pager
// TODO: Create "no items" screen
// TODO: Change implementation of navigation using Compose Destinations
// TODO: Write UI and unit tests
// TODO: Make  UI responsive