package sk.msvvas.sofia.fam.offline.data.application.entities.codebook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places_codebook")
data class PlacesCodebookEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "description")
    val description: String
)
