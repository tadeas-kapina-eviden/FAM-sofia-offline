package sk.msvvas.sofia.fam.offline.data.entities.codebook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_codebook")
data class UserCodebookEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "full_name")
    val fullName: String
)
