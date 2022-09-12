package sk.msvvas.sofia.fam.offline.ui.views.inventory.list

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InventoryListView(
    inventoryListViewModel: InventoryListViewModel
) {

    val inventories by inventoryListViewModel.inventories.observeAsState(emptyList())
    val selectedInventoryId by inventoryListViewModel.selectedInventoryId.observeAsState("")
    val isDownloadConfirmShown by inventoryListViewModel.isDownloadConfirmShown.observeAsState(false)


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Vyber Inventúru",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(
                        enabled = true,
                        state = ScrollState(0)
                    )
            ) {
                inventories.forEach {
                    InventoryListItem(
                        inventory = it,
                        onClick = {
                            inventoryListViewModel.onSelectInventory(it.id)
                        }
                    )
                }
            }
        }
        if (isDownloadConfirmShown) {
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
                    value = "Naozaj chceš stiahnuť Inventúru s id: $selectedInventoryId?",
                    readOnly = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                    ),
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                )
                Row() {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { inventoryListViewModel.onSelectInventoryConfirm() }
                    ) {
                        Text(text = "Áno")
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { inventoryListViewModel.onSelectInventoryDecline() }
                    ) {
                        Text(text = "Nie")
                    }
                }
            }
        }
    }
}