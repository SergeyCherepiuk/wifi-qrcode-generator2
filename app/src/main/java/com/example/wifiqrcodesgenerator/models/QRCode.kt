package com.example.wifiqrcodesgenerator.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wifiqrcodesgenerator.utils.generateBitmap

@Entity(tableName = "qrcode")
data class QRCode(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var ssid: String,
    var password: String,
)

fun QRCode.toBitmap(): Bitmap = generateBitmap(this.ssid, this.password)