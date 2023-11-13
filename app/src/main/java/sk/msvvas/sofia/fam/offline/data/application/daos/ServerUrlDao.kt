package sk.msvvas.sofia.fam.offline.data.application.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.ServerUrlEntity

/**
 * Data access object for local save of server url
 */
@Dao
interface ServerUrlDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(url: ServerUrlEntity)

    @Query("SELECT * from server_url limit 1")
    suspend fun load(): ServerUrlEntity?
}