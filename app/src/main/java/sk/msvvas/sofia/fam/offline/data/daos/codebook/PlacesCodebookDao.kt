package sk.msvvas.sofia.fam.offline.data.daos.codebook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.entities.codebook.PlacesCodebookEntity

@Dao
interface PlacesCodebookDao {

    @Insert(onConflict = IGNORE)
    suspend fun save(place: PlacesCodebookEntity)

    @Insert(onConflict = IGNORE)
    suspend fun saveAll(places: List<PlacesCodebookEntity>)

    @Query("SELECT * FROM places_codebook")
    fun getAll(): List<PlacesCodebookEntity>

    @Query("SELECT * FROM places_codebook WHERE id = :id")
    suspend fun findById(id: String): List<PlacesCodebookEntity>

}