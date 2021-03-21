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
package com.example.androiddevchallenge.ui.chart.model

import androidx.compose.ui.geometry.Offset

data class ChartData(
    /**
     * x,y座標
     */
    val offset: Offset,
    /**
     * x,y座標の上に書くテキスト
     */
    val textOnOffset: String = "",
//    /**
//     * 点を表す円のカラー
//     */
//    val circleColor: Color = Color.White
)
