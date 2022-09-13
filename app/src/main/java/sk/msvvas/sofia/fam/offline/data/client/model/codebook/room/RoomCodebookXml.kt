package sk.msvvas.sofia.fam.offline.data.client.model.codebook.room

import com.thoughtworks.xstream.annotations.XStreamAlias

data class RoomCodebookXml(
    @XStreamAlias("d:Raumn")
    val id: String,
    @XStreamAlias("d:Stort")
    val localityId: String,
    @XStreamAlias("d:Descr")
    val description: String,
)
