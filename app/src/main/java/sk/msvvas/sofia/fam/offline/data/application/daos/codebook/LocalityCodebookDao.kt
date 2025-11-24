package sk.msvvas.sofia.fam.offline.data.application.daos.codebook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity

/**
 * Data access object for localityCodebook table
 */
@Dao
interface LocalityCodebookDao {

    /**
     * Insert one item to localityCodebook table
     * @param locality localityCodebook data
     */
    @Insert(onConflict = IGNORE)
    suspend fun save(locality: LocalityCodebookEntity)

    /**
     * Insert multiple items to localityCodebook table
     * @param localities list of localityCodebooks data
     */
    @Insert(onConflict = IGNORE)
    suspend fun saveAll(localities: List<LocalityCodebookEntity>)

    /**
     * Get all items from localityCodebook table
     */
    @Query("SELECT * FROM localities_codebook")
    fun getAll(): List<LocalityCodebookEntity>

    /**
     * Get one item from localityCodebook table identified by id
     * @param id id of localityCodebook we want to get
     * @return list of localityCodebooks with id equal as parameter (should have only one item)
     */
    @Query("SELECT * FROM localities_codebook WHERE id = :id")
    suspend fun findById(id: String): List<LocalityCodebookEntity>
}