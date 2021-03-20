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
package com.example.androiddevchallenge.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.ui.utils.LocalSysUiController

// private val LightColors = lightColors(
//    primary = Pink100,
//    secondary = Pink900,
//    background = White,
//    surface = White850,
//    onPrimary = Gray,
//    onSecondary = White,
//    onBackground = Gray,
//    onSurface = Gray
// )
// private val DarkColors = darkColors(
//    primary = Green900,
//    secondary = Green300,
//    background = Gray,
//    surface = White150,
//    onPrimary = White,
//    onSecondary = Gray,
//    onBackground = White,
//    onSurface = White850
// )
private val LightColorPalette = DevChallengeColors(
    primary = Pink100,
    secondary = Pink900,
    background = White,
    surface = White850,
    onPrimary = Gray,
    onSecondary = White,
    onBackground = Gray,
    onSurface = Gray,
    textButton1 = White,
    textButton2 = Pink900,
    textH1 = Gray,
    textH2 = Gray,
    textSubtitle1 = Gray,
    textBody1 = Gray,
    textBody2 = Gray,
    textCaption = Gray,
    isDark = false
)
private val DarkColorPalette = DevChallengeColors(
    primary = Green900,
    secondary = Green300,
    background = Gray,
    surface = White150,
    onPrimary = White,
    onSecondary = Gray,
    onBackground = White,
    onSurface = White850,
    textButton1 = Gray,
    textButton2 = White,
    textH1 = White,
    textH2 = White,
    textSubtitle1 = White,
    textBody1 = White,
    textBody2 = White,
    textCaption = White,
    isDark = false
)

@Composable
fun surfaceColorPrimary(): Color {
    return DevChallengeTheme.colors.primary
}
@Composable
fun surfaceColorBackground(): Color {
    return DevChallengeTheme.colors.background
}

@Composable
fun DevChallengeScaffold(
    darkTheme: Boolean = isSystemInDarkTheme(),
    surfaceColor: @Composable () -> Color = { surfaceColorPrimary() },
    bottomBar: @Composable () -> Unit = {},
    content: @Composable() () -> Unit
) {
    MyTheme(darkTheme) {
        Scaffold(backgroundColor = surfaceColor(), bottomBar = bottomBar) {
            content()
        }
    }
}

@Composable
fun MyTheme(darkTheme: Boolean, content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val sysUiController = LocalSysUiController.current
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.background // .copy(alpha = AlphaNearOpaque)
        )
    }
    ProvideDevChallengeColors(colors) {
        MaterialTheme(
            colors = debugColors(darkTheme),
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

object DevChallengeTheme {
    val colors: DevChallengeColors
        @Composable
        get() = LocalDevChallengeColors.current
    val typography: Typography
        @Composable
        get() = MaterialTheme.typography
}

@Stable
class DevChallengeColors(
    primary: Color,
    secondary: Color,
    background: Color,
    surface: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    textButton1: Color, // 角丸ボタンの上のテキストカラー
    textButton2: Color, // 枠なしボタンのテキストカラー
    textH1: Color,
    textH2: Color,
    textSubtitle1: Color,
    textBody1: Color,
    textBody2: Color,
    textCaption: Color,
    isDark: Boolean
) {
    var primary by mutableStateOf(primary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var background by mutableStateOf(background)
        private set
    var surface by mutableStateOf(surface)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var onSurface by mutableStateOf(onSurface)
        private set
    var textButton1 by mutableStateOf(textButton1)
        private set
    var textButton2 by mutableStateOf(textButton2)
        private set
    var textH1 by mutableStateOf(textH1)
        private set
    var textH2 by mutableStateOf(textH2)
        private set
    var textSubtitle1 by mutableStateOf(textSubtitle1)
        private set
    var textBody1 by mutableStateOf(textBody1)
        private set
    var textBody2 by mutableStateOf(textBody2)
        private set
    var textCaption by mutableStateOf(textCaption)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: DevChallengeColors) {
        primary = other.primary
        secondary = other.secondary
        background = other.background
        surface = other.surface
        onPrimary = other.onPrimary
        onSecondary = other.onSecondary
        onBackground = other.onBackground
        onSurface = other.onSurface
        textButton1 = other.textButton1
        textButton2 = other.textButton2
        textH1 = other.textH1
        textH2 = other.textH2
        textSubtitle1 = other.textSubtitle1
        textBody1 = other.textBody1
        textBody2 = other.textBody2
        textCaption = other.textCaption
        isDark = other.isDark
    }
}

@Composable
fun ProvideDevChallengeColors(
    colors: DevChallengeColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalDevChallengeColors provides colorPalette, content = content)
}

private val LocalDevChallengeColors = staticCompositionLocalOf<DevChallengeColors> {
    error("No JetsnackColorPalette provided")
}

/**
 * A Material [Colors] implementation which sets all colors to [debugColor] to discourage usage of
 * [MaterialTheme.colors] in preference to [DevChallengeTheme.colors].
 */
fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Magenta
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)
