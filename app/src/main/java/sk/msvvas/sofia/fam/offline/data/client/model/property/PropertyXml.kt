package sk.msvvas.sofia.fam.offline.data.client.model.property

import com.thoughtworks.xstream.annotations.XStreamAlias

data class PropertyXml(
    @XStreamAlias("d:Inven")
    val inventoryId: String,
    @XStreamAlias("d:Invnr")
    val inventNumber: String,
    @XStreamAlias("d:Sernr")
    val serialNumber: String,
    @XStreamAlias("d:Zstattext")
    val statusText: String,
    @XStreamAlias("d:Mandt")
    val client: String,
    @XStreamAlias("d:Bukrs")
    val accountingCircle: String,
    @XStreamAlias("d:Anln1")
    val propertyNumber: String,
    @XStreamAlias("d:Anln2")
    val subNumber: String,
    @XStreamAlias("d:Txt50")
    val textMainNumberIm: String,
    @XStreamAlias("d:Zstat")
    val recordStatus: String,
    @XStreamAlias("d:Werks")
    val werks: String,
    @XStreamAlias("d:Werksn")
    val werksNew: String,
    @XStreamAlias("d:Stort")
    val locality: String,
    @XStreamAlias("d:Stortn")
    val localityNew: String,
    @XStreamAlias("d:Raumn")
    val room: String,
    @XStreamAlias("d:Raumnn")
    val roomNew: String,
    @XStreamAlias("d:Pernr")
    val personalNumber: String,
    @XStreamAlias("d:Pernrn")
    val personalNumberNew: String,
    @XStreamAlias("d:Kostl")
    val center: String,
    @XStreamAlias("d:Kostln")
    val centerNew: String,
    @XStreamAlias("d:Anlue")
    val workplace: String,
    @XStreamAlias("d:Anluen")
    val workplaceNew: String,
    @XStreamAlias("d:Mengeo")
    val quantity: String,
    @XStreamAlias("d:Mengen")
    val quantityNew: String,
    @XStreamAlias("d:Xnote")
    val fixedNote: String,
    @XStreamAlias("d:Fnote")
    val variableNote: String,
    @XStreamAlias("d:Manua")
    val isManual: String,
)
