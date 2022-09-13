package sk.msvvas.sofia.fam.offline.data.client.model.transformator

import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.client.model.property.FeedPropertyXml

object PropertyTransformator {
    fun propertyListFromPropertyFeed(feedPropertyXml: FeedPropertyXml): List<PropertyEntity> {
        return feedPropertyXml.entries.map { entry ->
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
}