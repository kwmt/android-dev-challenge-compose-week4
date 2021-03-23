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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.androiddevchallenge.domain.model.createData
import com.example.androiddevchallenge.domain.model.translateTemperatureByTimeToChartData
import com.example.androiddevchallenge.ui.theme.DevChallengeScaffold
import com.example.androiddevchallenge.ui.theme.DevChallengeTheme
import com.example.androiddevchallenge.ui.theme.GrayAlpha
import com.example.androiddevchallenge.ui.utils.DrawableResImage
import com.example.androiddevchallenge.ui.utils.LocalSysUiController
import com.example.androiddevchallenge.ui.utils.SystemUiController
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import net.kwmt27.chart.Chart

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            CompositionLocalProvider(LocalSysUiController provides systemUiController) {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    ProvideWindowInsets {
        DevChallengeScaffold {
            Box(
                modifier = Modifier
                    .fillMaxHeight()

            ) {
                DrawableResImage(
                    modifier = Modifier.fillMaxSize(),
                    drawableRes = R.drawable.sunrise,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                ) {
                    HomeTitle(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                        text = "San Francisco, CA (U.S.A)", before = 32.dp
                    )

                    CircleCurrentTemperatureView()

                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .height(180.dp)
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
}

@Composable
private fun CircleCurrentTemperatureView() {
    BoxWithConstraints(
        modifier = Modifier.padding(
            start = 16.dp,
            top = 8.dp,
            end = 16.dp,
            bottom = 8.dp
        )
    ) {
        val boxWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
        Box(
            modifier = Modifier
                .size(boxWidth)
                .background(
                    color = GrayAlpha,
                    shape = RoundedCornerShape(CornerSize(boxWidth / 2))
                )
        ) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Text(
                            "41",
                            fontSize = 36.sp,
                            color = DevChallengeTheme.colors.surface,
                        )
                        Text(
                            "°",
                            fontSize = 30.sp,
                            color = DevChallengeTheme.colors.surface,
                        )
                        Text(
                            "/",
                            fontSize = 36.sp,
                            color = DevChallengeTheme.colors.surface,
                        )
                        Text(
                            "51",
                            fontSize = 36.sp,
                            color = DevChallengeTheme.colors.surface,
                        )
                        Text(
                            "°",
                            fontSize = 30.sp,
                            color = DevChallengeTheme.colors.surface,
                        )
                    }
                    Row {
                        Text(
                            "41",
                            fontSize = 80.sp,
                            color = DevChallengeTheme.colors.surface,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "°",
                            fontSize = 40.sp,
                            color = DevChallengeTheme.colors.surface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        "2021/03/24",
                        fontSize = 24.sp,
                        color = DevChallengeTheme.colors.surface,
                    )
                    Text(
                        "6:23",
                        fontSize = 24.sp,
                        color = DevChallengeTheme.colors.surface,
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
    before: Dp
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .paddingFrom(alignmentLine = LastBaseline, before = before),
            text = text,
            style = DevChallengeTheme.typography.h1,
            color = DevChallengeTheme.colors.textH1
        )
    }
}
