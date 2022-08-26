package sk.msvvas.sofia.fam.offline.ui.views.property.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity


//TODO add other fields
@Composable
fun PropertyDetailView(
    propertyDetailViewModel: PropertyDetailViewModel
) {

    val property by propertyDetailViewModel.property.observeAsState(
        PropertyEntity()
    )

    val underLineColor = MaterialTheme.colors.primary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {
        TextField(
            value = "Podrobnosti: " + property.propertyNumber + "/" + property.subnumber,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        TextField(
            value = property.textMainNumber,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
        )
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
        ) {
            Text(
                text = "Sériové číslo",
                modifier = Modifier.weight(2f)
            )
            TextField(
                value = property.serialNumber,
                readOnly = true,
                onValueChange = {}
            )
        }

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
        ) {
            Text(
                text = "Invent číslo",
                modifier = Modifier.weight(2f)
            )
            TextField(
                value = property.inventNumber,
                readOnly = true,
                onValueChange = {}
            )
        }
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
        ) {
            Text(
                text = "Závod",
                modifier = Modifier.weight(2f)
            )
            TextField(
                value = property.werks,
                readOnly = true,
                onValueChange = {}
            )
        }

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
        ) {
            Text(
                text = "Lokalita",
                modifier = Modifier.weight(2f)
            )
            TextField(
                value = property.werks,
                readOnly = true,
                onValueChange = {}
            )
        }


    }
}