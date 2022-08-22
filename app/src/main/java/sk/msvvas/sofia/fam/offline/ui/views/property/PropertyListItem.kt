package sk.msvvas.sofia.fam.offline.ui.views.property

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.model.PropertyPreviewModel

@Composable
fun PropertyListItem(
    property: PropertyPreviewModel
) {
    val color = MaterialTheme.colors.primary
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawWithContent {
                drawContent()
                clipRect {
                    val strokeWidth = Stroke.DefaultMiter
                    val y = size.height
                    drawLine(
                        brush = SolidColor(color),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Square,
                        start = Offset.Zero.copy(y = y),
                        end = Offset(x = size.width, y = y)
                    )
                }
            }
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = property.textMainNumber,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)
            )
            Text(
                text = property.propertyNumber + "/" + property.subNumber,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)
            )
        }
        Text(
            text = "Spracované",
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PropertyListItemPreview() {
    PropertyListItem(
        property = PropertyPreviewModel(
            textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
            propertyNumber = "22005779",
            subNumber = "45",
            status = 'S'
        )
    )
}