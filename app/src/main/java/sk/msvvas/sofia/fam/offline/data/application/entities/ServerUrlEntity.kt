package sk.msvvas.sofia.fam.offline.data.application.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity for server url saved in local databse
 */
@Entity(
    tableName = "server_url"
)
data class ServerUrlEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "url")
    val url: String
)
