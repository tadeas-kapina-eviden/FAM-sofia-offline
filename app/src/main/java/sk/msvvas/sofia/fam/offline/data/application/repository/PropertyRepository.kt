package sk.msvvas.sofia.fam.offline.data.application.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.PropertyDao
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity

class PropertyRepository(private val propertyDao: PropertyDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allData = MutableLiveData<List<PropertyEntity>>()
    val searchResult = MutableLiveData<PropertyEntity>()
    val searchByInventoryIdResult = MutableLiveData<List<PropertyEntity>>()
    val loaded = MutableLiveData(false)

    init {
        getAll()
    }

    fun save(property: PropertyEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.save(property)
            getAll()
        }
    }

    fun saveAll(properties: List<PropertyEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.saveAll(properties)
            getAll()
        }
    }

    fun update(property: PropertyEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.update(property)
        }
    }

    fun delete(property: PropertyEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.delete(property)
        }
    }

    fun deleteAll() {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.deleteAll()
        }
    }

    fun findById(id: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFindById(id).await()
        }
    }

    private fun asyncFindById(id: Long): Deferred<PropertyEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            val resultList = propertyDao.findById(id)
            return@async if (resultList.isEmpty()) null else resultList[0]
        }

    fun findByInventoryId(inventoryId: String) {
        coroutineScope.launch(Dispatchers.Main) {
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
            loaded.value = true
        }
    }

    private fun asyncGetAll(): Deferred<List<PropertyEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async propertyDao.getAll()
        }
}