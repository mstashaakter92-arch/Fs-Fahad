package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = Color(0xFF001B2E),
    primaryContainer = Color(0xFF00385C),
    onPrimaryContainer = Color(0xFFC7E7FF),

    secondary = RoyalGold,
    onSecondary = Color(0xFF281800),
    secondaryContainer = Color(0xFF4A3400),
    onSecondaryContainer = Color(0xFFFFDF9E),

    tertiary = NeonCyan,
    onTertiary = Color(0xFF003831),

    background = BgDark,
    onBackground = TextWhite,

    surface = SurfaceDark,
    onSurface = TextWhite,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextMuted,

    error = StatusFailed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Always esports dark aesthetic
    dynamicColor: Boolean = false, // Keep branded esports identity
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
