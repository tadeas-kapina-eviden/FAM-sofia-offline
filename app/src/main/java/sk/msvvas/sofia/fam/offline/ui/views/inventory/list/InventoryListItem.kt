package sk.msvvas.sofia.fam.offline.ui.views.inventory.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.ui.theme.FAMInventuraOfflineClientTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun InventoryListItem(
    inventory: InventoryEntity,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                onClick()
            }
            .background(
                color = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        ) {
            Text(
                text = inventory.id + " - " + inventory.note,
                style = MaterialTheme.typography.body2.copy(
                ),
                color = MaterialTheme.colors.primary,
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = MaterialTheme.typography.body2.toSpanStyle()) {
                        append("Dátum:\t")
                    }
                    append(
                        LocalDateTime.parse(
                            inventory.createdAt,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                        ).format(
                            DateTimeFormatter.ofPattern("dd.MM.yyyy")
                        )
                    )
                },
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = MaterialTheme.typography.body2.toSpanStyle()) {
                        append("Založené:\t")
                    }
                    append(inventory.createdBy)
                },
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = MaterialTheme.typography.body2.toSpanStyle()) {
                        append("Spracované/Celkom: \t")
                    }
                    append("${inventory.countProcessed}/${inventory.countAll}")
                },
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
        }
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun InventoryListItemPreview() {
    FAMInventuraOfflineClientTheme {
        InventoryListItem(
            InventoryEntity(
                id = "350",
                note = "UCJ nejaky fakt ze extremne dlhy popis na skusku",
                createdAt = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
                createdBy = "110SEVCOVA",
                countProcessed = 44,
                countAll = 44
            ),
            onClick = {

            }
        )
    }
}