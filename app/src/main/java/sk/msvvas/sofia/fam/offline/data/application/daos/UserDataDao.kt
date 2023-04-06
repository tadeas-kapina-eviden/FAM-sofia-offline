package sk.msvvas.sofia.fam.offline.data.application.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.UserDataEntity

@Dao
interface UserDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(url: UserDataEntity)

    @Query("SELECT * from user_data")
    suspend fun getAll(): List<UserDataEntity>

    @Query("DELETE FROM user_data")
    suspend fun deleteAll()

}