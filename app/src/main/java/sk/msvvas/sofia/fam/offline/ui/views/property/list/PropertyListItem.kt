package sk.msvvas.sofia.fam.offline.ui.views.property.list

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.application.model.PropertyPreviewModel
import sk.msvvas.sofia.fam.offline.ui.theme.FAMInventuraOfflineClientTheme

/**
 * Component show basic information of property in inventory detail
 * @param property property information to show
 * @param onClick Function that executed when row is clicked
 */
@Composable
fun PropertyListItem(
    property: PropertyPreviewModel,
    onClick: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                shape = RoundedCornerShape(8.dp),
                width = 1.dp,
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
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)
            )
            Text(
                text = property.propertyNumber + "/" + property.subNumber,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)
            )
        }
        Text(
            text = when (property.status) {
                'X' -> "Nespracovaný"
                'Z' -> "Zmenený"
                'C' -> "Chýbajúci"
                'S' -> "Spracovaný"
                'N' -> "Nový"
                else -> {
                    ""
                }
            },
            modifier = Modifier
                .weight(1f)
                .padding(5.dp),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PropertyListItemPreview() {
    FAMInventuraOfflineClientTheme {
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
}