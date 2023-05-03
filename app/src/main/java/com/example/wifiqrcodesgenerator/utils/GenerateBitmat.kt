package com.example.wifiqrcodesgenerator.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.lang.Integer.max
import java.lang.Integer.min


// Credits: https://stackoverflow.com/a/64504871
fun generateBitmap(ssid: String, password: String): Bitmap {
	val (displayWidthPx, displayHeightPx) = ScreenResolution.getScreenResolution()
	val size: Int = max(800, min(displayWidthPx, displayHeightPx))

	val writer = QRCodeWriter()
	val text = "WIFI:S:$ssid;T:WPA;P:$password;H:;;"
	val bitMatrix: BitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size)

	val width: Int = bitMatrix.getWidth()
	val height: Int = bitMatrix.getHeight()
	val pixels = IntArray(width * height)
	for (y in 0 until height) {
		for (x in 0 until width) {
			pixels[y * width + x] = if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
		}
	}

	val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
	bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
	return bitmap
}