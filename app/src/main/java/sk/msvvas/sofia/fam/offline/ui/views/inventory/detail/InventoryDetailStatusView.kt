package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
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
                    .weight(1f),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Miestnosť",
                modifier = Modifier
                    .weight(1f),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Spracované/celkom",
                modifier = Modifier
                    .weight(1f),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1
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
        .padding(bottom = 2.dp)
        .fillMaxWidth()
        .clickable {
            onSelect(localityRoomCountPair.locality, localityRoomCountPair.room)
        }
        .border(
            width = 1.dp,
            color = MaterialTheme.colors.primary,
            shape = RoundedCornerShape(8.dp)
        )
        .background(color = MaterialTheme.colors.secondary, shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onSelect(localityRoomCountPair.locality, localityRoomCountPair.room)
                }
                .padding(top = 1.dp, bottom = 1.dp, start = 15.dp)
        ) {
            Text(
                text = localityRoomCountPair.locality,
                modifier = Modifier
                    .weight(1f),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = localityRoomCountPair.room,
                modifier = Modifier
                    .weight(1f),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "$processed/$all",
                modifier = Modifier
                    .weight(1f),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

fun countLocalityRoomPairs(properties: List<PropertyEntity>): List<LocalityRoomCountPair> {
    val result = mutableListOf<LocalityRoomCountPair>()
    properties.forEach {
        val pair = LocalityRoomCountPair(
            if (it.recordStatus == 'Z' || it.recordStatus == 'S') it.localityNew else it.locality,
            if (it.recordStatus == 'Z' || it.recordStatus == 'S') it.roomNew else it.room,
        )
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