package com.example.wifiqrcodesgenerator.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifiqrcodesgenerator.models.QRCode
import com.example.wifiqrcodesgenerator.ui.theme.WifiQRCodesGeneratorTheme


@Composable
fun ElevatedButtonsRow(
    item: QRCode,
    modifier: Modifier = Modifier,
    isInEditingMode: Boolean = false,
    toggleEditingMode: () -> Unit,
    updateItem: (QRCode, String, String) -> Unit,
    textFieldSsid: String,
    textFieldPassword: String,
    deleteItem: (QRCode) -> Unit,
    shareImage: (Context, QRCode) -> Unit
) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.width(250.dp)
    ) {
        ElevatedButton(
            onClick = {
                val isChanged: Boolean = item.ssid != textFieldSsid || item.password != textFieldPassword
                if (isInEditingMode && isChanged) {
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
        ElevatedButton(
            onClick = {
                if (isInEditingMode) {
                    toggleEditingMode()
                }
                deleteItem(item)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
        ElevatedButton(
            onClick = { shareImage(context, item) }
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun ButtonsRowPreview() {
    WifiQRCodesGeneratorTheme {
        Surface {
            ElevatedButtonsRow(
                item = QRCode(
                    ssid = "asdkoda-wifi",
                    password = "secret"
                ),
                toggleEditingMode = { },
                updateItem = { _, _, _ -> },
                textFieldSsid = "new-asdkoda-wifi",
                textFieldPassword = "new-secret",
                deleteItem = { },
                shareImage = { _, _ -> }
            )
        }
    }
}