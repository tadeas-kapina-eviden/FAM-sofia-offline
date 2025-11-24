package sk.msvvas.sofia.fam.offline.data.application.entities.codebook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity for rooms_codebook table in local database
 */
@Entity(
    tableName = "rooms_codebook"
)
data class RoomCodebookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "locality_id")
    var localityId: String,

    @ColumnInfo(name = "description")
    val description: String
)
