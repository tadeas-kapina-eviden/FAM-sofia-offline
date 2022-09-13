package sk.msvvas.sofia.fam.offline.data.client.model.property

data class PropertyEntryXml(
    var id: String,
    var updated: String,
    var title: String,
    var category: String,
    val link: String,
    val content: PropertyContentXml
)
