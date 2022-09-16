package sk.msvvas.sofia.fam.offline.data.application.daos.codebook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.PlaceCodebookEntity

/**
 * Data access object for placeCodebook table
 */
@Dao
interface PlaceCodebookDao {
    /**
     * Insert one item to placeCodebook table
     * @param place placeCodebook data
     */
    @Insert(onConflict = IGNORE)
    suspend fun save(place: PlaceCodebookEntity)

    /**
     * Insert multiple items to placeCodebook table
     * @param places list of placeCodebooks data
     */
    @Insert(onConflict = IGNORE)
    suspend fun saveAll(places: List<PlaceCodebookEntity>)

    /**
     * Get all items from placeCodebook table
     */
    @Query("SELECT * FROM places_codebook")
    fun getAll(): List<PlaceCodebookEntity>

    /**
     * Get one item from placeCodebook table identified by id
     * @param id id of placeCodebook we want to get
     * @return list of placeCodebooks with id equal as parameter (should have only one item)
     */
    @Query("SELECT * FROM places_codebook WHERE id = :id")
    suspend fun findById(id: String): List<PlaceCodebookEntity>

}