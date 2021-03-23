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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.domain.model.createData
import com.example.androiddevchallenge.domain.model.translateTemperatureByTimeToChartData
import com.example.androiddevchallenge.ui.theme.DevChallengeColors
import com.example.androiddevchallenge.ui.theme.DevChallengeScaffold
import com.example.androiddevchallenge.ui.theme.DevChallengeTheme
import com.example.androiddevchallenge.ui.theme.GrayAlpha
import com.example.androiddevchallenge.ui.utils.DrawableResImage
import net.kwmt27.chart.Chart

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevChallengeScaffold {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
//    Surface(color = MaterialTheme.colors.background) {
    DevChallengeScaffold {
        Box(modifier =  Modifier.fillMaxHeight()) {


            DrawableResImage(

                modifier = Modifier.fillMaxSize(),
                drawableRes = R.drawable.sunrise,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
//                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                HomeTitle(text = "Browse themes", before = 32.dp)
                val m = Modifier.height(100.dp)
                Box(
                    modifier = m
                ) {
                    Chart(
                        modifier = Modifier.background(GrayAlpha),
                        list = translateTemperatureByTimeToChartData(createData()),
                        lineColor = DevChallengeTheme.colors.surface,
                        textColor = DevChallengeTheme.colors.surface,
                        circleColor = DevChallengeTheme.colors.surface,
                    )
                }
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    DevChallengeScaffold {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    DevChallengeScaffold(darkTheme = true) {
        MyApp()
    }
}

@Composable
private fun HomeTitle(
    modifier: Modifier = Modifier,
    text: String,
    before: Dp,
    isFilterIcon: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier
                .paddingFrom(alignmentLine = LastBaseline, before = before),
            text = text,
            style = DevChallengeTheme.typography.h1,
            color = DevChallengeTheme.colors.textH1
        )
        if (isFilterIcon) {
            Icon(
                Icons.Filled.FilterList, contentDescription = null,
                tint = DevChallengeTheme.colors.textBody1,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Bottom)
            )
        }
    }
}
