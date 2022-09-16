package sk.msvvas.sofia.fam.offline.data.transformator.codebook

import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.locality.LocalityCodebookFeedXml

/**
 * Transformator for transforming LocalityCodebookEntity to LocalityCodebookXml and back
 */
object LocalityCodebookTransformator {

    /**
     * Function for transforming Feed of LocalityCodebooks to List of LocalityCodebookEntities
     * @param localityCodebookFeedXml xml feed of LocalityCodebooks
     * @return list of LocalityCodebookEntities
     */
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