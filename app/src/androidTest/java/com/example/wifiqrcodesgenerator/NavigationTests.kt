package com.example.wifiqrcodesgenerator

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.wifiqrcodesgenerator.navigation.Destinations
import com.example.wifiqrcodesgenerator.navigation.NavGraph
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTests {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupNavGraph() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavGraph(navController = navController)
        }
    }

    @Test
    fun startDestinationTest() {
        composeTestRule
            .onNodeWithTag(testTag = "Items list")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToAddItemScreenTest() {
        composeTestRule
            .onNodeWithTag(testTag = "Navigate to \"add item\" screen")
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, Destinations.ADD_ITEM_ROUTE)
    }
}