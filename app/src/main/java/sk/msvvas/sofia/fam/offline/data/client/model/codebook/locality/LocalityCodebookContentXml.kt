package sk.msvvas.sofia.fam.offline.data.client.model.codebook.locality

import com.thoughtworks.xstream.annotations.XStreamAlias

data class LocalityCodebookContentXml(
    @XStreamAlias("m:properties")
    val locality: LocalityCodebookXml
)
