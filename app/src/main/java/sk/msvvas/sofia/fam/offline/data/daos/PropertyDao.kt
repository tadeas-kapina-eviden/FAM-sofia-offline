package sk.msvvas.sofia.fam.offline.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity


@Dao
interface PropertyDao {

    @Insert(onConflict = IGNORE)
    suspend fun save(propertyEntity: PropertyEntity)

    @Insert(onConflict = IGNORE)
    suspend fun saveAll(propertyEntities: List<PropertyEntity>)

    @Query("SELECT * FROM properties")
    fun getAll(): List<PropertyEntity>

    @Query("SELECT * FROM properties WHERE id = :id")
    suspend fun findById(id: String): List<PropertyEntity>

}