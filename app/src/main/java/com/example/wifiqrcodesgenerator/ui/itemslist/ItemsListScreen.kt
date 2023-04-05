package com.example.wifiqrcodesgenerator.ui.itemslist

import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifiqrcodesgenerator.modifiers.advancedShadow
import com.example.wifiqrcodesgenerator.modifiers.gradientBackground
import com.example.wifiqrcodesgenerator.ui.theme.Orange
import com.example.wifiqrcodesgenerator.ui.theme.Red
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import java.lang.Integer.min
import java.lang.Math.max

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ItemsListScreen(
	uiState: ItemsListUiState,
	addItem: () -> Unit,
	updateItem: (ItemUiState, String, String) -> Unit,
	deleteItem: (ItemUiState) -> Unit,
	modifier: Modifier = Modifier
) {
	Box(modifier = modifier.fillMaxSize()) {
		val state by remember { mutableStateOf(PagerState()) }
		HorizontalPager(
			count = uiState.items.size,
			state = state
		) { pageIndex ->
			QRCodePage(
				item = uiState.items[pageIndex],
				updateItem = updateItem,
				deleteItem = deleteItem
			)
		}
		DotIndicators(
			itemsCount = state.pageCount,
			currentPageIndex = state.currentPage,
			modifier = Modifier.align(Alignment.BottomCenter)
		)
		FloatingActionButton(
			onClick = addItem,
			modifier = Modifier
				.align(Alignment.BottomEnd)
				.padding(24.dp)
		) {
			Icon(
				imageVector = Icons.Default.Add,
				contentDescription = null
			)
		}
	}
}

@Composable
private fun QRCodePage(
	item: ItemUiState,
	modifier: Modifier = Modifier,
	updateItem: (ItemUiState, String, String) -> Unit,
	deleteItem: (ItemUiState) -> Unit
) {
	var ssid by remember { mutableStateOf(item.ssid) }
	val updateSsid: (String) -> Unit = { value -> ssid = value }
	var password by remember { mutableStateOf(item.password) }
	val updatePassword: (String) -> Unit = { value -> password = value }
	var isInEditingMode by remember { mutableStateOf(false) }
	val toggleEditingMode = { isInEditingMode = !isInEditingMode }
	Box(modifier = modifier.fillMaxSize()) {
		TextFields(
			ssid = ssid,
			updateSsid = updateSsid,
			password = password,
			updatePassword = updatePassword,
			isInEditingMode = isInEditingMode,
			modifier = Modifier.align(Alignment.TopCenter)
		)
		QRCodePageContent(
			item = item,
			isInEditingMode = isInEditingMode,
			toggleEditingMode = toggleEditingMode,
			updateItem = updateItem,
			textFieldSsid = ssid,
			textFieldPassword = password,
			deleteItem = deleteItem,
			modifier = Modifier.align(Alignment.Center)
		)
	}
}

@Preview(
	showBackground = true,
	uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun QRCodePagePreview() {
	QRCodePage(
		item = ItemUiState(
			ssid = "asdkoda-wifi",
			password = "secret"
		),
		updateItem = { _, _, _ -> },
		deleteItem = {  }
	)
}

@Composable
fun QRCodePageContent(
	item: ItemUiState,
	isInEditingMode: Boolean,
	toggleEditingMode: () -> Unit,
	updateItem: (ItemUiState, String, String) -> Unit,
	textFieldSsid: String,
	textFieldPassword: String,
	deleteItem: (ItemUiState) -> Unit,
	modifier: Modifier = Modifier
) {
	val paddingTop = animateDpAsState(
		targetValue = if (isInEditingMode) 200.dp else 0.dp,
		animationSpec = tween(
			durationMillis = 150,
			easing = EaseOut
		)
	)
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = modifier.padding(top = paddingTop.value)
	) {
		val infiniteTransition = rememberInfiniteTransition()
		val shadowBlurRadius = infiniteTransition.animateFloat(
			initialValue = 30f,
			targetValue = 45f,
			animationSpec = infiniteRepeatable(
				animation = tween(durationMillis = 5000),
				repeatMode = RepeatMode.Reverse
			)
		)
		val cornerRadius: Dp = 75.dp
		QRCodeImage(
			image = item.toBitmap(),
			cornerRadius = cornerRadius,
			modifier = Modifier.advancedShadow(
				color = Red,
				alpha = 0.75f,
				cornersRadius = cornerRadius,
				shadowBlurRadius = shadowBlurRadius.value.dp
			)
		)
		Text(
			text = item.ssid,
			fontSize = 24.sp,
			fontWeight = FontWeight.Medium,
			modifier = Modifier.padding(28.dp)
		)
		ButtonsRow(
			item = item,
			isInEditingMode = isInEditingMode,
			toggleEditingMode = toggleEditingMode,
			updateItem = updateItem,
			textFieldSsid = textFieldSsid,
			textFieldPassword = textFieldPassword,
			deleteItem = deleteItem
		)
	}
}

@Preview
@Composable
private fun QRCodePageContentPreview() {
	QRCodePageContent(
		item = ItemUiState(
			ssid = "asdkoda-wifi",
			password = "secret"
		),
		isInEditingMode = true,
		toggleEditingMode = {  },
		updateItem = { _, _, _ -> },
		textFieldSsid = "new-asdkoda-wifi",
		textFieldPassword = "new-secret",
		deleteItem = {  }
	)
}

