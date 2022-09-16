package sk.msvvas.sofia.fam.offline.data.application.model

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
    val id: Long,
    val textMainNumber: String,
    val propertyNumber: String,
    val subNumber: String,
    val status: Char
)
