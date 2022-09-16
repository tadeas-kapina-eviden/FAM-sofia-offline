package sk.msvvas.sofia.fam.offline.data.application.daos

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity

/**
 * Data access object for property table
 */
@Dao
interface PropertyDao {

    /**
     * Insert one item to property table
     * @param propertyEntity property data
     */
    @Insert(onConflict = IGNORE)
    suspend fun save(propertyEntity: PropertyEntity)

    /**
     * Insert multiple items to property table
     * @param propertyEntities list of properties data
     */
    @Insert(onConflict = IGNORE)
    suspend fun saveAll(propertyEntities: List<PropertyEntity>)

    /**
     * Update one item in property table
     * @param propertyEntity property data - must have equal id as item we want to change
     */
    @Update
    suspend fun update(propertyEntity: PropertyEntity)

    /**
     * Delete one item from property table
     * @param property property data - must have same id as item we want to delete
     */
    @Delete
    suspend fun delete(property: PropertyEntity)

    /**
     * Delete all items from property table
     */
    @Query("DELETE FROM properties")
    suspend fun deleteAll()

    /**
     * Get all items from property table
     */
    @Query("SELECT * FROM properties")
    fun getAll(): List<PropertyEntity>

    /**
     * Get one item from property table identified by id
     * @param id id of property we want to get
     * @return list of properties with id equal as parameter (should have only one item)
     */
    @Query("SELECT * FROM properties WHERE id = :id")
    suspend fun findById(id: Long): List<PropertyEntity>

    /**
     * Get multiple items from property table identified by id
     * @param inventoryId id of inventory from which we want to get properties
     * @return list of properties with inventoryId equal as parameter
     */
    @Query("SELECT * FROM properties WHERE inventory_id = :inventoryId")
    suspend fun findByInventoryId(inventoryId: String): List<PropertyEntity>
}