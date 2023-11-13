package sk.msvvas.sofia.fam.offline.data.application.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sk.msvvas.sofia.fam.offline.data.application.daos.InventoryDao
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity

/**
 * Repository for high-level interactions with database table inventory
 * @param inventoryDao DAO for inventory table
 */
class InventoryRepository(private val inventoryDao: InventoryDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    /**
     * Save one item to inventory table
     * @param inventory inventory data
     */
    fun save(inventory: InventoryEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            inventoryDao.save(inventory)
        }
    }

    /**
     * Save multiple items to inventory table
     * @param inventories list of inventory data
     */
    fun saveAll(inventories: List<InventoryEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            inventoryDao.saveAll(inventories)
        }
    }

    /**
     * Delete all items from inventory table
     */
    fun deleteAll() {
        coroutineScope.launch(Dispatchers.IO) {
            inventoryDao.deleteAll()
        }
    }


    /**
     * Get one item from inventory table identified by id
     * Item is saved to searchResult
     * @param id id of inventory we want to get
     */
    suspend fun findById(id: String): InventoryEntity {
        return inventoryDao.findById(id)
    }

    /**
     * Get one all items from inventory table
     */
    suspend fun getAll(): List<InventoryEntity> {
        return inventoryDao.getAll()
    }
}