package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.application.model.LocalityRoomCountPair

@Composable
fun InventoryDetailStatusView(
    inventoryDetailViewModel: InventoryDetailViewModel,
) {
    val properties by inventoryDetailViewModel.properties.observeAsState(emptyList())
    val localityRoomPairs = countLocalityRoomPairs(properties)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = "Lokalita",
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "Miestnosť",
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "Spracované/celkom",
                modifier = Modifier
                    .weight(1f)
            )
        }
        localityRoomPairs.forEach {
            StatusRow(
                localityRoomCountPair = it,
                onSelect = { loc, room ->
                    inventoryDetailViewModel.onLocalityRoomStatusSelect(
                        loc,
                        room
                    )
                })
        }
    }

}

@Composable
fun StatusRow(
    localityRoomCountPair: LocalityRoomCountPair,
    onSelect: (String, String) -> Unit
) {
    val processed = localityRoomCountPair.processed
    val all = localityRoomCountPair.all
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onSelect(localityRoomCountPair.locality, localityRoomCountPair.room)
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onSelect(localityRoomCountPair.locality, localityRoomCountPair.room)
                }
                .padding(15.dp)
        ) {
            Text(
                text = localityRoomCountPair.locality,
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = localityRoomCountPair.room,
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "$processed/$all",
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

fun countLocalityRoomPairs(properties: List<PropertyEntity>): List<LocalityRoomCountPair> {
    val result = mutableListOf<LocalityRoomCountPair>()
    properties.forEach {
        val pair = LocalityRoomCountPair(it.localityNew, it.roomNew)
        if (pair in result) {
            val toAdd = result[result.indexOf(pair)]
            toAdd.all++
            toAdd.processed += if (it.recordStatus == 'Z' || it.recordStatus == 'S') 1 else 0
        } else {
            pair.all = 1
            pair.processed = if (it.recordStatus == 'Z' || it.recordStatus == 'S') 1 else 0
            result.add(pair)
        }
    }
    return result
}