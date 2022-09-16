package sk.msvvas.sofia.fam.offline.data.transformator.codebook

import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.locality.LocalityCodebookFeedXml

object LocalityCodebookTransformator {
    fun localityCodebookListFromLocalityCodebookFeed(localityCodebookFeedXml: LocalityCodebookFeedXml): List<LocalityCodebookEntity> {
        if(localityCodebookFeedXml.entries == null || localityCodebookFeedXml.entries.isEmpty())
            return emptyList()
        return localityCodebookFeedXml.entries.map { entry ->
            entry.content.locality.let {
                LocalityCodebookEntity(
                    id = it.id,
                    description = it.description
                )
            }
        }
    }
}