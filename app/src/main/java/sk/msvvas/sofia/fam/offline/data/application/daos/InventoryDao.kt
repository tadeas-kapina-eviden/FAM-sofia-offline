package sk.msvvas.sofia.fam.offline.data.application.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity

/**
 * Data access object for inventory table
 */
@Dao
interface InventoryDao {

    /**
     * Insert one item to inventory table
     * @param inventoryEntity property data
     */
    @Insert(onConflict = IGNORE)
    suspend fun save(inventoryEntity: InventoryEntity)

    /**
     * Insert multiple items to inventory table
     * @param inventoryEntities list of inventories data
     */
    @Insert(onConflict = IGNORE)
    suspend fun saveAll(inventoryEntities: List<InventoryEntity>)

    /**
     * Delete all items from inventory table
     */
    @Query("DELETE FROM inventories")
    suspend fun deleteAll()

    /**
     * Get all items from inventory table
     */
    @Query("SELECT * FROM inventories")
    fun getAll(): List<InventoryEntity>

    /**
     * Get one item from inventory table identified by id
     * @param id id of inventory we want to get
     * @return list of inventories with id equal as parameter (should have only one item)
     */
    @Query("SELECT * FROM inventories WHERE id = :id")
    suspend fun findById(id: String): List<InventoryEntity>
}