package sk.msvvas.sofia.fam.offline.data.application.model

import androidx.room.ColumnInfo

/**
 * Model for preview of Property
 * Used in Property list in Inventory detail
 * @param id id od property
 * @param textMainNumber text main number of property
 * @param propertyNumber property number of property
 * @param subNumber sub number of property
 * @param status status code of property
 */
data class PropertyPreviewModel(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "text_main_number") val textMainNumber: String,
    @ColumnInfo(name = "property_number") val propertyNumber: String,
    @ColumnInfo(name = "subnumber") val subNumber: String,
    @ColumnInfo(name = "record_status") val status: Char
)
