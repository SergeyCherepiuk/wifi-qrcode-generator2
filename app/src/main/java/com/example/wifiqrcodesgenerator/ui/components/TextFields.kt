package com.example.wifiqrcodesgenerator.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextFields(
	ssid: String,
	updateSsid: (String) -> Unit,
	password: String,
	updatePassword: (String) -> Unit,
	modifier: Modifier = Modifier
) {
	Column(modifier = modifier) {
		OutlinedTextField(
			value = ssid,
			onValueChange = updateSsid,
			maxLines = 1,
			label = { Text("SSID") },
			shape = RoundedCornerShape(20.dp),
			modifier = Modifier
				.fillMaxWidth()
				.padding(10.dp),
		)
		var isPasswordVisible by remember { mutableStateOf(false) }
		OutlinedTextField(
			value = password,
			onValueChange = updatePassword,
			maxLines = 1,
			label = { Text("Password") },
			shape = RoundedCornerShape(20.dp),
			trailingIcon = { Icon(
				imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
				contentDescription = null,
				modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
			) },
			visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
			modifier = Modifier
				.fillMaxWidth()
				.padding(10.dp)
		)
	}
}

@Preview(
	showBackground = true,
	uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AnimatedTextFieldsPreview() {
	TextFields(
		ssid = "asdkoda-wifi",
		updateSsid = {  },
		password = "secret",
		updatePassword = {  }
	)
}