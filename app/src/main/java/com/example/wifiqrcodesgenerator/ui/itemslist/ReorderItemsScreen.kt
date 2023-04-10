package com.example.wifiqrcodesgenerator.ui.itemslist

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.wifiqrcodesgenerator.navigation.Destinations
import com.example.wifiqrcodesgenerator.ui.theme.DarkGray
import org.burnoutcrew.reorderable.*

fun NavGraphBuilder.reorderItems(
	navController: NavController,
	viewModel: ItemsListViewModel
) {
	composable(Destinations.REORDER_ITEMS_ROUTE) {
		val uiState by viewModel.uiState.collectAsState()
		ReorderItemsScreen(
			uiState = uiState,
			reorderItems = viewModel::reorderItems,
			navigateUp = navController::navigateUp
		)
	}
}

fun NavController.navigateToReorderItems() {
	navigate(Destinations.REORDER_ITEMS_ROUTE) {
		launchSingleTop = true
	}
}

@Composable
fun ReorderItemsScreen(
	uiState: ItemsListUiState,
	reorderItems: (Int, Int) -> Unit,
	navigateUp: () -> Unit
) {
	Box {
		val reorderableState = rememberReorderableLazyListState(onMove = { from, to ->
			reorderItems(from.index, to.index)
		})
		LazyColumn(
			state = reorderableState.listState,
			modifier = Modifier
				.fillMaxSize()
				.reorderable(reorderableState)
		) {
			items(uiState.items, { System.identityHashCode(it) }) { item ->
				ReorderableItem(
					reorderableState = reorderableState,
					key = System.identityHashCode(item)
				) {
					ItemRow(
						item = item,
						modifier = Modifier.detectReorder(reorderableState)
					)
				}
			}
		}
		FloatingActionButton(
			onClick = navigateUp,
			modifier = Modifier
				.padding(end = 24.dp, bottom = 24.dp)
				.align(Alignment.BottomEnd)
		) {
			Icon(
				imageVector = Icons.Default.Check,
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
private fun ReorderItemsScreenPreview() {
	ReorderItemsScreen(
		uiState = ItemsListUiState(
			listOf(
				ItemUiState(
					ssid = "free-hotspot",
					password = "asdf-fgg4"
				),
				ItemUiState(
					ssid = "asdkoda-wifi",
					password = "secret"
				),
				ItemUiState(
					ssid = "public-wifi",
					password = ""
				)
			)
		),
		reorderItems = { _, _ -> },
		navigateUp = {  }
	)
}

@Composable
private fun ItemRow(
	item: ItemUiState,
	modifier: Modifier = Modifier
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier
			.fillMaxWidth()
			.background(DarkGray)
			.padding(16.dp)
	) {
		Text(
			text = item.ssid,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
			fontSize = 20.sp,
			modifier = Modifier.weight(1f)
		)
		Icon(
			imageVector = Icons.Default.Reorder,
			contentDescription = null,
			modifier = Modifier.padding(start = 12.dp)
		)
	}
}

@Preview(
	showBackground = true,
	uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ItemRowPreview() {
	ItemRow(
		item = ItemUiState(
			ssid = "asdkoda-wifi",
			password = "secret"
		)
	)
}