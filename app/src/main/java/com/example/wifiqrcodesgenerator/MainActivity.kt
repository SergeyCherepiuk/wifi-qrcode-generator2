package com.example.wifiqrcodesgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.wifiqrcodesgenerator.navigation.NavGraph

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val navController = rememberNavController()
			NavGraph(navController)
		}
	}
}

// TODO: Share the image of the QR code using implicit intent
// TODO: Improve dots indicator for pager
// TODO: Create "no items" screen
// TODO: Change implementation of navigation using Compose Navigation