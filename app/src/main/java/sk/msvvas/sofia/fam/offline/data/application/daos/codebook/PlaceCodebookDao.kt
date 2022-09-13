package sk.msvvas.sofia.fam.offline.data.application.daos.codebook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.PlaceCodebookEntity

@Dao
interface PlaceCodebookDao {

    @Insert(onConflict = IGNORE)
    suspend fun save(place: PlaceCodebookEntity)

    @Insert(onConflict = IGNORE)
    suspend fun saveAll(places: List<PlaceCodebookEntity>)

    @Query("SELECT * FROM places_codebook")
    fun getAll(): List<PlaceCodebookEntity>

    @Query("SELECT * FROM places_codebook WHERE id = :id")
    suspend fun findById(id: String): List<PlaceCodebookEntity>

}