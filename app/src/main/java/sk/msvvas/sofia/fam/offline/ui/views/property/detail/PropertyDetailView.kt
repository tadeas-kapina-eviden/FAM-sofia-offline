package sk.msvvas.sofia.fam.offline.ui.views.property.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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

                InputRow(
                    label = "Sériové číslo",
                    value = property.serialNumber,
                    onClick = {}
                )
                InputRow(
                    label = "Invent. číslo",
                    value = property.inventNumber,
                    onClick = {}
                )
                InputRow(
                    label = "Závod",
                    value = property.werks,
                    onClick = {}
                )
                InputRow(
                    label = "Lokalita",
                    value = locality,
                    onClick = { propertyDetailViewModel.showLocationCodebookSelectionView() }
                )
                InputRow(
                    label = "Miestnosť",
                    value = room,
                    onClick = { propertyDetailViewModel.showRoomCodebookSelectionView() }
                )
                InputRow(
                    label = "z. Os.",
                    value = user,
                    onClick = { propertyDetailViewModel.showUserCodebookSelectionView() }
                )
                InputRow(
                    label = "Stredisko",
                    value = property.center,
                    onClick = {}
                )
                InputRow(
                    label = "Pracovisko",
                    value = place,
                    onClick = { propertyDetailViewModel.showPlaceCodebookSelectionView() }
                )
                InputRow(
                    label = "Poznámka",
                    value = fixedNote,
                    onClick = { propertyDetailViewModel.showFixedNoteCodebookSelectionView() }
                )
                InputRow(
                    label = "Vlastná pozn.",
                    value = variableNote,
                    onClick = { propertyDetailViewModel.showVariableNoteCodebookSelectionView() }
                )
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
private fun InputRow(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawWithBottomLine(
                width = 1f,
                color = MaterialTheme.colors.primary
            )
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(2f)
        )
        TextField(
            value = value,
            enabled = false,
            onValueChange = {},
            modifier = Modifier
                .weight(5f)
                .clickable(enabled = true) {
                    onClick()
                },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = LocalTextStyle.current
                .copy(textAlign = TextAlign.End)
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