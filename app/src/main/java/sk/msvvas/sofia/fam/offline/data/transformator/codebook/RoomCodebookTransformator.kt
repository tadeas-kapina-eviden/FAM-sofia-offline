package sk.msvvas.sofia.fam.offline.data.transformator.codebook

import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.RoomCodebookEntity
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.room.RoomCodebookFeedXml

/**
 * Transformator for transforming RoomCodebookEntity to RoomCodebookXml and back
 */
object RoomCodebookTransformator {

    /**
     * Function for transforming Feed of RoomCodebooks to List of RoomCodebookEntities
     * @param roomCodebookFeedXml xml feed of RoomCodebooks
     * @return list of RoomCodebookEntities
     */
    fun roomCodebookListFromRoomCodebookFeed(roomCodebookFeedXml: RoomCodebookFeedXml): List<RoomCodebookEntity> {
        if(roomCodebookFeedXml.entries == null || roomCodebookFeedXml.entries.isEmpty())
            return emptyList()
        return roomCodebookFeedXml.entries.map { entry ->
            entry.content.room.let {
                RoomCodebookEntity(
                    id = 0,
                    name = it.id,
                    localityId = it.localityId,
                    description = it.description
                )
            }
        }
    }
}