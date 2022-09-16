package sk.msvvas.sofia.fam.offline.data.application.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.InventoryDao
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity

/**
 * Repository for high-level interactions with database table inventory
 * @param inventoryDao DAO for inventory table
 */
class InventoryRepository(private val inventoryDao: InventoryDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * All data from inventory table
     * Get by getAll function
     * @see getAll
     */
    val allData = MutableLiveData<List<InventoryEntity>>()

    /**
     * Last searched item from inventory table
     * Get by findById function
     * @see findById
     */
    val searchResult = MutableLiveData<InventoryEntity>()

    init {
        getAll()
    }

    /**
     * Save one item to inventory table
     * @param inventory inventory data
     */
    fun save(inventory: InventoryEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            inventoryDao.save(inventory)
            getAll()
        }
    }

    /**
     * Save multiple items to inventory table
     * @param inventories list of inventory data
     */
    fun saveAll(inventories: List<InventoryEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            inventoryDao.saveAll(inventories)
            getAll()
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
     * @see searchResult
     * @param id id of inventory we want to get
     */
    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<InventoryEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async inventoryDao.findById(id)[0]
        }

    /**
     * Get one all items from inventory table
     * Item is saved to allData
     * @see allData
     */
    fun getAll() {
        coroutineScope.launch(Dispatchers.Main) {
            allData.value = asyncGetAll().await()
        }
    }

    private fun asyncGetAll(): Deferred<List<InventoryEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async inventoryDao.getAll()
        }
}