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
import androidx.compose.ui.unit.sp
import sk.msvvas.sofia.fam.offline.R
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.model.PropertyPreviewModel
import sk.msvvas.sofia.fam.offline.ui.components.CodebookSelectionView
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

    val isCodebookSelectionViewShown by inventoryDetailViewModel.isCodebookSelectionViewShown.observeAsState(
        false
    )
    val codebookSelectionViewData by inventoryDetailViewModel.codebookSelectionViewData.observeAsState(
        listOf()
    )

    val codebookSelectionViewLastValue by inventoryDetailViewModel.codebookSelectionViewLastValue.observeAsState(
        ""
    )

    val codebookSelectionViewIdGetter by inventoryDetailViewModel.codebookSelectionViewIdGetter.observeAsState { "" }

    val codebookSelectionViewDescriptionGetter by inventoryDetailViewModel.codebookSelectionViewDescriptionGetter.observeAsState { "" }
    val selectCodebook by inventoryDetailViewModel.selectCodebook.observeAsState { "" }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .verticalScroll(
                        enabled = true,
                        state = ScrollState(0)
                    )
                    .weight(1f)
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
                            keyboardType = KeyboardType.Ascii
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
                        .fillMaxWidth(0.5f)
                ) {
                    StatusFilterButton(
                        isSelected = (statusFilter == 'U'),
                        idSelected = R.drawable.unprocessed_selected,
                        idUnselected = R.drawable.unprocessed_unselected,
                        text = "Nespracované",
                        count = inventoryDetailViewModel.countUnprocessed(),
                        onClick = {
                            inventoryDetailViewModel.statusFilterUnprocessed()
                        }
                    )
                    StatusFilterButton(
                        isSelected = (statusFilter == 'P'),
                        idSelected = R.drawable.processed_selected,
                        idUnselected = R.drawable.processed_unselected,
                        text = "Spracované",
                        count = inventoryDetailViewModel.countProcessed(),
                        onClick = {
                            inventoryDetailViewModel.statusFilterProcessed()
                        }
                    )
                    StatusFilterButton(
                        isSelected = (statusFilter == 'S'),
                        idSelected = R.drawable.status_selected,
                        idUnselected = R.drawable.status_unselected,
                        text = "Status",
                        count = -1,
                        onClick = {
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
                if (statusFilter == 'S') {
                    InventoryDetailStatusView(inventoryDetailViewModel = inventoryDetailViewModel)
                } else {
                    PropertyListView(
                        properties = propertyEntityListToPropertyPreviewList(properties),
                        changeView = {
                            inventoryDetailViewModel.onSelectProperty(it)
                        })
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    inventoryDetailViewModel.onSelectProperty(-1)
                }) {
                Text(text = "+ Nový")
            }
        }
        if (errorHeader.isNotEmpty()) {
            ErrorModalWindow(
                errorHeader = errorHeader,
                errorText = errorText
            ) {
                inventoryDetailViewModel.closeErrorAlert()
            }
        }

        if (isCodebookSelectionViewShown) {
            CodebookSelectionView(
                codebookData = codebookSelectionViewData,
                lastFilerValue = codebookSelectionViewLastValue,
                idGetter = codebookSelectionViewIdGetter,
                descriptionGetter = codebookSelectionViewDescriptionGetter,
                onSelect = selectCodebook,
                onClose = { inventoryDetailViewModel.closeCodebookSelectionView() }
            )
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
private fun RowScope.StatusFilterButton(
    isSelected: Boolean,
    idSelected: Int,
    idUnselected: Int,
    text: String,
    count: Int,
    onClick: () -> Unit
) {
    Column(
        Modifier
            .clickable(enabled = true) {
                onClick()
            }
            .weight(1f)
    ) {
        Box {
            Image(
                painter = painterResource(id = (if (isSelected) idSelected else idUnselected)),
                contentDescription = ""
            )
            if (count >= 0) {
                Text(
                    text = count.toString(),
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
        Text(
            text = text,
            style = TextStyle(
                fontSize = 12.sp
            )
        )
    }
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