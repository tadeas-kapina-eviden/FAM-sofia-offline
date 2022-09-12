package sk.msvvas.sofia.fam.offline.data.client.structure

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamImplicit


@XStreamAlias("feed")
data class FeedXml(
    var id: String,
    var updated: String,
    var title: String,
    var author: AuthorXml,
    val link: String,
    @XStreamImplicit(itemFieldName = "entry")
    val entries: List<EntryXml>
)