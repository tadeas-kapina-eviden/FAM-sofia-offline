package sk.msvvas.sofia.fam.offline.data.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.daos.PropertyDao
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity

class PropertyRepository(private val propertyDao: PropertyDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allData = MutableLiveData<List<PropertyEntity>>()
    val searchResult = MutableLiveData<PropertyEntity>()

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
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<PropertyEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async propertyDao.findById(id)[0]
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