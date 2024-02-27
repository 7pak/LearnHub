package com.example.courseapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = DarkBlue2,
    primaryVariant = LightBlue,
    secondary = Teal200,
    background = DarkBlue,
    onBackground = Color.White,
)

private val LightColorPalette = lightColors(

    primary = DarkPurple1,
    primaryVariant = LightPurple,
    background = Color.White,
    onBackground = DarkPurple2
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun CourseAppTheme(darkTheme: Boolean = isSystemInDarkTheme(),content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette


    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (darkTheme) DarkBlue else LightPurple
        )
        systemUiController.setNavigationBarColor(color = if (darkTheme)DarkBlue else LightPurple )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}