package sk.msvvas.sofia.fam.offline.data.application.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sk.msvvas.sofia.fam.offline.data.application.daos.PropertyDao
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity

/**
 * Repository for high-level interactions with database table property
 * @param propertyDao DAO for inventory table
 */
class PropertyRepository(private val propertyDao: PropertyDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * Save one item to property table
     * @param property property data
     */
    fun save(property: PropertyEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.save(property)
        }
    }

    /**
     * Save multiple data to property table
     * @param properties list of property data
     */
    fun saveAll(properties: List<PropertyEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.saveAll(properties)
        }
    }

    /**
     * Change one item in property table
     * @param property property data we want to change - must have same primary key (id) as item we want to change
     */
    fun update(property: PropertyEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.update(property)
        }
    }

    /**
     * Delete one item form property table
     * @param property data of property we want to delete
     */
    fun delete(property: PropertyEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.delete(property)
        }
    }

    /**
     * Delete all items from property table
     */
    fun deleteAll() {
        coroutineScope.launch(Dispatchers.IO) {
            propertyDao.deleteAll()
        }
    }

    /**
     * Find one item in property table identified by id
     * Result is save to searchResult
     * @param id id of item we want to get
     */
    suspend fun findById(id: Long): PropertyEntity {
        return propertyDao.findById(id)[0]
    }


    /**
     * Find properties from table by search criteria
     * */
    suspend fun findBySearchCriteria(
        recordStaus: Char,
        locality: String?,
        room: String?,
        user: String?
    ): List<PropertyEntity> {
        if ("PSZN".contains(recordStaus)) {
            return propertyDao.findBySearchCriteria(
                recordStaus,
                null,
                null,
                null,
                locality,
                room,
                user
            )
        } else if ("UXC".contains(recordStaus)) {
            return propertyDao.findBySearchCriteria(
                recordStaus,
                locality,
                room,
                user,
                null,
                null,
                null
            )
        } else {
            // TODO: dokončit
            return ArrayList()
        }
    }


    /**
     * Find multiple items in property table identified by inventoryId
     */
    suspend fun findByInventoryId(inventoryId: String): List<PropertyEntity> {
        return propertyDao.findByInventoryId(inventoryId)
    }

    /**
     * Get all data from property table
     */
    suspend fun getAll(): List<PropertyEntity> {
        return propertyDao.getAll()
    }

    suspend fun getInventoryId(): String {
        return propertyDao.getInventoryId()
    }

    suspend fun getCountProcessed(): Int {
        return propertyDao.getCountProccesed("NSZ")
    }

    suspend fun getCountUnProcessed(): Int {
        return propertyDao.getCountUnProccesed("XCU")
    }
}