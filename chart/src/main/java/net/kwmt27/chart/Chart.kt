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

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import net.kwmt27.chart.model.ChartData
import net.kwmt27.chart.util.paint
import net.kwmt27.chart.util.textPaint

@Composable
fun Chart(
    modifier: Modifier = Modifier,
    list: List<ChartData>,
    lineColor: Color = Color.Blue,
    circleColor: Color = Color.White,
    distanceFromOffsetToText: Dp = 10.dp,
    textColor: Color = Color.Black,
    textSize: TextUnit = 18.sp,
) {
    ChartArea(
        modifier = modifier
    ) {
        val context = LocalContext.current
        var offset by remember { mutableStateOf(0f) }
        Canvas(
            modifier =
            Modifier
                .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)
                .fillMaxSize()
                .scrollable(
                    orientation = Orientation.Horizontal,
                    state = rememberScrollableState { delta ->
                        offset += delta / 100
                        Log.d("kwmt", "offset: $offset")
                        delta
                    }
                )
        ) {
            // X-axis draw time
            drawIntoCanvas { canvas ->
                val height = size.height
                val xUnit = 120f // width / list.size
                val minValue = list.minOf { it.offset.y } // 0??
                val maxValue = list.maxOf { it.offset.y } // 11??
                val diff = maxValue - minValue
                val yUnit = (height - 60.dp.toPx()) / diff
                val textPaint = textPaint(textColor)

                list.forEach { chartData ->
                    val (offsetX, screenOffsetY) = newOffset(
                        chartData.offset.copy(chartData.offset.x + offset, chartData.offset.y),
                        xUnit,
                        yUnit,
                        minValue,
                        height
                    )
                    // FIXME: force unwrap
                    val imageBitmap =
                        ContextCompat.getDrawable(context, chartData.imageDrawable!!)!!.toBitmap()
                            .asImageBitmap()
                    val halfImageWidth = imageBitmap.width / 2
                    drawImage(
                        imageBitmap, Offset(offsetX - halfImageWidth, 10f)
                    )
                    drawTextXAxisPoint(
                        canvas,
                        chartData.textOnXAxis,
                        offsetX,
                        0f,
                        textPaint,
                        13.sp,
                        this
                    )
                }
                // draw chart
                drawChart(
                    canvas,
                    list.map {
                        it.copy(
                            offset = Offset(
                                it.offset.x + offset,
                                it.offset.y
                            )
                        )
                    },
                    xUnit,
                    yUnit,
                    height,
                    minValue,
                    lineColor,
                    circleColor,
                    this,
                    distanceFromOffsetToText.toPx(),
                    textColor,
                    textSize,
                )
            }
        }
    }
}

@Composable
fun ChartArea(modifier: Modifier, content: @Composable (BoxScope.() -> Unit)) {
    Box(
        modifier = modifier,
        content = content,
    )
}

private fun drawChart(
    canvas: Canvas,
    list: List<ChartData>,
    xUnit: Float,
    yUnit: Float,
    height: Float,
    minValue: Float,
    lineColor: Color,
    circleColor: Color,
    drawScope: DrawScope,
    textOnLineHeight: Float,
    textColor: Color,
    textSize: TextUnit,
) {
    drawChartLine(canvas, list, xUnit, yUnit, minValue, height, lineColor)
    drawCircleAndText(
        canvas,
        list,
        circleColor,
        textColor,
        xUnit,
        yUnit,
        minValue,
        height,
        textOnLineHeight,
        textSize,
        drawScope
    )
}

/**
 * ?????????????????????????????????????????????
 */
private fun drawCircleAndText(
    canvas: Canvas,
    list: List<ChartData>,
    circleColor: Color,
    textColor: Color,
    xUnit: Float,
    yUnit: Float,
    minValue: Float,
    height: Float,
    textOnLineHeight: Float,
    textSize: TextUnit,
    drawScope: DrawScope
) {
    val circlePaint = Paint().apply {
        this.color = circleColor
        this.style = PaintingStyle.Fill
    }
    val textPaint = textPaint(textColor)
    list.forEach { chartData ->
        val (offsetX, screenOffsetY) = newOffset(chartData.offset, xUnit, yUnit, minValue, height)
        drawTextPoint(
            canvas,
            chartData,
            offsetX,
            screenOffsetY,
            textOnLineHeight,
            textPaint,
            textSize,
            drawScope
        )
        drawCirclePoint(canvas, offsetX, screenOffsetY, circlePaint)
    }
}

/**
 * ??????????????????????????????
 */
private fun drawCirclePoint(
    canvas: Canvas,
    offsetX: Float,
    screenOffsetY: Float,
    circlePaint: Paint
) {
    canvas.drawCircle(Offset(offsetX, screenOffsetY), radius = 10f, circlePaint)
}

/**
 * ???????????????????????????????????????
 */
private fun drawTextPoint(
    canvas: Canvas,
    chartData: ChartData,
    offsetX: Float,
    screenOffsetY: Float,
    textOnLineHeight: Float,
    textPaint: android.graphics.Paint,
    textSize: TextUnit,
    drawScope: DrawScope
) {
    canvas.nativeCanvas.drawText(
        chartData.textOnOffset,
        offsetX,
        screenOffsetY - textOnLineHeight,
        paint(drawScope = drawScope, paint = textPaint, textUnit = textSize)
    )
}

private fun drawTextXAxisPoint(
    canvas: Canvas,
    text: String,
    offsetX: Float,
    height: Float,
    textPaint: android.graphics.Paint,
    textSize: TextUnit,
    drawScope: DrawScope
) {
    canvas.nativeCanvas.drawText(
        text,
        offsetX,
        height,
        paint(drawScope = drawScope, paint = textPaint, textUnit = textSize)
    )
}

private fun drawChartLine(
    canvas: Canvas,
    list: List<ChartData>,
    xUnit: Float,
    yUnit: Float,
    minValue: Float,
    height: Float,
    lineColor: Color
) {
    // chart
    val path = Path().apply {
        list.forEachIndexed { index, chartData ->
            val (offsetX, screenOffsetY) = newOffset(
                chartData.offset,
                xUnit,
                yUnit,
                minValue,
                height
            )
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
}

/**
 * ????????????
 */
private fun newOffset(
    offset: Offset,
    xUnit: Float,
    yUnit: Float,
    minValue: Float,
    height: Float
): Offset {
    val offsetX = offset.x * xUnit
    val offsetY = offset.y * yUnit - minValue * yUnit // ???????????????
    val screenOffsetY = height - offsetY // ????????????
    return Offset(offsetX, screenOffsetY)
}
