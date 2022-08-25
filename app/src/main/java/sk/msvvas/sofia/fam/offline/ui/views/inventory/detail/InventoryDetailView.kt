package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.model.PropertyPreviewModel
import sk.msvvas.sofia.fam.offline.ui.views.property.PropertyListView

@Composable
fun InventoryDetailView(
    inventoryDetailViewModel: InventoryDetailViewModel
) {

    val properties by inventoryDetailViewModel.filteredProperties.observeAsState(emptyList())
    val isFiltersShow by inventoryDetailViewModel.isFiltersShown.observeAsState(false)
    val inventoryId by inventoryDetailViewModel.inventoryId.observeAsState("")
    val codeFilter by inventoryDetailViewModel.codeFilter.observeAsState("")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = codeFilter,
                onValueChange = {
                    inventoryDetailViewModel.onCodeFilterChange(it)
                },
                modifier = Modifier
                    .weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        inventoryDetailViewModel.runCodeFilter()
                    }
                )
            )
            Button(
                onClick = { inventoryDetailViewModel.onFiltersShowClick() },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Zobraziť filtre"
                )
            }
        }
        if (isFiltersShow) {
            InventoryDetailFiltersComponent(inventoryDetailViewModel)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary
                )
        ) {
            Text(
                text = "Inv. $inventoryId",
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 1.dp)
            )
        }
        PropertyListView(properties = propertyEntityListToPropertyPreviewList(properties))
    }
    inventoryDetailViewModel.filterOutValues()
}

@Preview(showBackground = true)
@Composable
fun InventoryDetailViewPreview() {
//TODO add preview
//InventoryDetailView()
}

fun propertyEntityListToPropertyPreviewList(propertyEntities: List<PropertyEntity>): List<PropertyPreviewModel> {
    return propertyEntities.map {
        PropertyPreviewModel(
            textMainNumber = it.textMainNumber,
            status = it.recordStatus,
            subNumber = it.subnumber,
            propertyNumber = it.propertyNumber
        )
    }
}