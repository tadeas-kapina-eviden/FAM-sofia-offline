package sk.msvvas.sofia.fam.offline.data.entities.codebook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localities_codebook")
data class LocalityCodebookEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "flagged")
    val flagged : Boolean
)
