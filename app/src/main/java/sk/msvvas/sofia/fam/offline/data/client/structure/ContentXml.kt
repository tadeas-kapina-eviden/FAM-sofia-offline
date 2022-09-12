package sk.msvvas.sofia.fam.offline.data.client.structure

import com.thoughtworks.xstream.annotations.XStreamAlias
import sk.msvvas.sofia.fam.offline.data.client.InventoryXml

data class ContentXml(
    @XStreamAlias("m:properties")
    val inventory: InventoryXml
)
