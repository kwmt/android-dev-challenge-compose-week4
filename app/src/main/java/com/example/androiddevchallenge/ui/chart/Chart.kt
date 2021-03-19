package com.example.androiddevchallenge.ui.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

@Composable
fun Chart() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val path = Path().apply {
            moveTo(0f, canvasHeight / 2)
            lineTo(canvasWidth / 2, 0f)
            lineTo(canvasWidth, canvasHeight /2 )
        }
        val color = Color.Black
        val paint = Paint().apply {
            this.color = color
            this.style = PaintingStyle.Stroke
            this.isAntiAlias = true
        }
        drawIntoCanvas { canvas ->
            canvas.drawPath(
                path, paint
            )
        }
    }
}