package sk.msvvas.sofia.fam.offline.data.client.model.codebook.place

import sk.msvvas.sofia.fam.offline.data.client.model.Author
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamImplicit


@XStreamAlias("feed")
data class PlaceCodebookFeedXml(
    var id: String,
    var updated: String,
    var title: String,
    var author: Author,
    val link: String,
    @XStreamImplicit(itemFieldName = "entry")
    val entries: List<PlaceCodebookEntryXml>
)