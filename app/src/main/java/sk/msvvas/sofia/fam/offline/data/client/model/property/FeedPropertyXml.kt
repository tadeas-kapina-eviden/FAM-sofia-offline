package sk.msvvas.sofia.fam.offline.data.client.model.property

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamImplicit
import sk.msvvas.sofia.fam.offline.data.client.model.Author

@XStreamAlias("feed")
data class FeedPropertyXml(
    var id: String,
    var updated: String,
    var title: String,
    var author: Author,
    val link: String,
    @XStreamImplicit(itemFieldName = "entry")
    val entries: List<EntryPropertyXml>
)