@Composable
private fun TextFields(
	ssid: String,
	updateSsid: (String) -> Unit,
	password: String,
	updatePassword: (String) -> Unit,
	isInEditingMode: Boolean,
	modifier: Modifier = Modifier
) {
	AnimatedVisibility(
		visible = isInEditingMode,
		enter = slideInVertically(
			initialOffsetY = { -it },
			animationSpec = tween(
				durationMillis = 150,
				easing = EaseOut
			)
		),
		exit = slideOutVertically(
			targetOffsetY = { -it },
			animationSpec = tween(
				durationMillis = 150,
				easing = EaseOut
			)
		),
		modifier = modifier
	) {
		Column {
			OutlinedTextField(
				value = ssid,
				onValueChange = updateSsid,
				label = { Text("ssid") },
				shape = RoundedCornerShape(20.dp),
				modifier = Modifier.padding(10.dp)
			)
			OutlinedTextField(
				value = password,
				onValueChange = updatePassword,
				label = { Text("password") },
				shape = RoundedCornerShape(20.dp),
				modifier = Modifier.padding(10.dp)
			)
		}
	}
}

@Preview(
	showBackground = true,
	uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun TextFieldsPreview() {
	TextFields(
		ssid = "asdkoda-wifi",
		updateSsid = {  },
		password = "secret",
		updatePassword = {  },
		isInEditingMode = true
	)
}

@Composable
private fun ButtonsRow(
	item: ItemUiState,
	modifier: Modifier = Modifier,
	isInEditingMode: Boolean = false,
	toggleEditingMode: () -> Unit,
	updateItem: (ItemUiState, String, String) -> Unit,
	textFieldSsid: String,
	textFieldPassword: String,
	deleteItem: (ItemUiState) -> Unit
) {
	Row(
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = modifier.width(250.dp)
	) {
		ElevatedButton(
			onClick = {
				val isDifferent: Boolean = item.ssid != textFieldSsid || item.password != textFieldPassword
				if (isInEditingMode && isDifferent) {
					updateItem(item, textFieldSsid, textFieldPassword)
				}
				toggleEditingMode()
			}
		) {
			Icon(
				imageVector = if (isInEditingMode) Icons.Default.Check else Icons.Default.Edit,
				contentDescription = null
			)
		}
		ElevatedButton(onClick = { deleteItem(item) }) {
			Icon(
				imageVector = Icons.Default.Delete,
				contentDescription = null
			)
		}
		ElevatedButton(onClick = { /*TODO*/ }) {
			Icon(
				imageVector = Icons.Default.Share,
				contentDescription = null
			)
		}
	}
}

@Preview(
	showBackground = true,
	uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ButtonsRowPreview() {
	ButtonsRow(
		item = ItemUiState(
			ssid = "asdkoda-wifi",
			password = "secret"
		),
		toggleEditingMode = {  },
		updateItem = { _, _, _ -> },
		textFieldSsid = "new-asdkoda-wifi",
		textFieldPassword = "new-secret",
		deleteItem = {  }
	)
}

@Composable
private fun QRCodeImage(
	image: Bitmap,
	modifier: Modifier = Modifier,
	size: Dp = 300.dp,
	cornerRadius: Dp = 70.dp,
	borderWidth: Dp = 15.dp,
	colors: List<Color> = listOf(Red, Orange)
) {
	val transition = rememberInfiniteTransition()
	val angle by transition.animateFloat(
		initialValue = 0f,
		targetValue = 360f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 10000),
			repeatMode = RepeatMode.Restart
		)
	)
	Box(
		modifier = modifier
			.size(size)
			.clip(RoundedCornerShape(cornerRadius))
			.gradientBackground(
				colors = colors,
				angle = angle
			)
			.padding(borderWidth)
	) {
		Image(
			bitmap = image.asImageBitmap(),
			contentDescription = null,
			contentScale = ContentScale.Fit,
			modifier = Modifier
				.clip(RoundedCornerShape(cornerRadius - borderWidth))
				.fillMaxSize()
		)
	}
}

@Preview(
	showBackground = true,
	uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun QRCodeImagePreview() {
	QRCodeImage(
		image = ItemUiState(
			ssid = "asdkoda-wifi",
			password = "secret"
		).toBitmap()
	)
}

@Composable
private fun DotIndicators(
	itemsCount: Int,
	currentPageIndex: Int,
	modifier: Modifier = Modifier
) {
	Row(modifier = modifier.padding(20.dp)) {
		// ???
		val startIndex = max(0, min(currentPageIndex-2, itemsCount-5))
		val endIndex = min(itemsCount-1, max(currentPageIndex+2, 4))
		Log.d("asdqwe", "DotIndicators: $startIndex, $endIndex, $currentPageIndex")
		(0 until itemsCount)
			.toList()
			.slice(startIndex..endIndex)
			.forEach {
				val color = animateColorAsState(
					targetValue = if (it == currentPageIndex) Color.White else Color.Gray,
					animationSpec = tween(
						durationMillis = 300,
						easing = LinearEasing
					)
				)
				val width = animateDpAsState(
					targetValue = if (it == currentPageIndex) 18.dp else 6.dp,
					animationSpec = tween(
						durationMillis = 150,
						easing = LinearEasing
					)
				)
				Box(modifier = Modifier
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
	DotIndicators(
		itemsCount = 5,
		currentPageIndex = 3
	)
}