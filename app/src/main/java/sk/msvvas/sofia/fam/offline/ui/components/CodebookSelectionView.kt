package sk.msvvas.sofia.fam.offline.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.entities.codebook.LocalityCodebookEntity

@Composable
fun CodebookSelectionView(
    codebookData: List<Any>,
    lastFilerValue: String,
    idGetter: (Any) -> String,
    descriptionGetter: (Any) -> String,
    onSelect: (String) -> Any,
    onClose: () -> Unit
) {
    var filterValue by remember {
        mutableStateOf(lastFilerValue)
    }

    var filteredCodebookData by remember {
        mutableStateOf(codebookData.filter {
            filterValue.isEmpty() || idGetter(it).contains(filterValue) || descriptionGetter(it).contains(
                filterValue
            )
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.background,
            )
    ) {
        Row(
            modifier = Modifier
                .padding(top = 15.dp, end = 15.dp, bottom = 5.dp)
                .align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close button.",
                modifier = Modifier.clickable { onClose() }
            )
        }
        TextField(
            value = filterValue,
            onValueChange = {
                filterValue = it
                filteredCodebookData = codebookData.filter { codebook ->
                    filterValue.isEmpty() || idGetter(codebook).contains(filterValue) || descriptionGetter(
                        codebook
                    ).contains(filterValue)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp),
            keyboardActions = KeyboardActions(
                onDone = {
                    onSelect(filterValue)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(
                    enabled = true,
                    state = ScrollState(0)
                )
        ) {
            filteredCodebookData.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 15.dp)
                        .clickable(enabled = true) {
                            onSelect(idGetter(it))
                        }
                ) {
                    Text(
                        highlightSelectedText(filterValue, idGetter(it)),
                        modifier = Modifier
                            .weight(2f)
                    )
                    Text(
                        highlightSelectedText(filterValue, descriptionGetter(it)),
                        modifier = Modifier
                            .weight(5f),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
        Button(
            onClick = {
                onSelect(filterValue)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "OK")
        }
    }
}

fun highlightSelectedText(selected: String, text: String): AnnotatedString {
    if (selected.isEmpty() || !text.contains(selected) || text.split(selected).size > 2)
        return buildAnnotatedString { append(text) }
    else
        return buildAnnotatedString {
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

private fun String.find(predicate: String): Int {
    for (i in 0..(this.length - predicate.length))
        if (predicate == this.subSequence(i, i + predicate.length))
            return i
    return Int.MAX_VALUE
}

@Preview(showBackground = true)
@Composable
fun CodebookSelectionPreview() {
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
        lastFilerValue = "",
        idGetter = {
            (it as LocalityCodebookEntity).id
        },
        descriptionGetter = {
            (it as LocalityCodebookEntity).description
        },
        onSelect = {},
        onClose = {}
    )
}