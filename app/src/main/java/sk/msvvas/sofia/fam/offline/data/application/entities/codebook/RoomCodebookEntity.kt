package sk.msvvas.sofia.fam.offline.data.application.entities.codebook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity for rooms_codebook table in local database
 */
@Entity(
    tableName = "rooms_codebook",
    indices = [
        Index(value = ["id"], unique = true)
    ]
)
data class RoomCodebookEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "locality_id")
    var localityId: String,

    @ColumnInfo(name = "description")
    val description: String
)
