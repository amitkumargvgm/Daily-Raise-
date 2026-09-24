package com.example.ui.theme

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
    primary = Gold400,
    onPrimary = Slate950,
    primaryContainer = Gold700,
    onPrimaryContainer = Gold50,
    secondary = Indigo400,
    onSecondary = Slate950,
    secondaryContainer = Indigo800,
    onSecondaryContainer = Indigo200,
    tertiary = Rose500,
    onTertiary = Color.White,
    background = Color(0xFF0B0F19),
    surface = Color(0xFF111827),
    surfaceVariant = Color(0xFF1F2937),
    onBackground = Color(0xFFF9FAFB),
    onSurface = Color(0xFFF9FAFB),
    onSurfaceVariant = Color(0xFF9CA3AF),
    outline = Color(0xFF4B5563),
    outlineVariant = Color(0xFF374151)
)

private val LightColorScheme = lightColorScheme(
    primary = Gold600,
    onPrimary = Color.White,
    primaryContainer = Gold100,
    onPrimaryContainer = Gold700,
    secondary = Indigo600,
    onSecondary = Color.White,
    secondaryContainer = Indigo200,
    onSecondaryContainer = Indigo900,
    tertiary = Rose600,
    onTertiary = Color.White,
    background = Color(0xFFF8FAFC),
    surface = Color.White,
    surfaceVariant = Color(0xFFF1F5F9),
    onBackground = Slate900,
    onSurface = Slate900,
    onSurfaceVariant = Slate700,
    outline = Slate400,
    outlineVariant = Slate200
)

@Composable
fun MotivationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set false to preserve our bespoke gold & indigo branding
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
