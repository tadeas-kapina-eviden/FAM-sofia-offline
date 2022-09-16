package sk.msvvas.sofia.fam.offline.data.transformator

import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.client.model.inventory.InventoryFeedXml

object InventoryTransformator {

    fun inventoryListFromInventoryFeed(inventoryFeedXml: InventoryFeedXml): List<InventoryEntity> {
        if(inventoryFeedXml.entries == null || inventoryFeedXml.entries.isEmpty())
            return emptyList()
        return inventoryFeedXml.entries.map { entry ->
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