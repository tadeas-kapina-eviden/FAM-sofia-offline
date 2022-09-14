package sk.msvvas.sofia.fam.offline.ui.views.inventory.list

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.ui.components.ConfirmModalWindow
import sk.msvvas.sofia.fam.offline.ui.components.InformationNonClosableModalWindow

@Composable
fun InventoryListView(
    inventoryListViewModel: InventoryListViewModel
) {

    val inventories by inventoryListViewModel.inventories.observeAsState(emptyList())
    val selectedInventoryId by inventoryListViewModel.selectedInventoryId.observeAsState("")
    val isDownloadConfirmShown by inventoryListViewModel.isDownloadConfirmShown.observeAsState(false)
    val downloadingData by inventoryListViewModel.downloadingData.observeAsState(false)

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
        if (downloadingData) {
            InformationNonClosableModalWindow(
                header = "Načítavanie",
                body = "Sťahujú sa dáta, prosím počkajte",
            )
        }
        if (isDownloadConfirmShown) {
            ConfirmModalWindow(
                header = "Naozaj chceš stiahnuť Inventúru s id: $selectedInventoryId?",
                body = "Po stiahnutí nebudeš mať možnosť pracovať s ostatnými inventúrami, až kým túto neodošleš.",
                confirmButtonText = "Áno",
                confirmButtonAction = { inventoryListViewModel.onSelectInventoryConfirm() },
                declineButtonText = "Nie",
                declineButtonAction = { inventoryListViewModel.onSelectInventoryDecline() }
            )
        }
    }
}
