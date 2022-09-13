package sk.msvvas.sofia.fam.offline.data.client.model.codebook.note

import com.thoughtworks.xstream.annotations.XStreamAlias

data class NoteCodebookContentXml(
    @XStreamAlias("m:properties")
    val note: NoteCodebookXml
)
