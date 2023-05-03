package com.example.wifiqrcodesgenerator.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifiqrcodesgenerator.ui.theme.WifiQRCodesGeneratorTheme

@Composable
fun TextFieldsColumn(
	ssid: String,
	updateSsid: (String) -> Unit,
	password: String,
	updatePassword: (String) -> Unit,
	clearFocus: () -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		verticalArrangement = Arrangement.spacedBy(24.dp),
		modifier = modifier
	) {
		OutlinedTextField(
			value = ssid,
			onValueChange = updateSsid,
			singleLine = true,
			label = { Text("SSID") },
			shape = CircleShape,
			keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
			modifier = Modifier.fillMaxWidth()
		)
		var isPasswordVisible by remember { mutableStateOf(false) }
		val passwordTrailingIcon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
		val passwordVisualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
		OutlinedTextField(
			value = password,
			onValueChange = updatePassword,
			singleLine = true,
			label = { Text("Password") },
			shape = CircleShape,
			trailingIcon = {
				Icon(
					imageVector = passwordTrailingIcon,
					contentDescription = null,
					modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
				)
			},
			visualTransformation = passwordVisualTransformation,
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Password,
				imeAction = ImeAction.Done
			),
			keyboardActions = KeyboardActions(onDone = { clearFocus() }),
			modifier = Modifier.fillMaxWidth()
		)
	}
}

@Preview
@Composable
private fun TextFieldsColumn() {
	WifiQRCodesGeneratorTheme {
		Surface {
			TextFieldsColumn(
				ssid = "asdkoda-wifi",
				updateSsid = {  },
				password = "secret",
				updatePassword = {  },
				clearFocus = {  }
			)
		}
	}
}