package sk.msvvas.sofia.fam.offline.data.client.model.inventory

import com.thoughtworks.xstream.annotations.XStreamAlias

data class InventoryXml(
    @XStreamAlias("d:Inven")
    val id: String,
    @XStreamAlias("d:Datin")
    val date: String,
    @XStreamAlias("d:Datinf")
    val dateFormatted: String,
    @XStreamAlias("d:Xubname")
    val personalNumber: String,
    @XStreamAlias("d:Ipozn")
    val note: String,
    @XStreamAlias("d:Text20")
    val counts: String
)
