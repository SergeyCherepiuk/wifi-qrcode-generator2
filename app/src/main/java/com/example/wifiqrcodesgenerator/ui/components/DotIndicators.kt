package com.example.wifiqrcodesgenerator.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifiqrcodesgenerator.ui.theme.WifiQRCodesGeneratorTheme
import com.example.wifiqrcodesgenerator.utils.getEndIndex
import com.example.wifiqrcodesgenerator.utils.getStartIndex

@Composable
fun DotIndicators(
    itemsCount: Int,
    currentPageIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        val startIndex = getStartIndex(currentPageIndex, itemsCount)
        val endIndex = getEndIndex(currentPageIndex, itemsCount)
        (startIndex until endIndex).forEach {
            val color = animateColorAsState(
                targetValue = if (it == currentPageIndex) Color.White else Color.Gray,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                ),
                label = "pagerIndicatorColor"
            )
            val width = animateDpAsState(
                targetValue = if (it == currentPageIndex) 18.dp else 6.dp,
                animationSpec = tween(
                    durationMillis = 150,
                    easing = LinearEasing
                ),
                label = "pagerIndicatorWidth"
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .size(height = 6.dp, width = width.value)
                    .clip(CircleShape)
                    .background(color.value)
            )
        }
    }
}

@Preview
@Composable
private fun DotIndicatorsPreview() {
    WifiQRCodesGeneratorTheme {
        Surface {
            DotIndicators(
                itemsCount = 5,
                currentPageIndex = 3
            )
        }
    }
}