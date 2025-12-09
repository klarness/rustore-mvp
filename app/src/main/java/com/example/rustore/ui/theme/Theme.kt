package com.example.rustore.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Color.White,
    primaryContainer = PurpleGrey80,
    onPrimaryContainer = Color(0xFFE8F5E9),

    secondary = Pink80,
    onSecondary = Color.White,
    secondaryContainer = PurpleGrey80,
    onSecondaryContainer = Color(0xFFE8F5E9),

    background = Color(0xFF00140A),
    onBackground = Color(0xFFE1F5E9),

    surface = Color(0xFF00140A),
    onSurface = Color(0xFFE1F5E9),

    surfaceVariant = Color(0xFF14301B),
    onSurfaceVariant = Color(0xFFC8E6C9)
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFA5D6A7),
    onPrimaryContainer = Color(0xFF003314),

    secondary = PurpleGrey40,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFC5E1A5),
    onSecondaryContainer = Color(0xFF203000),

    tertiary = Pink40,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFC8E6C9),
    onTertiaryContainer = Color(0xFF003314),

    background = Color(0xFFF1F8E9),
    onBackground = Color(0xFF102A12),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF102A12),

    surfaceVariant = Color(0xFFE0F2F1),
    onSurfaceVariant = Color(0xFF255D2D)
)

@Composable
fun RuStoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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
