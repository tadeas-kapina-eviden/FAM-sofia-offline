package sk.msvvas.sofia.fam.offline.data.application.entities.codebook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for user_codebook table in local database
 */
@Entity(tableName = "user_codebook")
data class UserCodebookEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "full_name")
    val fullName: String
)
