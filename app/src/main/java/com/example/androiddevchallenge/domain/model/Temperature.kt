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
package com.example.androiddevchallenge.domain.model

import androidx.compose.ui.geometry.Offset
import com.example.androiddevchallenge.ui.chart.model.ChartData
import org.threeten.bp.LocalTime

/**
 * 時間とそのときの温度
 */
data class Temperature(
    /**
     * 時間
     */
    val time: LocalTime,
    /**
     * 温度
     */
    val temperature: Float,
)

fun translateTemperatureByTimeToChartData(temperatures: List<Temperature>): List<ChartData> {
//    return emptyList()
    val min = temperatures.minOf { temperature ->
        temperature.time
    }

    return temperatures.map { temperature ->
        val newTime = temperature.time.minusHours(min.hour.toLong())
        temperature.copy(time = newTime)
    }.map { temperature ->
        ChartData(
            offset = Offset(x = temperature.time.hour.toFloat(), y = temperature.temperature),
            textOnOffset = temperature.temperature.toString()
        )
    }
}

fun createData(): List<Temperature> {
    return listOf(
        Temperature(
            time = LocalTime.of(6, 0),
            temperature = 5f,
        ),
        Temperature(
            time = LocalTime.of(7, 0),
            temperature = 5f,
        ),
        Temperature(
            time = LocalTime.of(8, 0),
            temperature = 6f,
        ),
        Temperature(
            time = LocalTime.of(9, 0),
            temperature = 6f,
        ),
        Temperature(
            time = LocalTime.of(10, 0),
            temperature = 7f,
        ),
        Temperature(
            time = LocalTime.of(11, 0),
            temperature = 8f,
        ),
        Temperature(
            time = LocalTime.of(12, 0),
            temperature = 9f,
        ),
        Temperature(
            time = LocalTime.of(13, 0),
            temperature = 10f,
        ),
        Temperature(
            time = LocalTime.of(14, 0),
            temperature = 12f,
        ),
        Temperature(
            time = LocalTime.of(15, 0),
            temperature = 11f,
        ),
        Temperature(
            time = LocalTime.of(16, 0),
            temperature = 10f,
        ),
        Temperature(
            time = LocalTime.of(17, 0),
            temperature = 8f,
        ),
        Temperature(
            time = LocalTime.of(18, 0),
            temperature = 6f,
        ),
    )
}