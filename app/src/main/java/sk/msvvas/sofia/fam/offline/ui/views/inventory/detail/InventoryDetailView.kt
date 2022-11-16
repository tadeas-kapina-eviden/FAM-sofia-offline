package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.msvvas.sofia.fam.offline.R
import sk.msvvas.sofia.fam.offline.data.transformator.PropertyTransformator
import sk.msvvas.sofia.fam.offline.ui.components.*
import sk.msvvas.sofia.fam.offline.ui.views.property.list.PropertyListItem

@Composable
fun InventoryDetailView(
    inventoryDetailViewModel: InventoryDetailViewModel
) {
    val properties by inventoryDetailViewModel.filteredProperties.observeAsState(emptyList())
    val isFiltersShow by inventoryDetailViewModel.isFiltersShown.observeAsState(false)
    val inventoryId by inventoryDetailViewModel.inventoryId.observeAsState("")
    val codeFilter by inventoryDetailViewModel.codeFilter.observeAsState("")
    val localityFilter by inventoryDetailViewModel.localityFilter.observeAsState("")
    val roomFilter by inventoryDetailViewModel.roomFilter.observeAsState("")
    val userFilter by inventoryDetailViewModel.userFilter.observeAsState("")
    val errorHeader by inventoryDetailViewModel.errorHeader.observeAsState("")
    val errorText by inventoryDetailViewModel.errorText.observeAsState("")
    val codeFilterLocality by inventoryDetailViewModel.codeFilterLocality.observeAsState()
    val codeFilterRoom by inventoryDetailViewModel.codeFilterRoom.observeAsState()
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
    val deleteCodebook by inventoryDetailViewModel.deleteCodebook.observeAsState {}

    val exitModalShown by inventoryDetailViewModel.exitModalShown.observeAsState(false)
    val submitInventoryConfirmModalShown by inventoryDetailViewModel.submitInventoryConfirmModalShown.observeAsState(
        false
    )
    val requireLoginModalShown by inventoryDetailViewModel.requireLoginModalShown.observeAsState(
        false
    )

    val loadingData by inventoryDetailViewModel.loadingData.observeAsState(
        false
    )

    val loadingState by inventoryDetailViewModel.loadingState.observeAsState("")


    val activity = (LocalContext.current as? Activity)


    val focusRequester = remember {
        FocusRequester()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .align(Alignment.Center)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    ) {
                        TextField(
                            value = codeFilter,
                            onValueChange = {
                                inventoryDetailViewModel.onCodeFilterChange(it)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.primary,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .focusRequester(focusRequester),
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
                                },
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                textColor = MaterialTheme.colors.primary,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = MaterialTheme.typography.body1,

                            )
                        StyledTextButton(
                            onClick = { inventoryDetailViewModel.onFiltersShowClick() },
                            modifier = Modifier
                                .weight(1f),
                            text = if (!isFiltersShow) "Zobraziť filtre" else "Skryť filtre",
                            textModifier = Modifier
                                .padding(vertical = 8.dp)
                        )
                    }
                }
                if (isFiltersShow) {
                    item { InventoryDetailFiltersComponent(inventoryDetailViewModel) }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
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
                        StatusFilterButton(
                            isSelected = false,
                            idSelected = R.drawable.sync,
                            idUnselected = R.drawable.sync,
                            text = "Synchronizovať",
                            count = -1,
                            onClick = {
                                inventoryDetailViewModel.submitInventoryConfirmModalShow()
                            }
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.primary
                            )
                    ) {
                        Text(
                            text = "Inv. $inventoryId"
                                    + if (localityFilter.isNotEmpty()) ", Lok. $localityFilter" else ""
                                    + if (roomFilter.isNotEmpty()) ", Miest. $roomFilter" else ""
                                    + if (userFilter.isNotEmpty()) ", Os. $userFilter" else "",
                            modifier = Modifier
                                .padding(horizontal = 15.dp, vertical = 1.dp),
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
                if (statusFilter == 'S') {
                    item { InventoryDetailStatusView(inventoryDetailViewModel = inventoryDetailViewModel) }
                } else {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawWithBottomLine(
                                    width = 1f,
                                    color = MaterialTheme.colors.primary
                                )
                                .padding(horizontal = 15.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = "Názov",
                                modifier = Modifier.weight(3f),
                                color = MaterialTheme.colors.primary,
                                style = MaterialTheme.typography.body1
                            )
                            Text(
                                text = "Status",
                                modifier = Modifier
                                    .weight(2f)
                                    .padding(start = 5.dp),
                                color = MaterialTheme.colors.primary,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                    items(PropertyTransformator.propertyEntityListToPropertyPreviewList(properties)) { item ->
                        PropertyListItem(
                            property = item,
                            onClick = { id -> inventoryDetailViewModel.onSelectProperty(id) })
                    }
                }
            }
            Row(
                Modifier
                    .padding(bottom = 15.dp)
            ) {
                StyledTextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        inventoryDetailViewModel.onSelectProperty(-1)
                    },
                    text = "+  Nový"
                )
            }
        }
        if (submitInventoryConfirmModalShown) {
            ConfirmModalWindow(
                header = "Potvrdenie odoslania",
                body = "Naozaj chcete odoslať inventúru na spracovanie?",
                confirmButtonText = "Áno",
                confirmButtonAction = {
                    inventoryDetailViewModel.submitInventory()
                },
                declineButtonText = "Nie",
                declineButtonAction = {
                    inventoryDetailViewModel.submitInventoryConfirmModalHide()
                }
            )
        }
        if (requireLoginModalShown) {
            ConfirmModalWindow(
                header = "Vyžaduje sa prihlásenie!",
                body = "Pre pokračovanie sa musíte prihlásiť.",
                confirmButtonText = "Prihlásiť",
                confirmButtonAction = { inventoryDetailViewModel.toLogin() },
                declineButtonText = "Zrušiť",
                declineButtonAction = { inventoryDetailViewModel.requireLoginModalHide() }
            )
        }
        if (loadingData) {
            LoadingAnimationModalWindow(header = "Načítavanie", loadingState)
        }
        if (exitModalShown) {
            ConfirmModalWindow(
                header = "Opúšťate aplikáciu...",
                body = "Naozaj chcete opustiť aplikáciu?",
                confirmButtonText = "Áno",
                confirmButtonAction = {
                    activity?.finish()
                },
                declineButtonText = "Nie",
                declineButtonAction = {
                    inventoryDetailViewModel.hideExitModalWindow()
                }
            )
        }
        if (errorHeader.isNotEmpty()) {
            InformationModalWindow(
                header = errorHeader,
                body = errorText,
                buttonText = "Zavrieť",
                buttonAction = { inventoryDetailViewModel.closeErrorAlert() }
            )
        }

        if (isCodebookSelectionViewShown) {
            CodebookSelectionView(
                codebookData = codebookSelectionViewData,
                lastFilterValue = codebookSelectionViewLastValue,
                idGetter = codebookSelectionViewIdGetter,
                descriptionGetter = codebookSelectionViewDescriptionGetter,
                onSelect = { selectCodebook(it); focusRequester.requestFocus() },
                onClose = { inventoryDetailViewModel.closeCodebookSelectionView(); focusRequester.requestFocus() },
                onDelete = { deleteCodebook(); focusRequester.requestFocus() }
            )
        }

        if (codeFilterRoom != null || codeFilterLocality != null) {
            InformationModalWindow(
                header = "Vstupujete do miestnosti...",
                body = "Lokalita: $codeFilterLocality\nMiestnosť: $codeFilterRoom",
                buttonText = "OK",
                buttonAction = { inventoryDetailViewModel.confirmLocalityChange() }
            )
        }
    }
    inventoryDetailViewModel.filterOutValues()
    BackHandler {
        inventoryDetailViewModel.showExitModalWindow()
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
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
    Box(
        modifier = Modifier
            .weight(1f)
            .padding(vertical = 8.dp)

    ) {
        Column(
            Modifier
                .clickable(enabled = true) {
                    onClick()
                }
                .padding(start = 8.dp, top = 8.dp, end = 16.dp)
        ) {
            Image(
                painter = painterResource(id = (if (isSelected) idSelected else idUnselected)),
                contentDescription = ""
            )
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (count >= 0) {
            Text(
                text = count.toString(),
                modifier = Modifier.align(Alignment.TopEnd),
                style = MaterialTheme.typography.body1.copy(
                    textAlign = TextAlign.End,
                    fontSize = 10.sp
                )
            )
        }
    }
}