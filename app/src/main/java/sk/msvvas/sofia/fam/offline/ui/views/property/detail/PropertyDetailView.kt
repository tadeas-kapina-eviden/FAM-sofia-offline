package sk.msvvas.sofia.fam.offline.ui.views.property.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.ui.components.CodebookSelectionView
import sk.msvvas.sofia.fam.offline.ui.components.InputRow
import sk.msvvas.sofia.fam.offline.ui.components.drawWithBottomLine


@Composable
fun PropertyDetailView(
    propertyDetailViewModel: PropertyDetailViewModel
) {

    val property by propertyDetailViewModel.property.observeAsState(
        PropertyEntity()
    )

    val locality by propertyDetailViewModel.locality.observeAsState("")
    val room by propertyDetailViewModel.room.observeAsState("")
    val user by propertyDetailViewModel.user.observeAsState("")
    val place by propertyDetailViewModel.place.observeAsState("")
    val fixedNote by propertyDetailViewModel.fixedNote.observeAsState("")
    val variableNote by propertyDetailViewModel.variableNote.observeAsState("")
    val errorHeader by propertyDetailViewModel.errorHeader.observeAsState("")
    val errorText by propertyDetailViewModel.errorText.observeAsState("")

    val isCodebookSelectionViewShown by propertyDetailViewModel.isCodebookSelectionViewShown.observeAsState(
        false
    )

    val codebookSelectionViewData by propertyDetailViewModel.codebookSelectionViewData.observeAsState(
        listOf()
    )

    val codebookSelectionViewLastValue by propertyDetailViewModel.codebookSelectionViewLastValue.observeAsState(
        ""
    )

    val codebookSelectionViewIdGetter by propertyDetailViewModel.codebookSelectionViewIdGetter.observeAsState { "" }

    val codebookSelectionViewDescriptionGetter by propertyDetailViewModel.codebookSelectionViewDescriptionGetter.observeAsState { "" }
    val selectCodebook by propertyDetailViewModel.selectCodebook.observeAsState { "" }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    enabled = true,
                    state = ScrollState(0)
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                TextField(
                    value = if (propertyDetailViewModel.isNew) "Nová položka" else "Podrobnosti: " + property.propertyNumber + "/" + property.subnumber,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.background
                    )
                )
                TextField(
                    value = property.textMainNumber,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.background
                    ),
                    textStyle = LocalTextStyle.current
                        .copy(textAlign = TextAlign.End)
                )

                InputRowStyled(
                    label = "Sériové číslo",
                    value = property.serialNumber,
                    onClick = {}
                )
                InputRowStyled(
                    label = "Invent. číslo",
                    value = property.inventNumber,
                    onClick = {}
                )
                InputRowStyled(
                    label = "Závod",
                    value = property.werks,
                    onClick = {}
                )
                InputRowStyled(
                    label = "Lokalita",
                    value = locality,
                    onClick = { propertyDetailViewModel.showLocationCodebookSelectionView() }
                )
                InputRowStyled(
                    label = "Miestnosť",
                    value = room,
                    onClick = { propertyDetailViewModel.showRoomCodebookSelectionView() }
                )
                InputRowStyled(
                    label = "z. Os.",
                    value = user,
                    onClick = { propertyDetailViewModel.showUserCodebookSelectionView() }
                )
                InputRowStyled(
                    label = "Stredisko",
                    value = property.center,
                    onClick = {}
                )
                InputRowStyled(
                    label = "Pracovisko",
                    value = place,
                    onClick = { propertyDetailViewModel.showPlaceCodebookSelectionView() }
                )
                InputRowStyled(
                    label = "Poznámka",
                    value = fixedNote,
                    onClick = { propertyDetailViewModel.showFixedNoteCodebookSelectionView() }
                )
                InputRowStyled(
                    label = "Vlastná pozn.",
                    value = variableNote,
                    onClick = { propertyDetailViewModel.showVariableNoteCodebookSelectionView() }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp
                        )
                ) {
                    Row(modifier = Modifier.weight(1f)) {
                        Checkbox(
                            checked = propertyDetailViewModel.isManual,
                            onCheckedChange = {},
                            enabled = false
                        )
                        Text(
                            text = "Manuálny",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Row(modifier = Modifier.weight(1f)) {
                        Checkbox(
                            checked = propertyDetailViewModel.isNew,
                            onCheckedChange = {},
                            enabled = false
                        )
                        Text(
                            text = "Nový",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                if ("SZN".contains(property.recordStatus)) {
                    Button(
                        onClick = { propertyDetailViewModel.rollback() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Vráť na originál")
                    }
                }
                Button(
                    onClick = { propertyDetailViewModel.submit() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Potvrď")
                }
            }
        }
        if (isCodebookSelectionViewShown) {
            CodebookSelectionView(
                codebookData = codebookSelectionViewData,
                lastFilerValue = codebookSelectionViewLastValue,
                idGetter = codebookSelectionViewIdGetter,
                descriptionGetter = codebookSelectionViewDescriptionGetter,
                onSelect = selectCodebook,
                onClose = { propertyDetailViewModel.closeCodebookSelectionView() }
            )
        }

        if (propertyDetailViewModel.property.value != null) {
            propertyDetailViewModel.lateInitVarsData()
        }

        if (errorHeader.isNotEmpty()) {
            ErrorModalWindow(
                errorHeader = errorHeader,
                errorText = errorText
            ) {
                propertyDetailViewModel.closeErrorAlert()
            }
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
            .drawWithBottomLine(width = 1f, color = MaterialTheme.colors.primary),
        ratio = 2f / 5f,
        textFieldTextAlign = TextAlign.End,
        onClick = onClick
    )
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