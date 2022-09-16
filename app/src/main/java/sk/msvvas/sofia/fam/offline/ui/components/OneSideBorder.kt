package sk.msvvas.sofia.fam.offline.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.clipRect

/**
 * Modifier used to draw component with border only at bottom side
 * @param width width of border
 * @param color color of border
 */
fun Modifier.drawWithBottomLine(
    width: Float = 0f,
    color: Color = Color.Black
) = this.then(
    Modifier.drawWithContent {
        drawContent()
        clipRect {
            val y = size.height
            drawLine(
                brush = SolidColor(color),
                strokeWidth = width,
                cap = StrokeCap.Square,
                start = Offset.Zero.copy(y = y),
                end = Offset(x = size.width, y = y)
            )
        }
    }
)

/**
 * Modifier used to draw component with border only at top side
 * @param width width of border
 * @param color color of border
 */
fun Modifier.drawWithTopLine(
    width: Float = 0f,
    color: Color = Color.Black
) = this.then(
    Modifier.drawWithContent {
        drawContent()
        clipRect {
            drawLine(
                brush = SolidColor(color),
                strokeWidth = width,
                cap = StrokeCap.Square,
                start = Offset.Zero,
                end = Offset.Zero.copy(x = size.width)
            )
        }
    }
)