package sk.msvvas.sofia.fam.offline.data.application.daos

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.application.model.PropertyPreviewModel

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
    suspend fun getAll(): List<PropertyEntity>

    /**
     * Get one item from property table identified by id
     * @param id id of property we want to get
     * @return list of properties with id equal as parameter (should have only one item)
     */
    @Query("SELECT * FROM properties WHERE id = :id")
    suspend fun findById(id: Long): List<PropertyEntity>

    @Query(
        "select * FROM properties where " +
                "  (record_status = :recordStatus " +
                "  or (:recordStatus = 'P' and (record_status = 'N' or record_status = 'Z' or record_status = 'S')) " +
                "  or (:recordStatus = 'U' and (record_status = 'X' or record_status = 'C'))) " +
                "  and (:locality is null or locality = :locality) " +
                "  and (:locality_new is null or locality_new = :locality_new) " +
                "  and (:room is null or room = :room) " +
                "  and (:room_new is null or room_new = :room_new) " +
                "  and (:user is null or personal_number = :user) " +
                "  and (:user_new is null or personal_number_new = :user_new) "
    )
    suspend fun findBySearchCriteria(
        recordStatus: Char,
        locality: String?,
        room: String?,
        user: String?,
        locality_new: String?,
        room_new: String?,
        user_new: String?
    ): List<PropertyEntity>

    /**
     * Get multiple items from property table identified by id
     * @param inventoryId id of inventory from which we want to get properties
     * @return list of properties with inventoryId equal as parameter
     */
    @Query("SELECT * FROM properties WHERE inventory_id = :inventoryId")
    suspend fun findByInventoryId(inventoryId: String): List<PropertyEntity>

    @Query("SELECT inventory_id FROM properties LIMIT 1")
    suspend fun getInventoryId(): String

    @Query(
        "SELECT COUNT(record_status) AS row_count  " +
                "FROM properties " +
                "WHERE record_status like '%' || :values || '%'"
    )
    suspend fun getCountProccesed(
        values : String
    ): Int

    @Query(
        "SELECT COUNT(record_status) AS row_count  " +
                "FROM properties " +
                "where record_status like '%' || :values || '%'"
    )
    suspend fun getCountUnProccesed(
        values: String
    ): Int

    @Query(
        "select id, text_main_number, property_number, subnumber, record_status from properties " +
                "where id = :id"
    )
    suspend fun getPreviewById(id: Long): PropertyPreviewModel
}
