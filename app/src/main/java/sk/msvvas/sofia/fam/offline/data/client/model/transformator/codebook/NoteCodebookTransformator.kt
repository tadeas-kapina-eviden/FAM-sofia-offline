package sk.msvvas.sofia.fam.offline.data.client.model.transformator.codebook

import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.NoteCodebookEntity
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.note.NoteCodebookFeedXml

object NoteCodebookTransformator {
    fun noteCodebookListFromNoteCodebookFeed(noteCodebookFeedXml: NoteCodebookFeedXml): List<NoteCodebookEntity> {
        return noteCodebookFeedXml.entries.map { entry ->
            entry.content.note.let {
                NoteCodebookEntity(
                    id = it.id,
                    description = it.description,
                    flagged = it.flagged
                )
            }
        }
    }
}