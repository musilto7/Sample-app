package cz.musilto5.myflickerapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = ColorPalette.Primary.main,
    onPrimary = ColorPalette.Primary.onMain,
    primaryContainer = ColorPalette.Primary.container,
    onPrimaryContainer = ColorPalette.Primary.onContainer,
    secondary = ColorPalette.Secondary.main,
    onSecondary = ColorPalette.Secondary.onMain,
    secondaryContainer = ColorPalette.Secondary.container,
    onSecondaryContainer = ColorPalette.Secondary.onContainer,
    tertiary = ColorPalette.Tertiary.main,
    onTertiary = ColorPalette.Tertiary.onMain,
    tertiaryContainer = ColorPalette.Tertiary.container,
    onTertiaryContainer = ColorPalette.Tertiary.onContainer,
    error = ColorPalette.Error.main,
    onError = ColorPalette.Error.onMain,
    errorContainer = ColorPalette.Error.container,
    onErrorContainer = ColorPalette.Error.onContainer,
    background = ColorPalette.Neutral.background,
    onBackground = ColorPalette.Neutral.onBackground,
    surface = ColorPalette.Neutral.surface,
    onSurface = ColorPalette.Neutral.onSurface,
    surfaceVariant = ColorPalette.Neutral.surfaceVariant,
    onSurfaceVariant = ColorPalette.Neutral.onSurfaceVariant,
    outline = ColorPalette.Neutral.outline,
    outlineVariant = ColorPalette.Neutral.outlineVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = ColorPalette.Primary.light,
    onPrimary = ColorPalette.Primary.onMainDark,
    primaryContainer = ColorPalette.Primary.containerDark,
    onPrimaryContainer = ColorPalette.Primary.onContainerDark,
    secondary = ColorPalette.Secondary.light,
    onSecondary = ColorPalette.Secondary.onMainDark,
    secondaryContainer = ColorPalette.Secondary.containerDark,
    onSecondaryContainer = ColorPalette.Secondary.onContainerDark,
    tertiary = ColorPalette.Tertiary.light,
    onTertiary = ColorPalette.Tertiary.onMainDark,
    tertiaryContainer = ColorPalette.Tertiary.containerDark,
    onTertiaryContainer = ColorPalette.Tertiary.onContainerDark,
    error = ColorPalette.Error.light,
    onError = ColorPalette.Error.onMainDark,
    errorContainer = ColorPalette.Error.containerDark,
    onErrorContainer = ColorPalette.Error.onContainerDark,
    background = ColorPalette.Neutral.backgroundDark,
    onBackground = ColorPalette.Neutral.onBackgroundDark,
    surface = ColorPalette.Neutral.surfaceDark,
    onSurface = ColorPalette.Neutral.onSurfaceDark,
    surfaceVariant = ColorPalette.Neutral.surfaceVariantDark,
    onSurfaceVariant = ColorPalette.Neutral.onSurfaceVariantDark,
    outline = ColorPalette.Neutral.outlineDark,
    outlineVariant = ColorPalette.Neutral.outlineVariantDark
)

@Composable
fun MyFlickerApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
