package sk.msvvas.sofia.fam.offline.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.White,
    primaryVariant = Color.LightGray,
    secondary = Teal200,
    background = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF4374EF),
    primaryVariant = Color(0xFFBBCAD2),
    secondary = Color.White,
    onSurface = Color(0xBB222222),
    surface = Color(0xfffafafa)

    /* Other default colors to override
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

/**
 * Basic styles in application
 */
@Composable
fun FAMInventuraOfflineClientTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}