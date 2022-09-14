package sk.msvvas.sofia.fam.offline.ui.views.property.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.application.model.PropertyPreviewModel
import sk.msvvas.sofia.fam.offline.ui.components.drawWithBottomLine


@Composable
fun PropertyListView(
    properties: List<PropertyPreviewModel>,
    changeView: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithBottomLine(
                    width = 1f,
                    color = MaterialTheme.colors.primary
                )
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
        if (properties.isNotEmpty()) {
            properties
                .subList(
                    0,
                    if (properties.size < 100) properties.size else 100
                )
                .forEach {
                    PropertyListItem(property = it, onClick = { id -> changeView(id) })
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PropertyListViewPreview() {
    PropertyListView(
        properties = listOf(
            PropertyPreviewModel(
                id = 5L,
                textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
                propertyNumber = "22005779",
                subNumber = "22005779",
                status = 'S'
            ),
            PropertyPreviewModel(
                id = 1L,
                textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
                propertyNumber = "22005779",
                subNumber = "22005779",
                status = 'S'
            ),
            PropertyPreviewModel(
                id = 3L,
                textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
                propertyNumber = "22005779",
                subNumber = "22005779",
                status = 'S'
            ),
            PropertyPreviewModel(
                id = 2L,
                textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
                propertyNumber = "22005779",
                subNumber = "22005779",
                status = 'S'
            ),
            PropertyPreviewModel(
                id = 6L,
                textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
                propertyNumber = "22005779",
                subNumber = "22005779",
                status = 'S'
            )
        ),
        changeView = {}
    )
}