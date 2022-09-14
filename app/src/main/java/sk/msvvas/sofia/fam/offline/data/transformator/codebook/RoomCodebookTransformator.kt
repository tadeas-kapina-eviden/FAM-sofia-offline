package sk.msvvas.sofia.fam.offline.data.transformator.codebook

import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.RoomCodebookEntity
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.room.RoomCodebookFeedXml

object RoomCodebookTransformator {
    fun roomCodebookListFromRoomCodebookFeed(roomCodebookFeedXml: RoomCodebookFeedXml): List<RoomCodebookEntity> {
        return roomCodebookFeedXml.entries.map { entry ->
            entry.content.room.let {
                RoomCodebookEntity(
                    id = it.id,
                    localityId = it.localityId,
                    description = it.description
                )
            }
        }
    }
}