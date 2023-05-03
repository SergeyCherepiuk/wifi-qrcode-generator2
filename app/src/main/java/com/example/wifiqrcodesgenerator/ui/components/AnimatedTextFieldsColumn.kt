package com.example.wifiqrcodesgenerator.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.example.wifiqrcodesgenerator.ui.theme.WifiQRCodesGeneratorTheme

@Composable
fun AnimatedTextFields(
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
        val focusManager = LocalFocusManager.current
        TextFieldsColumn(
            ssid = ssid,
            updateSsid = updateSsid,
            password = password,
            updatePassword = updatePassword,
            clearFocus = focusManager::clearFocus
        )
    }
}

@Preview
@Composable
private fun AnimatedTextFieldsPreview() {
    WifiQRCodesGeneratorTheme {
        Surface {
            AnimatedTextFields(
                ssid = "asdkoda-wifi",
                updateSsid = { },
                password = "secret",
                updatePassword = { },
                isInEditingMode = true
            )
        }
    }
}