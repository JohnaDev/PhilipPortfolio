package com.juan.lazy.philipportfolio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.juan.lazy.philipportfolio.model.AppTheme

data class PortfolioColors(
    val background: Color,
    val backgroundGradientEnd: Color,
    val cardBackground: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val accentPrimary: Color,
    val accentSecondary: Color,
    val accentTertiary: Color
)

private val LocalPortfolioColors = staticCompositionLocalOf {
    PortfolioColors(
        background = Color.Unspecified,
        backgroundGradientEnd = Color.Unspecified,
        cardBackground = Color.Unspecified,
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        accentPrimary = Color.Unspecified,
        accentSecondary = Color.Unspecified,
        accentTertiary = Color.Unspecified
    )
}

object PortfolioTheme {
    val colors: PortfolioColors
        @Composable
        @ReadOnlyComposable
        get() = LocalPortfolioColors.current
}

private val DarkPortfolioColors = PortfolioColors(
    background = DarkBackground,
    backgroundGradientEnd = Color.Black.copy(alpha = 0.8f),
    cardBackground = CardBackground,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    accentPrimary = AccentPrimary,
    accentSecondary = AccentSecondary,
    accentTertiary = AccentTertiary
)

private val LightPortfolioColors = PortfolioColors(
    background = LightBackground,
    backgroundGradientEnd = Color(0xFFF1F5F9), // Slate 100
    cardBackground = LightCardBackground,
    textPrimary = LightTextPrimary,
    textSecondary = LightTextSecondary,
    accentPrimary = LightAccentPrimary,
    accentSecondary = LightAccentSecondary,
    accentTertiary = LightAccentTertiary
)

@Composable
fun PhilipPortfolioTheme(
    appTheme: AppTheme = AppTheme.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (appTheme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
        AppTheme.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = AccentPrimary,
            secondary = AccentSecondary,
            tertiary = AccentTertiary,
            background = DarkBackground,
            surface = CardBackground
        )
    } else {
        lightColorScheme(
            primary = LightAccentPrimary,
            secondary = LightAccentSecondary,
            tertiary = LightAccentTertiary,
            background = LightBackground,
            surface = LightCardBackground
        )
    }

    val portfolioColors = if (darkTheme) DarkPortfolioColors else LightPortfolioColors

    CompositionLocalProvider(LocalPortfolioColors provides portfolioColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
