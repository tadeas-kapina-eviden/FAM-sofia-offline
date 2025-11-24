package sk.msvvas.sofia.fam.offline.data.application.daos.codebook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.RoomCodebookEntity

/**
 * Data access object for roomCodebook table
 */
@Dao
interface RoomCodebookDao {
    /**
     * Insert one item to roomCodebook table
     * @param room roomCodebook data
     */
    @Insert(onConflict = IGNORE)
    suspend fun save(room: RoomCodebookEntity)

    /**
     * Insert multiple items to roomCodebook table
     * @param rooms list of roomCodebooks data
     */
    @Insert(onConflict = IGNORE)
    suspend fun saveAll(rooms: List<RoomCodebookEntity>)

    /**
     * Get all items from roomCodebook table
     */
    @Query("SELECT * FROM rooms_codebook")
    fun getAll(): List<RoomCodebookEntity>

    /**
     * Get one item from roomCodebook table identified by id
     * @param id id of roomCodebook we want to get
     * @return list of roomCodebooks with id equal as parameter (should have only one item)
     */
    @Query("SELECT * FROM rooms_codebook WHERE id = :id")
    suspend fun findById(id: String): List<RoomCodebookEntity>

}