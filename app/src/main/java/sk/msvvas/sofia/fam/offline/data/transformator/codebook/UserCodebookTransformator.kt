package sk.msvvas.sofia.fam.offline.data.transformator.codebook

import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.UserCodebookEntity
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.user.UserCodebookFeedXml

object UserCodebookTransformator {
    fun userCodebookListFromUserCodebookFeed(userCodebookFeedXml: UserCodebookFeedXml): List<UserCodebookEntity> {
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