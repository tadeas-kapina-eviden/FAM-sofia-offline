package sk.msvvas.sofia.fam.offline.data.client.model.inventory

import com.thoughtworks.xstream.annotations.XStreamAlias

data class InventoryContentXml(
    @XStreamAlias("m:properties")
    val inventory: InventoryXml
)
