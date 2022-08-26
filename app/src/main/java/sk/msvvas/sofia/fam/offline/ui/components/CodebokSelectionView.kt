package sk.msvvas.sofia.fam.offline.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.entities.codebook.LocalityCodebookEntity

@Composable
fun CodebookSelectionView(
    codebookData: List<Any>,
    idGetter: (Any) -> String,
    descriptionGetter: (Any) -> String,
    onSelect: (String) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp)
        )
        codebookData.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 15.dp)
                    .clickable {
                        onSelect(idGetter(codebookData))
                    }
                    .verticalScroll(
                        enabled = true,
                        state = ScrollState(0)
                    )
            ) {
                Text(
                    text = idGetter(it),
                    modifier = Modifier
                        .weight(2f)
                )
                Text(
                    text = descriptionGetter(it),
                    modifier = Modifier
                        .weight(5f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
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