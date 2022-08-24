package sk.msvvas.sofia.fam.offline.ui.views.inventory

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import sk.msvvas.sofia.fam.offline.data.model.InventoryModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun InventoryListItem(
    inventory: InventoryModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = inventory.id,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 5.dp)
            )
            Text(
                text = inventory.note,
                modifier = Modifier
                    .padding(bottom = 15.dp)
            )
            Text(
                text = "Dátum:\t" + inventory.createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                modifier = Modifier
                    .padding(bottom = 5.dp)

            )
            Text(
                text = "Založené:\t" + inventory.createdBy,
                modifier = Modifier
                    .padding(bottom = 5.dp)
            )
            Text(
                text = "Spracované/Celkom: \t" + inventory.countProcessed + "/" + inventory.countAll,
                modifier = Modifier
                    .padding(bottom = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryListItemPreview() {
    InventoryListItem(
        InventoryModel(
            id = "350",
            note = "UCJ",
            createdAt = LocalDate.now(),
            createdBy = "110SEVCOVA",
            countProcessed = 44,
            countAll = 44
        )
    )
}