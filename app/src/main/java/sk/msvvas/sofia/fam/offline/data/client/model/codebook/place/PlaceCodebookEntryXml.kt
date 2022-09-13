package sk.msvvas.sofia.fam.offline.data.client.model.codebook.place

data class PlaceCodebookEntryXml(
    var id: String,
    var updated: String,
    var title: String,
    var category: String,
    val link: String,
    val content: PlaceCodebookContentXml
)
