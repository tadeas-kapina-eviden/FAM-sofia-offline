package sk.msvvas.sofia.fam.offline.data.client.model.property

import com.thoughtworks.xstream.annotations.XStreamAlias

data class ContentPropertyXml(
    @XStreamAlias("m:properties")
    val property: PropertyXml
)
