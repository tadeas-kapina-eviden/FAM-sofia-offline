package sk.msvvas.sofia.fam.offline.data.client.model.codebook.locality

import com.thoughtworks.xstream.annotations.XStreamAlias

data class LocalityCodebookXml(
    @XStreamAlias("d:Stort")
    val id: String,
    @XStreamAlias("d:Ktext")
    val description: String,
)
