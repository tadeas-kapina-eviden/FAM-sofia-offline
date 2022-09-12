package sk.msvvas.sofia.fam.offline.data.application.entities.codebook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "rooms_codebook",
    foreignKeys = [ForeignKey(
        entity = LocalityCodebookEntity::class,
        parentColumns = ["id"],
        childColumns = ["locality_id"]
    )]
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
