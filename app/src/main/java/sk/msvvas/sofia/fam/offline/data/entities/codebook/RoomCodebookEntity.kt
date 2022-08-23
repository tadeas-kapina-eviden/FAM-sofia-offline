package sk.msvvas.sofia.fam.offline.data.entities.codebook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*TODO add foreign key on LocalityCodebook*/
@Entity(tableName = "rooms_codebook")
data class RoomCodebookEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "locality_id")
    val localityId: String,

    @ColumnInfo(name = "description")
    val description: String
)
