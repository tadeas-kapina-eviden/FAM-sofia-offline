package sk.msvvas.sofia.fam.offline.data.application.daos.codebook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.UserCodebookEntity

/**
 * Data access object for userCodebook table
 */
@Dao
interface UserCodebookDao {
    /**
     * Insert one item to userCodebook table
     * @param user userCodebook data
     */
    @Insert(onConflict = IGNORE)
    suspend fun save(user: UserCodebookEntity)

    /**
     * Insert multiple items to userCodebook table
     * @param users list of userCodebooks data
     */
    @Insert(onConflict = IGNORE)
    suspend fun saveAll(users: List<UserCodebookEntity>)

    /**
     * Get all items from userCodebook table
     */
    @Query("SELECT * FROM user_codebook")
    fun getAll(): List<UserCodebookEntity>

    /**
     * Get one item from userCodebook table identified by id
     * @param id id of userCodebook we want to get
     * @return list of userCodebooks with id equal as parameter (should have only one item)
     */
    @Query("SELECT * FROM user_codebook WHERE id = :id")
    suspend fun findById(id: String): List<UserCodebookEntity>

}