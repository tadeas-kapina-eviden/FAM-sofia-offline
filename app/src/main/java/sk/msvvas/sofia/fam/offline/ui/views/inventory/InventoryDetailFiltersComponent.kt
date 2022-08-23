package sk.msvvas.sofia.fam.offline.ui.views.inventory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InventoryDetailFiltersComponent() {
    var location by remember {
        mutableStateOf("")
    }

    var room by remember {
        mutableStateOf("")
    }

    var user by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            Text(
                text = "Lokalita:",
                modifier = Modifier
                    .weight(2f)
                    .padding(15.dp),
                textAlign = TextAlign.End
            )
            TextField(
                value = location,
                onValueChange = {
                    location = it
                },
                modifier = Modifier
                    .weight(3f),

                )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            Text(
                text = "Miestnosť:",
                modifier = Modifier
                    .weight(2f)
                    .padding(15.dp),
                textAlign = TextAlign.End
            )
            TextField(
                value = room,
                onValueChange = {
                    room = it
                },
                modifier = Modifier
                    .weight(3f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            Text(
                text = "Osoba:",
                modifier = Modifier
                    .weight(2f)
                    .padding(15.dp),
                textAlign = TextAlign.End
            )
            TextField(
                value = user,
                onValueChange = {
                    user = it
                },
                modifier = Modifier
                    .weight(3f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(
                text = "Sken bez detailu položky:",
                modifier = Modifier
                    .weight(2f)
                    .padding(15.dp),
                textAlign = TextAlign.End
            )
            Row(
                modifier = Modifier
                    .weight(3f)
            ) {
                Button(
                    onClick = { /*TODO*/ },
                ) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "dropdown icon",
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
            ) {
                Text(text = "Spustiť filtre")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryDetailFiltersComponentPreview() {
    InventoryDetailFiltersComponent()
}