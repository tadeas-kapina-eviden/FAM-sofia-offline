package sk.msvvas.sofia.fam.offline.data.application.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.PropertyDao
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity

/**
 * Repository for high-level interactions with database table property
 * @param propertyDao DAO for inventory table
 */
class PropertyRepository(private val propertyDao: PropertyDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * All data from property table
     * Get by getAll function
     * @see getAll
     */
    val allData = MutableLiveData<List<PropertyEntity>>()

    /**
     * Last searched item from property table
     * Get by findById function
     * @see findById
     */
    val searchResult = MutableLiveData<PropertyEntity>()

    /**
     * Last items searched by inventory id from property table
     * Get by findByInventoryId function
     * @see findByInventoryId
     */
    val searchByInventoryIdResult = MutableLiveData<List<PropertyEntity>>()

    /**
     * Tells id allData property is already loaded
     * @see allData
     */
    val loaded = MutableLiveData(false)

    init {
        getAll()
    }

    /**
     * Save one item to property table
     * @param property property data
     */
    fun save(property: PropertyEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.save(property)
            getAll()
        }
    }

    /**
     * Save multiple data to property table
     * @param properties list of property data
     */
    fun saveAll(properties: List<PropertyEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.saveAll(properties)
            getAll()
        }
    }

    /**
     * Change one item in property table
     * @param property property data we want to change - must have same primary key (id) as item we want to change
     */
    fun update(property: PropertyEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.update(property)
            getAll()
        }
    }

    /**
     * Delete one item form property table
     * @param property data of property we want to delete
     */
    fun delete(property: PropertyEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.delete(property)
            getAll()
        }
    }

    /**
     * Delete all items from property table
     */
    fun deleteAll() {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.deleteAll()
            getAll()
        }
    }

    /**
     * Find one item in property table identified by id
     * Result is save to searchResult
     * @see searchResult
     * @param id id of item we want to get
     */
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

    /**
     * Find multiple items in property table identified by inventoryId
     * Result is saved to searchByInventoryIdResult
     * @param inventoryId id of inventory from which we want to get properties
     * @see searchByInventoryIdResult
     */
    fun findByInventoryId(inventoryId: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchByInventoryIdResult.value = asyncFindByInventoryId(inventoryId).await()
        }
    }

    private fun asyncFindByInventoryId(id: String): Deferred<List<PropertyEntity>> =
        coroutineScope.async(Dispatchers.IO) {
            return@async propertyDao.findByInventoryId(id)
        }

    /**
     * Get all data from property table
     * Data are save to allData
     * @see allData
     */
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