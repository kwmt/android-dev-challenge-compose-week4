package com.example.androiddevchallenge.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.sp

private val labelTextSize = 18.sp
private val paint = android.graphics.Paint().apply {
    this.textAlign = android.graphics.Paint.Align.CENTER
    this.color = Color.Black.toLegacyInt()
}

fun paint(drawScope: DrawScope) = with(drawScope) {
    paint.apply {
        this.textSize = labelTextSize.toPx()
    }
}

private fun Color.toLegacyInt(): Int {
    return android.graphics.Color.argb(
        (alpha * 255.0f + 0.5f).toInt(),
        (red * 255.0f + 0.5f).toInt(),
        (green * 255.0f + 0.5f).toInt(),
        (blue * 255.0f + 0.5f).toInt()
    )
}
