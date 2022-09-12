package sk.msvvas.sofia.fam.offline.data.client.structure

data class EntryXml(
    var id: String,
    var updated: String,
    var title: String,
    var category: String,
    val link: String,
    val content: ContentXml
)
