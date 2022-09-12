package sk.msvvas.sofia.fam.offline.data.application.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.InventoryDao
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity

class InventoryRepository(private val inventoryDao: InventoryDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allData = MutableLiveData<List<InventoryEntity>>()
    val searchResult = MutableLiveData<InventoryEntity>()

    init {
        getAll()
    }

    fun save(inventory: InventoryEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            inventoryDao.save(inventory)
        }
    }

    fun saveAll(inventories: List<InventoryEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            inventoryDao.saveAll(inventories)
        }
    }

    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<InventoryEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async inventoryDao.findById(id)[0]
        }

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