package sk.msvvas.sofia.fam.offline.data.client.model.transformator

import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.client.model.inventory.FeedInventoryXml

object InventoryTransformator {

    fun inventoryListFromInventoryFeed(feedInventoryXml: FeedInventoryXml): List<InventoryEntity> {
        return feedInventoryXml.entries.map { entry ->
            entry.content.inventory.let {
                InventoryEntity(
                    id = it.id,
                    createdAt = it.date,
                    createdBy = it.personalNumber,
                    note = it.note,
                    countAll = it.counts.split("/")[1].toInt(),
                    countProcessed = it.counts.split("/")[0].toInt(),
                )
            }
        }
    }
}