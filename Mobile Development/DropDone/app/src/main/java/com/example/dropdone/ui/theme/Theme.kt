package com.example.dropdone.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = Dark,
    surfaceTint = LightGray,
    onSurface = LightGray,
    surface = Dark
)

private val LightColorPalette = lightColorScheme(
    primary = LightGray,
    surfaceTint = Dark,
    onSurface = Dark,
    surface = LightGray
)

@Composable
fun DropDoneTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}