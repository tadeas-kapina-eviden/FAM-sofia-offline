package sk.msvvas.sofia.fam.offline.data.application.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_data",
    indices = [
        Index(value = ["id"], unique = true)
    ]
)
data class UserDataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "login")
    var login: String,

    @ColumnInfo(name = "client")
    var client: String
)