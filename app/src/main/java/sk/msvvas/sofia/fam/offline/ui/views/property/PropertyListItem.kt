package sk.msvvas.sofia.fam.offline.ui.views.property

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.model.PropertyPreviewModel

@Composable
fun PropertyListItem(
    property: PropertyPreviewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = property.textMainNumber,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = property.propertyNumber + "/" + property.subNumber,
                modifier = Modifier.padding(5.dp)
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
            subNumber = "22005779",
            status = 'S'
        )
    )
}