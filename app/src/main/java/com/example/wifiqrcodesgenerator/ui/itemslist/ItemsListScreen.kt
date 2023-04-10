package com.example.wifiqrcodesgenerator.ui.itemslist

import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.wifiqrcodesgenerator.modifiers.advancedShadow
import com.example.wifiqrcodesgenerator.modifiers.gradientBackground
import com.example.wifiqrcodesgenerator.navigation.Destinations
import com.example.wifiqrcodesgenerator.ui.components.TextFields
import com.example.wifiqrcodesgenerator.ui.theme.Orange
import com.example.wifiqrcodesgenerator.ui.theme.Red
import java.lang.Integer.min
import java.lang.Integer.max

fun NavGraphBuilder.itemsList(
	navController: NavController,
	viewModel: ItemsListViewModel
) {
	composable(Destinations.ITEMS_LIST_ROUTE) {
		val uiState by viewModel.uiState.collectAsState()
		ItemsListScreen(
			uiState = uiState,
			updateItem = viewModel::updateItem,
			deleteItem = viewModel::deleteItem,
			navigateToReorderItems = navController::navigateToReorderItems,
			navigateToAddItem = navController::navigateToAddItem
		)
	}
}

fun NavController.navigateToItemsList() {
	navigate(Destinations.ITEMS_LIST_ROUTE) {
		launchSingleTop = true
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemsListScreen(
	uiState: ItemsListUiState,
	updateItem: (ItemUiState, String, String) -> Unit,
	deleteItem: (ItemUiState) -> Unit,
	navigateToReorderItems: () -> Unit,
	navigateToAddItem: () -> Unit,
	modifier: Modifier = Modifier
) {
	Box(modifier = modifier.fillMaxSize()) {
		val pagerState = rememberPagerState()
		LaunchedEffect(pagerState.currentPageOffsetFraction) {
			Log.d("qweasd", "ItemsListScreen: ${pagerState.currentPageOffsetFraction}")
		}
		HorizontalPager(
			pageCount = uiState.items.size,
			state = pagerState
		) { pageIndex ->
			QRCodePage(
				item = uiState.items[pageIndex],
				updateItem = updateItem,
				deleteItem = deleteItem
			)
		}
		DotIndicators(
			itemsCount = uiState.items.size,
			currentPageIndex = pagerState.currentPage,
			modifier = Modifier.align(Alignment.BottomCenter)
		)
		FloatingActionButtons(
			itemsCount = uiState.items.size,
			navigateToReorderItems = navigateToReorderItems,
			navigateToAddItem = navigateToAddItem,
			modifier = Modifier.align(Alignment.BottomEnd)
		)
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
		AnimatedTextFields(
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
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
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
private fun AnimatedTextFields(
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
		TextFields(
			ssid = ssid,
			updateSsid = updateSsid,
			password = password,
			updatePassword = updatePassword
		)
	}
}

@Preview(
	showBackground = true,
	uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AnimatedTextFieldsPreview() {
	AnimatedTextFields(
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
		val startIndex = max(0, min(currentPageIndex-2, itemsCount-5))
		val endIndex = min(itemsCount-1, max(currentPageIndex+2, 4))
		(startIndex..endIndex).forEach {
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

@Preview(
	showBackground = true,
	uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DotIndicatorsPreview() {
	DotIndicators(
		itemsCount = 5,
		currentPageIndex = 3
	)
}

@Composable
private fun FloatingActionButtons(
	itemsCount: Int,
	navigateToReorderItems: () -> Unit,
	navigateToAddItem: () -> Unit,
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier) {
		AnimatedVisibility(
			visible = itemsCount > 1,
			enter = slideInHorizontally { it },
			exit = slideOutHorizontally { it }
		) {
			FloatingActionButton(
				onClick = navigateToReorderItems,
				modifier = Modifier.padding(end = 24.dp, bottom = 24.dp)
			) {
				Icon(
					imageVector = Icons.Default.Reorder,
					contentDescription = null
				)
			}
		}
		FloatingActionButton(
			onClick = navigateToAddItem,
			modifier = Modifier.padding(end = 24.dp, bottom = 24.dp)
		) {
			Icon(
				imageVector = Icons.Default.Add,
				contentDescription = null
			)
		}
	}
}