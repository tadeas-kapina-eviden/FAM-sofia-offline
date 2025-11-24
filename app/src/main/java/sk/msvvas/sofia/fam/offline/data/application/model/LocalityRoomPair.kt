package sk.msvvas.sofia.fam.offline.data.application.model

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.Companion.INTEGER
data class LocalityRoomPair(
    @ColumnInfo(name = "locality")
    val locality: String,
    @ColumnInfo(name = "room")
    val room: String,
    @ColumnInfo(name = "processed", typeAffinity = INTEGER)
    var processed: Boolean
)
