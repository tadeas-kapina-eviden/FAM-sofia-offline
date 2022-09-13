package sk.msvvas.sofia.fam.offline.data.client.model.property

data class EntryPropertyXml(
    var id: String,
    var updated: String,
    var title: String,
    var category: String,
    val link: String,
    val content: ContentPropertyXml
)
