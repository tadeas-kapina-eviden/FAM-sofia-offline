package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InventoryDetailView(

) {
    var filter by remember {
        mutableStateOf("")
    }
    var isFiltersShow by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = filter,
                onValueChange = {
                    filter = it
                },
                modifier = Modifier
                    .weight(1f),
                maxLines = 1
            )
            Button(
                onClick = { isFiltersShow = !isFiltersShow },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Zobraziť filtre"
                )
            }
        }
        if (isFiltersShow) {
            InventoryDetailFiltersComponent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryDetailViewPreview() {
    InventoryDetailView()
}