package sk.msvvas.sofia.fam.offline.data.client.model.codebook.place

import com.thoughtworks.xstream.annotations.XStreamAlias

data class PlaceCodebookContentXml(
    @XStreamAlias("m:properties")
    val place: PlaceCodebookXml
)
