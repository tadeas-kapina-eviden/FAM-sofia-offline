package sk.msvvas.sofia.fam.offline.data.client.model.codebook.place

import com.thoughtworks.xstream.annotations.XStreamAlias

data class PlaceCodebookXml(
    @XStreamAlias("d:Anlue")
    val id: String,
    @XStreamAlias("d:AnlueTxt")
    val description: String,
)
