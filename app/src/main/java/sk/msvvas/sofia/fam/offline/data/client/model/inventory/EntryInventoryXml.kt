package sk.msvvas.sofia.fam.offline.data.client.model.inventory

data class EntryInventoryXml(
    var id: String,
    var updated: String,
    var title: String,
    var category: String,
    val link: String,
    val content: ContentInventoryXml
)
