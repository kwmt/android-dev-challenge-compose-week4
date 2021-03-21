/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.kwmt27.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.kwmt27.chart.model.ChartData
import net.kwmt27.chart.util.paint

@Composable
fun Chart(
    modifier: Modifier = Modifier,
    chartDataList: List<ChartData>,
    lineColor: Color = Color.Blue,
    circleColor: Color = Color.White,
    distanceFromOffsetToText: Dp = 10.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    ) {
        drawIntoCanvas { canvas ->
            drawChart(
                canvas,
                size,
                chartDataList,
                lineColor,
                circleColor,
                this,
                distanceFromOffsetToText.toPx()
            )
        }
    }
}

private fun drawChart(
    canvas: Canvas,
    size: Size,
    list: List<ChartData>,
    lineColor: Color,
    circleColor: Color,
    drawScope: DrawScope,
    textOnLineHeight: Float
) {
    val width = size.width
    val height = size.height
    val xUnit = width / list.size
    val minValue = list.minOf { it.offset.y }
    val maxValue = list.maxOf { it.offset.y }
    val diff = maxValue - minValue
    val yUnit = height / diff
    val path = Path().apply {
        list.forEachIndexed { index, chartData ->
            val (offsetX, screenOffsetY) = newOffset(chartData, xUnit, yUnit, minValue, height)
            if (index == 0) {
                moveTo(offsetX, screenOffsetY)
            } else {
                lineTo(offsetX, screenOffsetY)
            }
        }
    }
    val linePaint = Paint().apply {
        this.color = lineColor
        this.style = PaintingStyle.Stroke
        this.strokeWidth = 10f
        this.isAntiAlias = true
    }
    canvas.drawPath(
        path, linePaint
    )
    val circlePaint = Paint().apply {
        this.color = circleColor
        this.style = PaintingStyle.Fill
    }
    list.forEach { chartData ->
        val (offsetX, screenOffsetY) = newOffset(chartData, xUnit, yUnit, minValue, height)
        canvas.nativeCanvas.drawText(
            chartData.textOnOffset,
            offsetX,
            screenOffsetY - textOnLineHeight,
            paint(drawScope = drawScope)
        )

        canvas.drawCircle(Offset(offsetX, screenOffsetY), radius = 10f, circlePaint)
    }
}

/**
 * 座標変換
 */
private fun newOffset(
    chartData: ChartData,
    xUnit: Float,
    yUnit: Float,
    minValue: Float,
    height: Float
): Offset {
    val offsetX = chartData.offset.x * xUnit
    val offsetY = chartData.offset.y * yUnit - minValue * yUnit // オフセット
    val screenOffsetY = height - offsetY // 座標変換
    return Offset(offsetX, screenOffsetY)
}
