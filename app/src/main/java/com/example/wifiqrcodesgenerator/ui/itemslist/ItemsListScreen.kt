package com.example.wifiqrcodesgenerator.ui.itemslist

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.wifiqrcodesgenerator.models.QRCode
import com.example.wifiqrcodesgenerator.models.toBitmap
import com.example.wifiqrcodesgenerator.modifiers.advancedShadow
import com.example.wifiqrcodesgenerator.navigation.Destinations
import com.example.wifiqrcodesgenerator.ui.components.AnimatedTextFields
import com.example.wifiqrcodesgenerator.ui.components.ElevatedButtonsRow
import com.example.wifiqrcodesgenerator.ui.components.DotIndicators
import com.example.wifiqrcodesgenerator.ui.components.FloatingActionButtonsColumn
import com.example.wifiqrcodesgenerator.ui.components.QRCodeImage
import com.example.wifiqrcodesgenerator.ui.theme.Red
import com.example.wifiqrcodesgenerator.ui.theme.WifiQRCodesGeneratorTheme

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
			shareImage = viewModel::shareImage,
			navigateToReorderItems = navController::navigateToReorderItems,
			navigateToAddItem = navController::navigateToAddItem,
			modifier = Modifier.fillMaxSize()
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
	updateItem: (QRCode, String, String) -> Unit,
	deleteItem: (QRCode) -> Unit,
	shareImage: (Context, QRCode) -> Unit,
	navigateToReorderItems: () -> Unit,
	navigateToAddItem: () -> Unit,
	modifier: Modifier = Modifier
) {
	Box(modifier = modifier) {
		val pagerState = rememberPagerState()
		HorizontalPager(
			pageCount = uiState.items.size,
			state = pagerState
		) { pageIndex ->
			QRCodePage(
				item = uiState.items[pageIndex],
				updateItem = updateItem,
				deleteItem = deleteItem,
				shareImage = shareImage
			)
		}
		DotIndicators(
			itemsCount = uiState.items.size,
			currentPageIndex = pagerState.currentPage,
			modifier = Modifier
				.fillMaxSize()
				.wrapContentSize(Alignment.BottomCenter)
				.padding(20.dp)
		)
		FloatingActionButtonsColumn(
			itemsCount = uiState.items.size,
			navigateToReorderItems = navigateToReorderItems,
			navigateToAddItem = navigateToAddItem,
			modifier = Modifier
				.fillMaxSize()
				.wrapContentSize(Alignment.BottomEnd)
				.padding(end = 24.dp, bottom = 24.dp)
		)
	}
}

@Preview
@Composable
private fun ItemsListScreenPreview() {
	WifiQRCodesGeneratorTheme {
		Surface {
			val uiState = ItemsListUiState(
				items = listOf(
					QRCode(
						ssid = "asdkoda-wifi",
						password = "secret"
					)
				)
			)
			ItemsListScreen(
				uiState = uiState,
				updateItem = { _, _, _ -> },
				deleteItem = { },
				shareImage = { _, _ -> },
				navigateToReorderItems = {  },
				navigateToAddItem = {  },
				modifier = Modifier.fillMaxSize()
			)
		}
	}
}

@Composable
private fun QRCodePage(
	item: QRCode,
	modifier: Modifier = Modifier,
	updateItem: (QRCode, String, String) -> Unit,
	deleteItem: (QRCode) -> Unit,
	shareImage: (Context, QRCode) -> Unit,
) {
	var ssid by remember { mutableStateOf(item.ssid) }
	val updateSsid: (String) -> Unit = { value -> ssid = value }
	var password by remember { mutableStateOf(item.password) }
	val updatePassword: (String) -> Unit = { value -> password = value }
	var isInEditingMode by remember { mutableStateOf(false) }
	val toggleEditingMode = { isInEditingMode = !isInEditingMode }
	Box(modifier = modifier) {
		AnimatedTextFields(
			ssid = ssid,
			updateSsid = updateSsid,
			password = password,
			updatePassword = updatePassword,
			isInEditingMode = isInEditingMode,
			modifier = Modifier
				.align(Alignment.TopCenter)
				.padding(24.dp)
		)
		val paddingTop = animateDpAsState(
			targetValue = if (isInEditingMode) 200.dp else 0.dp,
			animationSpec = tween(
				durationMillis = 150,
				easing = EaseOut
			),
			label = "paddingTop"
		)
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier
				.padding(top = paddingTop.value)
				.fillMaxSize()
				.wrapContentSize(Alignment.Center)
		) {
			val infiniteTransition = rememberInfiniteTransition()
			val shadowBlurRadius = infiniteTransition.animateFloat(
				initialValue = 30f,
				targetValue = 45f,
				animationSpec = infiniteRepeatable(
					animation = tween(durationMillis = 5000),
					repeatMode = RepeatMode.Reverse
				),
				label = "shadowBlur"
			)
			val cornerRadius: Dp = 75.dp
			BoxWithConstraints {
				val size = min(maxWidth, maxHeight)
				val sizeFraction = if (size < 500.dp) 0.75f else 0.5f
				QRCodeImage(
					image = item.toBitmap(),
					cornerRadius = cornerRadius,
					modifier = Modifier
						.size(size * sizeFraction)
						.advancedShadow(
							color = Red,
							alpha = 0.75f,
							cornersRadius = cornerRadius,
							shadowBlurRadius = shadowBlurRadius.value.dp
						)
				)
			}
			Text(
				text = item.ssid,
				fontSize = 24.sp,
				fontWeight = FontWeight.Medium,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
				modifier = Modifier.padding(28.dp)
			)
			ElevatedButtonsRow(
				item = item,
				isInEditingMode = isInEditingMode,
				toggleEditingMode = toggleEditingMode,
				updateItem = updateItem,
				textFieldSsid = ssid,
				textFieldPassword = password,
				deleteItem = deleteItem,
				shareImage = shareImage
			)
		}
	}
}