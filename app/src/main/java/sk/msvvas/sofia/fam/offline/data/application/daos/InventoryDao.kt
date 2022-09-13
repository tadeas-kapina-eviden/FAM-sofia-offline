package sk.msvvas.sofia.fam.offline.data.application.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity

@Dao
interface InventoryDao {

    @Insert(onConflict = IGNORE)
    suspend fun save(inventoryEntity: InventoryEntity)

    @Insert(onConflict = IGNORE)
    suspend fun saveAll(inventoryEntities: List<InventoryEntity>)

    @Query("DELETE FROM inventories")
    suspend fun deleteAll()

    @Query("SELECT * FROM inventories")
    fun getAll(): List<InventoryEntity>

    @Query("SELECT * FROM inventories WHERE id = :id")
    suspend fun findById(id: String): List<InventoryEntity>
}