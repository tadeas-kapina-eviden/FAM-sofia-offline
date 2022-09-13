package sk.msvvas.sofia.fam.offline.data.client.model.property

import com.thoughtworks.xstream.annotations.XStreamAlias

data class PropertyContentXml(
    @XStreamAlias("m:properties")
    val property: PropertyXml
)
