package sk.msvvas.sofia.fam.offline.ui.views.inventory.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.model.InventoryModel
import java.time.LocalDate

@Composable
fun InventoryListView(
    inventoryListViewModel: InventoryListViewModel
) {

    val inventories by inventoryListViewModel.inventories.observeAsState(emptyList())

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
        InventoryModel(
            id = "350",
            note = "UCJ",
            createdAt = LocalDate.now(),
            createdBy = "110SEVECOVA",
            countProcessed = 40,
            countAll = 44
        ),
        InventoryModel(
            id = "1520",
            note = "FRI CIT 105930 - skúšobná",
            createdAt = LocalDate.of(2018, 9, 30),
            createdBy = "110MICIANOV1",
            countProcessed = 0,
            countAll = 0
        ),
        InventoryModel(
            id = "992",
            note = "NM 103110 KF",
            createdAt = LocalDate.of(2019, 12, 31),
            createdBy = "110KUBALOVA",
            countProcessed = 120,
            countAll = 150
        )
    )

    //TODO add preview
    //InventoryListView(inventories = inventories)
}