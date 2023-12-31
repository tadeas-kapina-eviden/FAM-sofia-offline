package sk.msvvas.sofia.fam.offline.data.application.entities.codebook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity for localities_codebook table in local database
 */
@Entity(
    tableName = "localities_codebook",
    indices = [
        Index(value = ["id"], unique = true)
    ]
)
data class LocalityCodebookEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "description")
    val description: String
)
