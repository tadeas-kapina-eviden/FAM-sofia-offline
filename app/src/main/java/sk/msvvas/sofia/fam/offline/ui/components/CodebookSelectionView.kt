package sk.msvvas.sofia.fam.offline.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity
import sk.msvvas.sofia.fam.offline.ui.theme.FAMInventuraOfflineClientTheme
import java.util.*


/**
 * Component used for selecting data from various codebooks
 * @param codebookData list of selectable codebooks
 * @param lastFilterValue value of filter when view pop up (usually last selected value)
 * @param idGetter function to get value of id from codebook
 * @param descriptionGetter function to get value of description form codebook
 * @param onSelect function that is executed when one of codebook is selected or is clicked done button on keyboard
 * @param onClose function that is executed when close or back buttons are clicked
 * @param onDelete function that is executed, when delete button is clicked
 */
@Composable
fun CodebookSelectionView(
    codebookData: List<Any>,
    lastFilterValue: String,
    idGetter: (Any) -> String,
    descriptionGetter: (Any) -> String,
    onSelect: (String) -> Any,
    onClose: () -> Unit,
    onDelete: () -> Unit,
    checkFormat: (String) -> Boolean,
    wrongFormatText: String = ""
) {
    var filterValue by remember {
        mutableStateOf(lastFilterValue)
    }

    var filteredCodebookData by remember {
        mutableStateOf(codebookData.filter {
            filterValue.isEmpty() || idGetter(it).contains(filterValue) || descriptionGetter(it).contains(
                filterValue
            )
        })
    }

    val focusRequester by remember {
        mutableStateOf(FocusRequester())
    }

    var wrongFormat by remember {
        mutableStateOf(false)
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colors.surface,
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 25.dp, bottom = 5.dp)
                    .align(Alignment.Start)
                    .fillMaxWidth(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close button.",
                    modifier = Modifier
                        .clickable { onClose() }
                        .align(Alignment.End),
                    tint = MaterialTheme.colors.primary
                )
            }
            TextField(
                value = TextFieldValue(
                    text = filterValue,
                    selection = if (filterValue.isBlank()) TextRange(0) else TextRange(filterValue.length)
                ),
                onValueChange = {
                    filterValue = it.text
                    filteredCodebookData = codebookData.filter { codebook ->
                        filterValue.isEmpty()
                                || idGetter(codebook).lowercase()
                            .contains(
                                filterValue.lowercase(
                                    Locale.getDefault()
                                )
                            )
                                || descriptionGetter(codebook).lowercase()
                            .contains(
                                filterValue.lowercase(
                                    Locale.getDefault()
                                )
                            )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 5.dp)
                    .focusRequester(focusRequester)
                    .border(
                        color = MaterialTheme.colors.primary,
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp)
                    ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (checkFormat(filterValue)) {
                            onSelect(filterValue)
                        } else {
                            wrongFormat = true
                        }
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.primary,
                    backgroundColor = MaterialTheme.colors.secondary,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primaryVariant
                ),
                textStyle = MaterialTheme.typography.body1
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
            ) {
                items(filteredCodebookData) { item ->
                    Row(
                        modifier = Modifier
                            .padding(vertical = 1.dp)
                            .clickable(enabled = true) {
                                onSelect(idGetter(item))
                            }
                            .background(color = MaterialTheme.colors.secondary)
                            .border(
                                color = MaterialTheme.colors.primary,
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp)
                            ),
                    ) {
                        Text(
                            highlightSelectedText(filterValue, idGetter(item)),
                            modifier = Modifier
                                .weight(3f)
                                .padding(horizontal = 10.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primary
                        )
                        Text(
                            highlightSelectedText(filterValue, descriptionGetter(item)),
                            modifier = Modifier
                                .weight(4f)
                                .padding(horizontal = 10.dp, vertical = 2.dp),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 15.dp)
            ) {
                StyledTextBackButton(
                    onClick = {
                        onDelete()
                    },
                    text = "Zmazať",
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.1f))
                StyledTextButton(
                    onClick = {
                        if (checkFormat(filterValue)) {
                            onSelect(filterValue)
                        } else {
                            wrongFormat = true
                        }
                    },
                    text = "Potvrdiť",
                    modifier = Modifier
                        .weight(1f)
                )
            }

        }

        if (wrongFormat) {
            InformationModalWindow(
                "Nesprávny formát",
                wrongFormatText,
                "Potvrdiť",
            ) { wrongFormat = false }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BackHandler {
        onClose()
    }
}

/**
 * Function for highlighting part of text in text block
 * Highlighted text is bold
 * @param selected text we want to highlight
 * @param text full text
 */
fun highlightSelectedText(selected: String, text: String): AnnotatedString {
    if (selected.isEmpty() || !text.contains(selected) || text.split(selected).size > 2)
        return buildAnnotatedString { append(text) }
    else
        return buildAnnotatedString {
            //TODO fix filtered text highlighting (case-insensitive)
            val unselected: List<String> = text.split(selected)
            if (unselected.size > 1 || text.find(unselected[0]) == 0)
                append(unselected[0])
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(selected)
            }
            if (unselected.size > 1)
                append(unselected[1])
        }
}

/**
 * Function to find first appear of wanted text in String
 * @param predicate text we want to find
 */
private fun String.find(predicate: String): Int {
    for (i in 0..(this.length - predicate.length))
        if (predicate == this.subSequence(i, i + predicate.length))
            return i
    return Int.MAX_VALUE
}

/**
 * Default preview for Codebook selection view
 */
@Preview(showBackground = true)
@Composable
fun CodebookSelectionPreview() {
    FAMInventuraOfflineClientTheme {
        CodebookSelectionView(
            codebookData = listOf(
                LocalityCodebookEntity(
                    id = "12345",
                    description = "Zdlhavy popis prvej lokality"
                ),
                LocalityCodebookEntity(
                    id = "25745",
                    description = "Zdlhavy popis druhej lokality"
                ),
                LocalityCodebookEntity(
                    id = "15875",
                    description = "Zdlhavy popis tretej lokality"
                ),
            ),
            lastFilterValue = "",
            idGetter = {
                (it as LocalityCodebookEntity).id
            },
            descriptionGetter = {
                (it as LocalityCodebookEntity).description
            },
            onSelect = {},
            onClose = {},
            onDelete = {},
            checkFormat = { true }
        )
    }
}