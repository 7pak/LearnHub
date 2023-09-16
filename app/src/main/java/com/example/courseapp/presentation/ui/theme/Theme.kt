package com.example.courseapp.presentation.ui.theme

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
    onBackground = Color.White
)

private val LightColorPalette = lightColors(


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
fun CourseAppTheme(/*darkTheme: Boolean = isSystemInDarkTheme(),*/ content: @Composable () -> Unit) {
    val colors = DarkColorPalette


    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color =  DarkBlue
        )
        systemUiController.setNavigationBarColor(color =DarkBlue )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}