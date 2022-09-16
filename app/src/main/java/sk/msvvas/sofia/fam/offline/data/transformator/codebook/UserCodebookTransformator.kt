package sk.msvvas.sofia.fam.offline.data.transformator.codebook

import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.UserCodebookEntity
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.user.UserCodebookFeedXml

/**
 * Transformator for transforming UserCodebookEntity to UserCodebookXml and back
 */
object UserCodebookTransformator {

    /**
     * Function for transforming Feed of UserCodebooks to List of UserCodebookEntities
     * @param userCodebookFeedXml xml feed of UserCodebooks
     * @return list of UserCodebookEntities
     */
    fun userCodebookListFromUserCodebookFeed(userCodebookFeedXml: UserCodebookFeedXml): List<UserCodebookEntity> {
        if(userCodebookFeedXml.entries == null || userCodebookFeedXml.entries.isEmpty())
            return emptyList()
        return userCodebookFeedXml.entries.map { entry ->
            entry.content.user.let {
                UserCodebookEntity(
                    id = it.id,
                    fullName = it.fullName
                )
            }
        }
    }
}