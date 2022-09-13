package sk.msvvas.sofia.fam.offline.data.client.model.codebook.note

import com.thoughtworks.xstream.annotations.XStreamAlias

data class NoteCodebookXml(
    @XStreamAlias("d:Xnote")
    val id: String,
    @XStreamAlias("d:Ktext")
    val description: String,
    @XStreamAlias("d:Flagd")
    val flagged: Boolean,
)
