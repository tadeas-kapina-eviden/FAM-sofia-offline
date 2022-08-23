package sk.msvvas.sofia.fam.offline.data.daos.codebook

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.entities.codebook.RoomCodebookEntity

@Dao
interface RoomCodebookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(room: RoomCodebookEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAll(rooms: List<RoomCodebookEntity>)

    @Query("SELECT * FROM rooms_codebook")
    fun getAll(): LiveData<List<RoomCodebookEntity>>

    @Query("SELECT * FROM rooms_codebook WHERE id = :id")
    suspend fun findById(id: String): List<RoomCodebookEntity>

}