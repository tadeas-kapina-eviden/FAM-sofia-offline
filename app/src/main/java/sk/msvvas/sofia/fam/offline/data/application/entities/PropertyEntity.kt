package sk.msvvas.sofia.fam.offline.data.application.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/**
 * Entity for properties table in local database
 */
@Entity(
    tableName = "properties",
    indices = [
        Index(value = ["id"], unique = true),
        Index(value = ["inventory_id"], unique = false),
        Index(value = ["record_status"], unique = false),
        Index(value = ["property_number", "subnumber"], unique = true),
    ]
)
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "inventory_id")
    var inventoryId: String = "",

    @ColumnInfo(name = "invent_number")
    var inventNumber: String = "",

    @ColumnInfo(name = "serial_number")
    var serialNumber: String = "",

    @ColumnInfo(name = "client")
    var client: String = "",

    @ColumnInfo(name = "property_number")
    var propertyNumber: String = "",

    @ColumnInfo(name = "subnumber")
    var subnumber: String = "",

    @ColumnInfo(name = "text_main_number")
    var textMainNumber: String = "",

    @ColumnInfo(name = "record_status")
    var recordStatus: Char = 'X',

    @ColumnInfo(name = "werks")
    var werks: String = "",

    @ColumnInfo(name = "locality")
    var locality: String = "",

    @ColumnInfo(name = "locality_new")
    var localityNew: String = "",

    @ColumnInfo(name = "room")
    var room: String = "",

    @ColumnInfo(name = "room_new")
    var roomNew: String = "",

    @ColumnInfo(name = "personal_number")
    var personalNumber: String = "",

    @ColumnInfo(name = "personal_number_new")
    var personalNumberNew: String = "",

    @ColumnInfo(name = "center")
    var center: String = "",

    @ColumnInfo(name = "center_new")
    var centerNew: String = "",

    @ColumnInfo(name = "workplace")
    var workplace: String = "",

    @ColumnInfo(name = "workplace_new")
    var workplaceNew: String = "",

    @ColumnInfo(name = "fixed_note")
    var fixedNote: String = "",

    @ColumnInfo(name = "variable_note")
    var variableNote: String = "",

    @ColumnInfo(name = "is_manual")
    var isManual: Boolean = false,

    @ColumnInfo(name = "is_saved")
    var isSaved: Boolean = false,

    @ColumnInfo(name = "isNew")
    var isNew: Boolean = false
)