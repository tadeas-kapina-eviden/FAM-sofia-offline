package sk.msvvas.sofia.fam.offline.data.application.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sk.msvvas.sofia.fam.offline.data.application.daos.PropertyDao
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.application.model.LocalityRoomPair

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
    suspend fun findProcessedBySearchCriteria(
        locality: String?,
        room: String?,
        user: String?
    ): List<PropertyEntity> {
        return propertyDao.findProcessedBySearchCriteria(
            locality,
            room,
            user
        )
    }

    /**
     * Find properties from table by search criteria
     * */
    suspend fun findUnprocessedBySearchCriteria(
        locality: String?,
        room: String?,
        user: String?
    ): List<PropertyEntity> {
        return propertyDao.findUnprocessedBySearchCriteria(
            locality,
            room,
            user
        )
    }

    suspend fun countNEW(): Int {
        return propertyDao.countNEW().toInt()
    }


    suspend fun getByIdentifiers(
        propertyNumber: String,
        subNumber: String
    ): PropertyEntity? {
        return propertyDao.getByIdentifiers(propertyNumber, subNumber)
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
        return propertyDao.getCountProccesed()
    }

    suspend fun getCountUnProcessed(): Int {
        return propertyDao.getCountUnProccesed()
    }

    /**
     * Find properties from table by search criteria
     * */
    suspend fun getCountProcessedSearchCriteria(
        locality: String?,
        room: String?,
        user: String?
    ): Int {
        return propertyDao.getCountProcessedSearchCriteria(
            locality,
            room,
            user
        )
    }

    /**
     * Find properties from table by search criteria
     * */
    suspend fun getCountUnProcessedSearchCriteria(
        locality: String?,
        room: String?,
        user: String?
    ): Int {
        return propertyDao.getCountUnProcessedSearchCriteria(
            locality,
            room,
            user
        )
    }


    suspend fun findLocalityRoomPairs(): List<LocalityRoomPair> {
        return propertyDao.findLocalityRoomPairs()
    }
}