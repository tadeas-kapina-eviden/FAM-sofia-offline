package sk.msvvas.sofia.fam.offline.ui.views.inventory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.model.Inventory
import java.time.LocalDate

@Composable
fun InventoryListView(
    inventories: List<Inventory>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Vyber Inventúru",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        )
        inventories.forEach {
            InventoryListItem(inventory = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryListViewPreview() {
    val inventories = listOf(
        Inventory(
            id = "350",
            note = "UCJ",
            createdAt = LocalDate.now(),
            createdBy = "110SEVECOVA",
            countProcessed = 40,
            countAll = 44
        ),
        Inventory(
            id = "1520",
            note = "FRI CIT 105930 - skúšobná",
            createdAt = LocalDate.of(2018, 9, 30),
            createdBy = "110MICIANOV1",
            countProcessed = 0,
            countAll = 0
        ),
        Inventory(
            id = "992",
            note = "NM 103110 KF",
            createdAt = LocalDate.of(2019, 12, 31),
            createdBy = "110KUBALOVA",
            countProcessed = 120,
            countAll = 150
        )
    )

    InventoryListView(inventories = inventories)
}