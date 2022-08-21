package sk.msvvas.sofia.fam.offline.ui.views.inventory

import androidx.compose.foundation.border
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
import sk.msvvas.sofia.fam.offline.data.model.Inventory
import java.time.LocalDate

@Composable
fun InventoryListItem(
    inventory: Inventory
) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .border(width = 1.dp, color = MaterialTheme.colors.primary)
            .fillMaxWidth()
    ) {
        Text(
            text = inventory.id,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        Text(
            text = inventory.note
        )
        Text(
            text = "Dátum:\t" + inventory.createdAt
        )
        Text(
            text = "Založené:\t" + inventory.createdBy
        )
        Text(
            text = "Spracované/Celkom\t" + inventory.countProcessed + "/" + inventory.countAll
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryListItemPreview() {
    InventoryListItem(
        Inventory(
            id = "350",
            note = "UCJ",
            createdAt = LocalDate.now(),
            createdBy = "110SEVCOVA",
            countProcessed = 44,
            countAll = 44
        )
    )
}