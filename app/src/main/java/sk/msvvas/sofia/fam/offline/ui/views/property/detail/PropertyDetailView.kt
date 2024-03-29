package sk.msvvas.sofia.fam.offline.ui.views.property.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.ui.components.*

/**
 * View for showing and changing data of single property
 * @param propertyDetailViewModel view model for this view
 */
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
    val userName by propertyDetailViewModel.userName.observeAsState("")
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
    val deleteCodebook by propertyDetailViewModel.deleteCodebook.observeAsState {}
    val checkFormatCodebook by propertyDetailViewModel.checkFormatCodebook.observeAsState {}
    val wrongFormatTextCodebook by propertyDetailViewModel.wrongFormatTextCodebook.observeAsState("")

    Box {
        if (property != null) {
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
                ) {
                    TextField(
                        value = if (property.propertyNumber.isBlank() || property.propertyNumber == "NOVY") "Nová položka" else "Podrobnosti: " + property.propertyNumber.toLong()
                            .toString() + "/" + property.subnumber.toLong().toString(),
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = MaterialTheme.colors.primary
                        ),
                        textStyle = MaterialTheme.typography.body1
                    )
                    TextField(
                        value = property.textMainNumber,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = MaterialTheme.colors.primary
                        ),
                        textStyle = MaterialTheme.typography.body1
                            .copy(textAlign = TextAlign.End)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .align(Alignment.CenterHorizontally)
                    ) {
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
                            onClick = { propertyDetailViewModel.showLocationCodebookSelectionView() },
                            enabled = true
                        )
                        InputRowStyled(
                            label = "Miestnosť",
                            value = room,
                            onClick = { propertyDetailViewModel.showRoomCodebookSelectionView() },
                            enabled = true
                        )
                        InputRowStyled(
                            label = "z. Os.",
                            value = user + if (userName.isNotBlank()) "\n" + userName else "",
                            onClick = { propertyDetailViewModel.showUserCodebookSelectionView() },
                            enabled = true
                        )
                        InputRowStyled(
                            label = "Stredisko",
                            value = property.center,
                            onClick = {}
                        )
                        InputRowStyled(
                            label = "Pracovisko",
                            value = place,
                            onClick = { propertyDetailViewModel.showPlaceCodebookSelectionView() },
                            enabled = true
                        )
                        InputRowStyled(
                            label = "Poznámka",
                            value = fixedNote,
                            onClick = { propertyDetailViewModel.showFixedNoteCodebookSelectionView() },
                            enabled = true
                        )
                        InputRowStyled(
                            label = "Vlastná pozn.",
                            value = variableNote,
                            onClick = { propertyDetailViewModel.showVariableNoteInputView() },
                            enabled = true
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
                                    enabled = false,
                                    colors = CheckboxDefaults.colors(
                                        disabledColor = MaterialTheme.colors.primaryVariant
                                    )
                                )
                                Text(
                                    text = "Manuálny",
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    color = MaterialTheme.colors.primaryVariant,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                            Row(modifier = Modifier.weight(1f)) {
                                Checkbox(
                                    checked = property.isNew,
                                    onCheckedChange = {},
                                    enabled = false,
                                    colors = CheckboxDefaults.colors(
                                        disabledColor = MaterialTheme.colors.primaryVariant
                                    )
                                )
                                Text(
                                    text = "Nový",
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    color = MaterialTheme.colors.primaryVariant,
                                    style = MaterialTheme.typography.body1

                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(bottom = 15.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    if ("SZN".contains(property.recordStatus) && !property.isSaved) {
                        StyledTextBackButton(
                            onClick = { propertyDetailViewModel.rollback() },
                            modifier = Modifier.weight(1f),
                            text = "Vráť na originál",
                        )
                        Spacer(modifier = Modifier.weight(0.1f))
                    }
                    StyledTextButton(
                        onClick = { propertyDetailViewModel.submit() },
                        modifier = Modifier.weight(1f),
                        textModifier = Modifier.padding(vertical = 10.dp),
                        text = "Potvrď",
                    )
                }
            }
            if (isCodebookSelectionViewShown) {
                CodebookSelectionView(
                    codebookData = codebookSelectionViewData,
                    lastFilterValue = codebookSelectionViewLastValue,
                    idGetter = codebookSelectionViewIdGetter,
                    descriptionGetter = codebookSelectionViewDescriptionGetter,
                    onSelect = selectCodebook,
                    onClose = { propertyDetailViewModel.closeCodebookSelectionView() },
                    onDelete = deleteCodebook,
                    checkFormat = checkFormatCodebook as (String) -> Boolean,
                    wrongFormatText = wrongFormatTextCodebook
                )
            }

            if (propertyDetailViewModel.property.value != null) {
                propertyDetailViewModel.lateInitVarsData()
            }

            if (errorHeader.isNotEmpty()) {
                InformationModalWindow(
                    header = errorHeader,
                    body = errorText,
                    buttonText = "Zavrieť",
                    buttonAction = { propertyDetailViewModel.closeErrorAlert() }
                )
            }
        } else {
            LoadingAnimationModalWindow(header = "Načítavanie", "Načítava sa majetok...")
        }
    }
    BackHandler {
        if (propertyDetailViewModel.id < 0) {
            propertyDetailViewModel.rollback()
        } else {
            propertyDetailViewModel.goBack()
        }
    }
}


/**
 * Styled input row in property detail
 */
@Composable
private fun InputRowStyled(
    label: String,
    value: String,
    enabled: Boolean = false,
    onClick: () -> Unit
) {
    InputRow(
        label = label,
        value = value,
        modifier = if (enabled) {
            Modifier
                .padding(bottom = 1.dp)
                .background(
                    color = MaterialTheme.colors.secondary,
                    shape = RoundedCornerShape(8.dp)
                )
        } else {
            Modifier.padding(bottom = 1.dp)
        },
        ratio = 1f,
        valueTextAlign = TextAlign.End,
        labelTextColor = if (enabled) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
        valueTextColor = if (enabled) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
        onClick = onClick,
        backgroundColor = if (enabled) MaterialTheme.colors.secondary else Color.Transparent,
        labelTextHorizontalPadding = 15.dp
    )
}