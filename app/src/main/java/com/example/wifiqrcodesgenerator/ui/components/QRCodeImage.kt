package com.example.wifiqrcodesgenerator.ui.components

import android.graphics.Bitmap
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wifiqrcodesgenerator.models.QRCode
import com.example.wifiqrcodesgenerator.models.toBitmap
import com.example.wifiqrcodesgenerator.modifiers.gradientBackground
import com.example.wifiqrcodesgenerator.ui.theme.Orange
import com.example.wifiqrcodesgenerator.ui.theme.Red
import com.example.wifiqrcodesgenerator.ui.theme.WifiQRCodesGeneratorTheme

@Composable
fun QRCodeImage(
    image: Bitmap,
    modifier: Modifier = Modifier,
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
        ),
        label = "gradientAngle"
    )
    Box(
        modifier = modifier
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
            modifier = Modifier.clip(RoundedCornerShape(cornerRadius - borderWidth))
        )
    }
}

@Preview
@Composable
private fun QRCodeImagePreview() {
    WifiQRCodesGeneratorTheme {
        Surface {
            QRCodeImage(
                image = QRCode(
                    ssid = "asdkoda-wifi",
                    password = "secret"
                ).toBitmap(),
                cornerRadius = 50.dp,
                borderWidth = 10.dp
            )
        }
    }
}