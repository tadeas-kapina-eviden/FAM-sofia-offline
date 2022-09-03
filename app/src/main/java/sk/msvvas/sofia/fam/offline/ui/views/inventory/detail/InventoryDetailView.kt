package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.R
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.model.PropertyPreviewModel
import sk.msvvas.sofia.fam.offline.ui.views.navigation.Routes
import sk.msvvas.sofia.fam.offline.ui.views.property.list.PropertyListView

@Composable
fun InventoryDetailView(
    inventoryDetailViewModel: InventoryDetailViewModel
) {

    val properties by inventoryDetailViewModel.filteredProperties.observeAsState(emptyList())
    val isFiltersShow by inventoryDetailViewModel.isFiltersShown.observeAsState(false)
    val inventoryId by inventoryDetailViewModel.inventoryId.observeAsState("")
    val codeFilter by inventoryDetailViewModel.codeFilter.observeAsState("")
    val errorHeader by inventoryDetailViewModel.errorHeader.observeAsState("")
    val errorText by inventoryDetailViewModel.errorText.observeAsState("")
    val codeFilterLocality by inventoryDetailViewModel.codeFilterLocality.observeAsState("")
    val codeFilterRoom by inventoryDetailViewModel.codeFilterRoom.observeAsState("")
    val statusFilter by inventoryDetailViewModel.statusFilter.observeAsState('U')


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .verticalScroll(
                    enabled = true,
                    state = ScrollState(0)
                )
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
                        imeAction = ImeAction.Done,
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.Characters,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            inventoryDetailViewModel.runCodeFilter()
                        }
                    ),
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
            ) {
                Image(
                    painter = painterResource(id = (if (statusFilter == 'U') R.drawable.unprocessed_selected else R.drawable.unprocessed_unselected)),
                    contentDescription = "",
                    Modifier.clickable(enabled = true) {
                        inventoryDetailViewModel.statusFilterUnprocessed()
                    }
                )
                Image(
                    painter = painterResource(id = (if (statusFilter == 'P') R.drawable.processed_selected else R.drawable.processed_unselected)),
                    contentDescription = "",
                    Modifier.clickable(enabled = true) {
                        inventoryDetailViewModel.statusFilterProcessed()
                    }
                )
                Image(
                    painter = painterResource(id = (if (statusFilter == 'S') R.drawable.status_selected else R.drawable.status_unselected)),
                    contentDescription = "",
                    Modifier.clickable(enabled = true) {
                        inventoryDetailViewModel.statusFilterStatus()
                    }
                )
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
            PropertyListView(
                properties = propertyEntityListToPropertyPreviewList(properties),
                changeView = { inventoryDetailViewModel.navController.navigate(Routes.PROPERTY_DETAIL.withArgs(it.toString())) })
        }

        if (errorHeader.isNotEmpty()) {
            ErrorModalWindow(
                errorHeader = errorHeader,
                errorText = errorText
            ) {
                inventoryDetailViewModel.closeErrorAlert()
            }
        }

        if (codeFilterRoom.isNotEmpty() || codeFilterLocality.isNotEmpty()) {
            LocalityChangeModalWindow(
                codeFilterLocality = codeFilterLocality,
                codeFilterRoom = codeFilterRoom
            ) {
                inventoryDetailViewModel.confirmLocalityChange()
            }
        }
    }
    inventoryDetailViewModel.filterOutValues()
}

@Composable
private fun BoxScope.ErrorModalWindow(
    errorHeader: String,
    errorText: String,
    close: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xBB222222),
        content = {}
    )
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .align(Alignment.Center)
            .background(color = MaterialTheme.colors.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary
            ),
    ) {
        TextField(
            value = errorHeader,
            readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            textStyle = TextStyle(fontSize = MaterialTheme.typography.h6.fontSize),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
        TextField(
            value = errorText,
            readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
        Row {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { close() }
            ) {
                Text(text = "Zavrieť")
            }
        }
    }
}

@Composable
private fun BoxScope.LocalityChangeModalWindow(
    codeFilterLocality: String,
    codeFilterRoom: String,
    confirm: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xBB222222),
        content = {}
    )
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .align(Alignment.Center)
            .background(color = MaterialTheme.colors.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary
            ),
    ) {
        TextField(
            value = "Vstupujete do miestnosti...",
            readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            textStyle = TextStyle(fontSize = MaterialTheme.typography.h6.fontSize),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
        TextField(
            value = "Lokalita: $codeFilterLocality\nMiestnosť: $codeFilterRoom",
            readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
        Row {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { confirm() }
            ) {
                Text(text = "Ok")
            }
        }
    }
}

fun propertyEntityListToPropertyPreviewList(propertyEntities: List<PropertyEntity>): List<PropertyPreviewModel> {
    return propertyEntities.map {
        PropertyPreviewModel(
            id = it.id,
            textMainNumber = it.textMainNumber,
            status = it.recordStatus,
            subNumber = it.subnumber,
            propertyNumber = it.propertyNumber
        )
    }
}