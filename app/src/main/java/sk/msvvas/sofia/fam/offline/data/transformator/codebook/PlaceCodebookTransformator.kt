package sk.msvvas.sofia.fam.offline.data.transformator.codebook

import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.PlaceCodebookEntity
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.place.PlaceCodebookFeedXml

object PlaceCodebookTransformator {
    fun placeCodebookListFromPlaceCodebookFeed(placeCodebookFeedXml: PlaceCodebookFeedXml): List<PlaceCodebookEntity> {
        if(placeCodebookFeedXml.entries == null || placeCodebookFeedXml.entries.isEmpty())
            return emptyList()
        return placeCodebookFeedXml.entries.map { entry ->
            entry.content.place.let {
                PlaceCodebookEntity(
                    id = it.id,
                    description = it.description
                )
            }
        }
    }
}