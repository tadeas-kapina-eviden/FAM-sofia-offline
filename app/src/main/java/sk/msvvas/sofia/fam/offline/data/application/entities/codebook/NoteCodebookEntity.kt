package sk.msvvas.sofia.fam.offline.data.application.entities.codebook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity for notes_codebook table in local database
 */
@Entity(
    tableName = "notes_codebook",
    indices = [
        Index(value = ["id"], unique = true)
    ]
)
data class NoteCodebookEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "flagged")
    val flagged: Boolean
)
