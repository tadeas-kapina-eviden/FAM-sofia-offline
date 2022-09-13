package sk.msvvas.sofia.fam.offline.data.client.model.codebook.room

import com.thoughtworks.xstream.annotations.XStreamAlias

data class RoomCodebookContentXml(
    @XStreamAlias("m:properties")
    val room: RoomCodebookXml
)
