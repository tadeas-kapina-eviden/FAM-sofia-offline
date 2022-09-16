package sk.msvvas.sofia.fam.offline.ui.views.inventory.list

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val exitModalShown by inventoryListViewModel.exitModalShown.observeAsState(false)

    val activity = (LocalContext.current as? Activity)

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(inventories) { item ->
                    InventoryListItem(
                        inventory = item,
                        onClick = {
                            inventoryListViewModel.onSelectInventory(item.id)
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

        if (exitModalShown) {
            ConfirmModalWindow(
                header = "Opúšťate aplikáciu...",
                body = "Naozaj chcete opustiť aplikáciue?",
                confirmButtonText = "Áno",
                confirmButtonAction = {
                    activity?.finish()
                },
                declineButtonText = "Nie",
                declineButtonAction = {
                    inventoryListViewModel.hideExitModalWindow()
                }
            )
        }
    }
    BackHandler {
        inventoryListViewModel.showExitModalWindow()
    }
}
