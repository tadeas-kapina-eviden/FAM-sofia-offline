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
import java.time.LocalDate

@Composable
fun InventoryListItem(
    id: String,
    note: String,
    createdAt: LocalDate,
    createdBy: String,
    counts: String
) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .border(width = 1.dp, color = MaterialTheme.colors.primary)
            .fillMaxWidth()
    ) {
        Text(
            text = id,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        Text(
            text = note
        )
        Text(
            text = "Dátum:\t$createdAt"
        )
        Text(
            text = "Založené:\t$createdBy"
        )
        Text(
            text = "Spracované/Celkom\t$counts"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryListItemPreview() {
    InventoryListItem(
        id = "350",
        note = "UCJ",
        createdAt = LocalDate.now(),
        createdBy = "110SEVCOVA",
        counts = "44/44"
    )
}