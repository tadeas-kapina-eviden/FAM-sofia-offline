package sk.msvvas.sofia.fam.offline.data.application.daos.codebook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.RoomCodebookEntity

@Dao
interface RoomCodebookDao {

    @Insert(onConflict = IGNORE)
    suspend fun save(room: RoomCodebookEntity)

    @Insert(onConflict = IGNORE)
    suspend fun saveAll(rooms: List<RoomCodebookEntity>)

    @Query("SELECT * FROM rooms_codebook")
    fun getAll(): List<RoomCodebookEntity>

    @Query("SELECT * FROM rooms_codebook WHERE id = :id")
    suspend fun findById(id: String): List<RoomCodebookEntity>

}