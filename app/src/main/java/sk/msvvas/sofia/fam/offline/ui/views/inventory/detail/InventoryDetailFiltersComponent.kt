package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.ui.components.InputRow
import sk.msvvas.sofia.fam.offline.ui.components.StyledTextButton

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
        InputRowStyled(
            label = "Lokalita:",
            value = localityFilter,
            onClick = { inventoryDetailViewModel.showLocationCodebookSelectionView() })
        InputRowStyled(
            label = "Miestnosť:",
            value = roomFilter,
            onClick = { inventoryDetailViewModel.showRoomCodebookSelectionView() })
        InputRowStyled(
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
                style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.End),
                color = MaterialTheme.colors.primary
            )
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Checkbox(
                    checked = scanWithoutDetail,
                    onCheckedChange = { inventoryDetailViewModel.onScanWithoutDetailButtonClick() },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colors.primary,
                        uncheckedColor = MaterialTheme.colors.primary
                    )
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            StyledTextButton(
                onClick = { inventoryDetailViewModel.runFilters() },
                text = "Spustiť filtre"
            )
        }
    }
}

@Composable
private fun InputRowStyled(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    InputRow(
        label = label,
        value = value,
        modifier = Modifier
            .padding(vertical = 5.dp),
        ratio = (2f / 3f),
        labelTextAlign = TextAlign.End,
        labelTextHorizontalPadding = 15.dp,
        onClick = onClick
    )
}