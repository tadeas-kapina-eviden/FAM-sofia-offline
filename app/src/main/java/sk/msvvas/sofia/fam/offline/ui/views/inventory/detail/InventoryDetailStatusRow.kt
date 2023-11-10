package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.data.application.model.LocalityRoomCountPair

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
