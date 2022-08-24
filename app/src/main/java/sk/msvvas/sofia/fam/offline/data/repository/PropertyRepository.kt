package sk.msvvas.sofia.fam.offline.data.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.daos.PropertyDao
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity

class PropertyRepository(private val propertyDao: PropertyDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allData = MutableLiveData<List<PropertyEntity>>()
    val searchResult = MutableLiveData<PropertyEntity>()
    val searchByInventoryIdResult = MutableLiveData<List<PropertyEntity>>()

    init {
        getAll()
    }

    fun save(property: PropertyEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.save(property)
        }
    }

    fun saveAll(properties: List<PropertyEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.saveAll(properties)
        }
    }

    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.IO) {
            searchResult.value = asyncFindById(id).await()
        }
    }

    private fun asyncFindById(id: String): Deferred<PropertyEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async propertyDao.findById(id)[0]
        }

    fun findByInventoryId(inventoryId: String) {
        coroutineScope.launch(Dispatchers.IO) {
            searchByInventoryIdResult.value = asyncFindByInventoryId(inventoryId).await()
        }
    }

    private fun asyncFindByInventoryId(id: String): Deferred<List<PropertyEntity>> =
        coroutineScope.async(Dispatchers.IO) {
            return@async propertyDao.findByInventoryId(id)
        }

    fun getAll() {
        coroutineScope.launch(Dispatchers.Main) {
            allData.value = asyncGetAll().await()
        }
    }

    private fun asyncGetAll(): Deferred<List<PropertyEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async propertyDao.getAll()
        }
}