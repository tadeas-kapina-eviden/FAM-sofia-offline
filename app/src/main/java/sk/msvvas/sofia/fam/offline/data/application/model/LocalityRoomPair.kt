package sk.msvvas.sofia.fam.offline.data.application.model

import androidx.room.ColumnInfo

data class LocalityRoomPair(
    @ColumnInfo(name = "locality")
    val locality: String,
    @ColumnInfo(name = "room")
    val room: String,
    @ColumnInfo(name = "processed")
    var processed: Boolean
)
