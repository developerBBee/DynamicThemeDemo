package jp.developer.bbee.dynamicthemedemo.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.developer.bbee.dynamicthemedemo.ColorType
import jp.developer.bbee.dynamicthemedemo.MainViewModel

@Composable
fun DynamicThemeDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    viewModel: MainViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val defaultScheme = lightColorScheme()

    val colors by viewModel.colorState.collectAsStateWithLifecycle()

    val colorScheme = defaultScheme.run { lightColorScheme(
        primary = colors[ColorType.PRIMARY] ?: primary,
        onPrimary = colors[ColorType.ON_PRIMARY] ?: onPrimary,
        primaryContainer = colors[ColorType.PRIMARY_CONTAINER] ?: primaryContainer,
        onPrimaryContainer = colors[ColorType.ON_PRIMARY_CONTAINER] ?: onPrimaryContainer,
        inversePrimary = colors[ColorType.INVERSE_PRIMARY] ?: inversePrimary,
        secondary = colors[ColorType.SECONDARY] ?: secondary,
        onSecondary = colors[ColorType.ON_SECONDARY] ?: onSecondary,
        secondaryContainer = colors[ColorType.SECONDARY_CONTAINER] ?: secondaryContainer,
        onSecondaryContainer = colors[ColorType.ON_SECONDARY_CONTAINER] ?: onSecondaryContainer,
        tertiary = colors[ColorType.TERTIARY] ?: tertiary,
        onTertiary = colors[ColorType.ON_TERTIARY] ?: onTertiary,
        tertiaryContainer = colors[ColorType.TERTIARY_CONTAINER] ?: tertiaryContainer,
        onTertiaryContainer = colors[ColorType.ON_TERTIARY_CONTAINER] ?: onTertiaryContainer,
        background = colors[ColorType.BACKGROUND] ?: background,
        onBackground = colors[ColorType.ON_BACKGROUND] ?: onBackground,
        surface = colors[ColorType.SURFACE] ?: surface,
        onSurface = colors[ColorType.ON_SURFACE] ?: onSurface,
        surfaceVariant = colors[ColorType.SURFACE_VARIANT] ?: surfaceVariant,
        onSurfaceVariant = colors[ColorType.ON_SURFACE_VARIANT] ?: onSurfaceVariant,
        surfaceTint = colors[ColorType.SURFACE_TINT] ?: surfaceTint,
        inverseSurface = colors[ColorType.INVERSE_SURFACE] ?: inverseSurface,
        inverseOnSurface = colors[ColorType.INVERSE_ON_SURFACE] ?: inverseOnSurface,
        error = colors[ColorType.ERROR] ?: error,
        onError = colors[ColorType.ON_ERROR] ?: onError,
        errorContainer = colors[ColorType.ERROR_CONTAINER] ?: errorContainer,
        onErrorContainer = colors[ColorType.ON_ERROR_CONTAINER] ?: onErrorContainer,
        outline = colors[ColorType.OUTLINE] ?: outline,
        outlineVariant = colors[ColorType.OUTLINE_VARIANT] ?: outlineVariant,
        scrim = colors[ColorType.SCRIM] ?: scrim,
    )}

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}