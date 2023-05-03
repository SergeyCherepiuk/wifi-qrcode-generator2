package com.example.wifiqrcodesgenerator.utils

import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics
import kotlin.run

data class Resolution(
    val width: Int,
    val height: Int
)

object ScreenResolution {
    var windowManager: WindowManager? = null

    fun getScreenResolution(): Resolution {
        windowManager?.let {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val windowMetrics: WindowMetrics = it.getCurrentWindowMetrics()
                val insets: Insets = windowMetrics
                    .windowInsets
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                val width = windowMetrics.bounds.width() - insets.left - insets.right
                val height = windowMetrics.bounds.height() - insets.top - insets.bottom
                Resolution(width = width, height = height)
            } else {
                val displayMetrics = DisplayMetrics()
                it.getDefaultDisplay().getMetrics(displayMetrics)
                Resolution(width = displayMetrics.widthPixels, height = displayMetrics.heightPixels)
            }
        } ?: run { return Resolution(width = 0, height = 0) }
    }
}