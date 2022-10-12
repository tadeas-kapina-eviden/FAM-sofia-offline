package sk.msvvas.sofia.fam.offline.data.application.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "server_url")
data class ServerUrlEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "url")
    val url: String
)
