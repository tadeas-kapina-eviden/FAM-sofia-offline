package sk.msvvas.sofia.fam.offline.data.daos.codebook

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.entities.codebook.UserCodebookEntity

@Dao
interface UserCodebookDao {

    @Insert(onConflict = IGNORE)
    suspend fun save(user: UserCodebookEntity)

    @Query("INSERT INTO user_codebook VALUES(:users)")
    suspend fun saveAll(users: List<UserCodebookEntity>)

    @Query("SELECT * FROM user_codebook")
    fun getAll(): LiveData<List<UserCodebookEntity>>

    @Query("SELECT * FROM user_codebook WHERE id = :id")
    suspend fun findById(id: String): List<UserCodebookEntity>

}