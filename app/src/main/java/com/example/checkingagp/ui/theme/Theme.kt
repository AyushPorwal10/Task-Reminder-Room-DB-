package com.example.checkingagp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = Color.Black, // this is for bg
    onBackground = Color.White,  // this is for items above bg like text  , icons
    primary = Purple80, // this is for top app bar , buttons and main
    secondary = Color.Red, // for floating actions btn , switches
    onPrimary = Color.White,
    surface = Color.Blue,
    onSecondary = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    background = Color.White,
    onBackground = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    surface = Color.Blue,
    secondary = Color.Red,
)

@Composable
fun CheckingAGPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}