package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
            onValueChange = { inventoryDetailViewModel.onLocalityFilterChange(it) })
        InputRow(
            label = "Miestnosť:",
            value = roomFilter,
            onValueChange = { inventoryDetailViewModel.onRoomFilterChange(it) })
        InputRow(
            label = "Osoba:",
            value = userFilter,
            onValueChange = { inventoryDetailViewModel.onUserFilterChange(it) })
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
            Row(
                modifier = Modifier
                    .weight(3f)
            ) {
                Button(
                    onClick = { inventoryDetailViewModel.onScanWithoutDetailButtonClick() },
                ) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "dropdown icon",
                        modifier = Modifier
                            .weight(1f)
                            /*TODO change icon*/
                            .rotate(
                                if (scanWithoutDetail) 180f else 0f
                            )
                    )
                }
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
fun InputRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
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
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .weight(3f)
        )
    }
}