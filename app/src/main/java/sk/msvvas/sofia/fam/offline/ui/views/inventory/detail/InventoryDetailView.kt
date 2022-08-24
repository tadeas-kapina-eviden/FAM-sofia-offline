package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.model.PropertyPreviewModel
import sk.msvvas.sofia.fam.offline.ui.views.property.PropertyListView

@Composable
fun InventoryDetailView(
    inventoryDetailViewModel: InventoryDetailViewModel
) {

    val properties by inventoryDetailViewModel.properties.observeAsState(emptyList())


    var filter by remember {
        mutableStateOf("")
    }
    var isFiltersShow by remember {
        mutableStateOf(false)
    }
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
                value = filter,
                onValueChange = {
                    filter = it
                },
                modifier = Modifier
                    .weight(1f),
                maxLines = 1
            )
            Button(
                onClick = { isFiltersShow = !isFiltersShow },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Zobraziť filtre"
                )
            }
        }
        if (isFiltersShow) {
            InventoryDetailFiltersComponent()
        }
        PropertyListView(properties = propertyEntityListToPropertyPreviewList(properties))
    }
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