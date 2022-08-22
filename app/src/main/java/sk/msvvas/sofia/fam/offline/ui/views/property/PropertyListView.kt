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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.model.InventoryModel
import sk.msvvas.sofia.fam.offline.data.model.PropertyPreviewModel
import java.time.LocalDate


@Composable
fun PropertyListView(
    properties: List<PropertyPreviewModel>
) {
    val underLineColor = MaterialTheme.colors.primary
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawContent()
                    clipRect {
                        val strokeWidth = Stroke.DefaultMiter
                        val y = size.height
                        drawLine(
                            brush = SolidColor(underLineColor),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Square,
                            start = Offset.Zero.copy(y = y),
                            end = Offset(x = size.width, y = y)
                        )
                    }
                }
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            Text(
                text = "Názov",
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Status",
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
            )
        }
        properties.forEach {
            PropertyListItem(property = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PropertyListViewPreview() {
    PropertyListView(
        properties = listOf(
            PropertyPreviewModel(
                textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
                propertyNumber = "22005779",
                subNumber = "22005779",
                status = 'S'
            ),
            PropertyPreviewModel(
                textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
                propertyNumber = "22005779",
                subNumber = "22005779",
                status = 'S'
            ),
            PropertyPreviewModel(
                textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
                propertyNumber = "22005779",
                subNumber = "22005779",
                status = 'S'
            ),
            PropertyPreviewModel(
                textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
                propertyNumber = "22005779",
                subNumber = "22005779",
                status = 'S'
            ),
            PropertyPreviewModel(
                textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
                propertyNumber = "22005779",
                subNumber = "22005779",
                status = 'S'
            )
        )
    )
}