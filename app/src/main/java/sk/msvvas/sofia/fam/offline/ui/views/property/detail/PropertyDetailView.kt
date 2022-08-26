package sk.msvvas.sofia.fam.offline.ui.views.property.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                .fillMaxWidth()
        )
        TextField(
            value = property.textMainNumber,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithBottomLine(
                    width = 1f,
                    color = MaterialTheme.colors.primary
                )
        ) {
            Text(
                text = "Sériové číslo",
                modifier = Modifier.weight(2f)
            )
            TextField(
                value = property.serialNumber,
                readOnly = true,
                onValueChange = {}
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithBottomLine(
                    width = 1f,
                    color = MaterialTheme.colors.primary
                )
        ) {
            Text(
                text = "Invent číslo",
                modifier = Modifier.weight(2f)
            )
            TextField(
                value = property.inventNumber,
                readOnly = true,
                onValueChange = {}
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithBottomLine(
                    width = 1f,
                    color = MaterialTheme.colors.primary
                )
        ) {
            Text(
                text = "Závod",
                modifier = Modifier.weight(2f)
            )
            TextField(
                value = property.werks,
                readOnly = true,
                onValueChange = {}
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithBottomLine(
                    width = 1f,
                    color = MaterialTheme.colors.primary
                )
        ) {
            Text(
                text = "Lokalita",
                modifier = Modifier.weight(2f)
            )
            TextField(
                value = property.werks,
                readOnly = true,
                onValueChange = {}
            )
        }


    }
}