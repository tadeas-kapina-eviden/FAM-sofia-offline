package sk.msvvas.sofia.fam.offline.data.transformator

import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.client.model.inventory.InventoryFeedXml
import sk.msvvas.sofia.fam.offline.data.client.model.submit.InventoryModelJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Transformator for transforming InventoryEntity to InventoryXml and back
 */
object InventoryTransformator {

    /**
     * Function for transforming Feed of Inventories to List of InventoryEntities
     * @param inventoryFeedXml xml feed of Inventories
     * @return list of InventoryEntities
     */
    fun inventoryListFromInventoryFeed(inventoryFeedXml: InventoryFeedXml): List<InventoryEntity> {
        if (inventoryFeedXml.entries == null || inventoryFeedXml.entries.isEmpty())
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

    fun inventoryEntityToInventoryModelJson(
        inventoryEntity: InventoryEntity,
        properties: List<PropertyEntity>
    ): InventoryModelJson {
        return InventoryModelJson(
            id = inventoryEntity.id,
            createdAt = inventoryEntity.createdAt,
            createdAtFormatted = LocalDateTime.parse(
                inventoryEntity.createdAt,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            ).format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy")
            ),
            createdBy = inventoryEntity.createdBy,
            note = inventoryEntity.note,
            properties = properties.map {
                PropertyTransformator.propertyEntityToPropertyModelJson(it)
            }
        )
    }
}