package com.example.wifiqrcodesgenerator.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.wifiqrcodesgenerator.ui.theme.Orange
import com.example.wifiqrcodesgenerator.ui.theme.Red
import kotlin.math.*

fun Modifier.gradientBackground(
	colors: List<Color> = listOf(Red, Orange),
	angle: Float = 45f
) = this.drawBehind {
	val angleRad = angle / 180f * PI
	val x = cos(angleRad).toFloat()
	val y = sin(angleRad).toFloat()

	val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
	val offset = center + Offset(x * radius, y * radius)

	val exactOffset = Offset(
		x = offset.x.coerceAtLeast(0f).coerceAtMost(size.width),
		y = size.height - offset.y.coerceAtLeast(0f).coerceAtMost(size.height)
	)

	drawRect(
		brush = Brush.linearGradient(
			colors = colors,
			start = Offset(size.width, size.height) - exactOffset,
			end = exactOffset
		),
		size = size
	)
}