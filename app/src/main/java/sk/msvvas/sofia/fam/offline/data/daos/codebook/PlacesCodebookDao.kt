package sk.msvvas.sofia.fam.offline.data.daos.codebook

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.entities.codebook.PlacesCodebookEntity

@Dao
interface PlacesCodebookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(user: PlacesCodebookEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAll(users: List<PlacesCodebookEntity>)

    @Query("SELECT * FROM places_codebook")
    fun getAll(): LiveData<List<PlacesCodebookEntity>>

    @Query("SELECT * FROM places_codebook WHERE id = :id")
    suspend fun findById(id: String): List<PlacesCodebookEntity>

}