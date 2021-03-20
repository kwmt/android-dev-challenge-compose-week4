package com.example.androiddevchallenge.ui.chart

import android.graphics.Point
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

@Composable
fun Chart(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize().background(color = Color.Gray)) {
        val canvasWidth = size.width
        val canvasHeight = size.height


        val list = getPoints(size.copy(height = size.height/2))


        val path = Path().apply {


            list.forEachIndexed { index, offset ->
                if(index == 0) {
                    moveTo(offset.x, offset.y)

                } else {
                    lineTo(offset.x, offset.y)
                }

            }


        }
        val color = Color.Blue
        val paint = Paint().apply {
            this.color = color
            this.style = PaintingStyle.Stroke
            this.strokeWidth = 10f
            this.isAntiAlias = true
        }
        drawIntoCanvas { canvas ->
            canvas.drawPath(
                path, paint
            )
        }
    }
}

fun getPoints(size: Size): List<Offset> {
    val wUnit = size.width / 5
    val hUnit = size.height / 10

    return listOf(
        Offset(wUnit, hUnit * 2),
        Offset(wUnit * 2, hUnit ),
        Offset(wUnit * 3, hUnit * 3),
        Offset(wUnit * 4, hUnit * 4),
        )
}