package sk.msvvas.sofia.fam.offline.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "inventories")
data class InventoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "created_at")
    var createdAt: String,

    @ColumnInfo(name = "created_by")
    var created_by: String,

    @ColumnInfo(name = "note")
    var note: String,

    @ColumnInfo(name = "count_processed")
    var countProcessed: Int,

    @ColumnInfo(name = "count_all")
    var countAll: Int
)