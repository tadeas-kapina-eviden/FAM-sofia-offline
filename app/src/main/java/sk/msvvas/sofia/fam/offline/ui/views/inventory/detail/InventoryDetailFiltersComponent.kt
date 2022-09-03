package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InventoryDetailFiltersComponent(
    inventoryDetailViewModel: InventoryDetailViewModel
) {
    val localityFilter by inventoryDetailViewModel.localityFilter.observeAsState("")
    val roomFilter by inventoryDetailViewModel.roomFilter.observeAsState("")
    val userFilter by inventoryDetailViewModel.userFilter.observeAsState("")
    val scanWithoutDetail by inventoryDetailViewModel.scanWithoutDetail.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        InputRow(
            label = "Lokalita:",
            value = localityFilter,
            onClick = { inventoryDetailViewModel.showLocationCodebookSelectionView() })
        InputRow(
            label = "Miestnosť:",
            value = roomFilter,
            onClick = { inventoryDetailViewModel.showRoomCodebookSelectionView() })
        InputRow(
            label = "Osoba:",
            value = userFilter,
            onClick = { inventoryDetailViewModel.showUserCodebookSelectionView() })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(
                text = "Sken bez detailu položky:",
                modifier = Modifier
                    .weight(2f)
                    .padding(15.dp),
                textAlign = TextAlign.End
            )
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Checkbox(
                    checked = scanWithoutDetail,
                    onCheckedChange = { inventoryDetailViewModel.onScanWithoutDetailButtonClick() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Button(
                onClick = { inventoryDetailViewModel.runFilters() },
            ) {
                Text(text = "Spustiť filtre")
            }
        }
    }
}

@Composable
private fun InputRow(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier
                .weight(2f)
                .padding(15.dp),
            textAlign = TextAlign.End
        )
        TextField(
            value = value,
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .weight(3f)
                .clickable {
                    onClick()
                }
        )
    }
}