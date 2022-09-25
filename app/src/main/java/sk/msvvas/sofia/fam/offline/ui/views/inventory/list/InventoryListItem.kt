package sk.msvvas.sofia.fam.offline.ui.views.inventory.list

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.ui.theme.FAMInventuraOfflineClientTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun InventoryListItem(
    inventory: InventoryEntity,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = inventory.id,
                modifier = Modifier
                    .padding(bottom = 5.dp),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary,
            )
            Text(
                text = inventory.note,
                modifier = Modifier
                    .padding(bottom = 15.dp),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = "Dátum:\t" + inventory.createdAt,
                modifier = Modifier
                    .padding(bottom = 5.dp),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = "Založené:\t" + inventory.createdBy,
                modifier = Modifier
                    .padding(bottom = 5.dp),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = "Spracované/Celkom: \t" + inventory.countProcessed + "/" + inventory.countAll,
                modifier = Modifier
                    .padding(bottom = 5.dp),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryListItemPreview() {
    FAMInventuraOfflineClientTheme {
        InventoryListItem(
            InventoryEntity(
                id = "350",
                note = "UCJ",
                createdAt = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                createdBy = "110SEVCOVA",
                countProcessed = 44,
                countAll = 44
            ),
            onClick = {

            }
        )
    }
}