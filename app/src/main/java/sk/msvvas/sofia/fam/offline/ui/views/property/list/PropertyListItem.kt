package sk.msvvas.sofia.fam.offline.ui.views.property.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.model.PropertyPreviewModel
import sk.msvvas.sofia.fam.offline.ui.components.drawWithBottomLine

@Composable
fun PropertyListItem(
    property: PropertyPreviewModel,
    onClick: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawWithBottomLine(
                width = 1f,
                color = MaterialTheme.colors.primary
            )
            .clickable {
                onClick(property.id)
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
            id = 10,
            textMainNumber = "PROGRAM 4 JS VIRTUAL DYN. MACH",
            propertyNumber = "22005779",
            subNumber = "45",
            status = 'S'
        ),
        onClick = {

        }
    )
}