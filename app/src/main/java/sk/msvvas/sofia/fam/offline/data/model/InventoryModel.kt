package sk.msvvas.sofia.fam.offline.data.model

import java.time.LocalDate

data class InventoryModel(
    val id : String,
    val createdAt : LocalDate,
    val createdBy : String,
    var note : String,
    var countProcessed : Int,
    var countAll : Int
)
