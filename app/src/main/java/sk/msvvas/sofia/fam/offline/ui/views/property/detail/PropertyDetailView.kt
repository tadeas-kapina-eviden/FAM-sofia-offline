package sk.msvvas.sofia.fam.offline.ui.views.property.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.ui.components.drawWithBottomLine


//TODO add other fields
@Composable
fun PropertyDetailView(
    propertyDetailViewModel: PropertyDetailViewModel
) {

    val property by propertyDetailViewModel.property.observeAsState(
        PropertyEntity()
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {
        TextField(
            value = "Podrobnosti: " + property.propertyNumber + "/" + property.subnumber,
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
            value = property.localityNew,
            onClick = {/*TODO*/ }
        )
        InputRow(
            label = "Miestnosť",
            value = property.roomNew,
            onClick = {/*TODO*/ }
        )
        InputRow(
            label = "z. Os.",
            value = property.personalNumberNew,
            onClick = {/*TODO*/ }
        )
        InputRow(
            label = "Stredisko",
            value = property.center,
            onClick = {}
        )
        InputRow(
            label = "Pracovisko",
            value = property.workplaceNew,
            onClick = {/*TODO*/ }
        )
        InputRow(
            label = "Poznámka",
            value = property.fixedNote,
            onClick = {/*TODO*/ }
        )
        InputRow(
            label = "Vlastná pozn.",
            value = property.variableNote,
            onClick = {/*TODO*/ }
        )
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
            readOnly = true,
            onValueChange = {},
            modifier = Modifier
                .weight(5f)
                .clickable {
                    onClick()
                },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background
            ),
            textStyle = LocalTextStyle.current
                .copy(textAlign = TextAlign.End)
        )
    }
}