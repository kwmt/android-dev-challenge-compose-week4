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