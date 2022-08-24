package sk.msvvas.sofia.fam.offline.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.daos.InventoryDao
import sk.msvvas.sofia.fam.offline.data.entities.InventoryEntity

/* TODO add functionality*/
class InventoryRepository(private val inventoryDao: InventoryDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val getAll: LiveData<List<InventoryEntity>> = inventoryDao.getAll()
    private val searchResult = MutableLiveData<InventoryEntity>()

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

    fun findById(id: String): LiveData<InventoryEntity> {
        coroutineScope.launch(Dispatchers.IO) {
            searchResult.value = asyncFind(id).await()
        }
        return searchResult
    }

    private fun asyncFind(id: String): Deferred<InventoryEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async inventoryDao.findById(id)[0]
        }
}