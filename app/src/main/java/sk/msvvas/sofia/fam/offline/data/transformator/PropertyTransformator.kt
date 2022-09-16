package sk.msvvas.sofia.fam.offline.data.transformator

import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.application.model.PropertyPreviewModel
import sk.msvvas.sofia.fam.offline.data.client.model.property.PropertyFeedXml

/**
 * Transformator for transforming PropertyEntity to PropertyXml and back
 */
object PropertyTransformator {

    /**
     * Function for transforming Feed of Properties to List of PropertyEntities
     * @param propertyFeedXml xml feed of Properties
     * @return list of PropertyEntities
     */
    fun propertyListFromPropertyFeed(propertyFeedXml: PropertyFeedXml): List<PropertyEntity> {
        if(propertyFeedXml.entries == null || propertyFeedXml.entries.isEmpty())
            return emptyList()
        return propertyFeedXml.entries.map { entry ->
            entry.content.property.let {
                PropertyEntity(
                    inventoryId = it.inventoryId,
                    inventNumber = it.inventNumber,
                    serialNumber = it.serialNumber,
                    client = it.client,
                    propertyNumber = it.propertyNumber,
                    subnumber = it.subNumber,
                    textMainNumber = it.textMainNumberIm,
                    recordStatus = it.recordStatus[0],
                    werks = it.werks,
                    locality = it.locality,
                    localityNew = it.localityNew,
                    room = it.room,
                    roomNew = it.roomNew,
                    personalNumber = it.personalNumber,
                    personalNumberNew = it.personalNumberNew,
                    center = it.center,
                    centerNew = it.centerNew,
                    workplace = it.workplace,
                    workplaceNew = it.workplaceNew,
                    fixedNote = it.fixedNote,
                    variableNote = it.variableNote
                )
            }
        }
    }

    fun propertyEntityListToPropertyPreviewList(propertyEntities: List<PropertyEntity>): List<PropertyPreviewModel> {
        return propertyEntities.map {
            PropertyPreviewModel(
                id = it.id,
                textMainNumber = it.textMainNumber,
                status = it.recordStatus,
                subNumber = it.subnumber,
                propertyNumber = it.propertyNumber
            )
        }
    }
}