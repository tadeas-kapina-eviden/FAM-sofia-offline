package sk.msvvas.sofia.fam.offline.data.application.daos

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.application.model.LocalityRoomPair
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
                "WHERE record_status = 78 or record_status = 83 or record_status = 90"
    )
    suspend fun getCountProccesed(): Int

    @Query(
        "SELECT COUNT(record_status) AS row_count  " +
                "FROM properties " +
                "where record_status = 88 or record_status = 67"
    )
    suspend fun getCountUnProccesed(): Int

    @Query(
        "select id, text_main_number, property_number, subnumber, record_status from properties " +
                "where id = :id"
    )
    suspend fun getPreviewById(id: Long): PropertyPreviewModel

    @Query(
        "select * from properties where property_number = :propertyNumber and subnumber = :subNumber limit 1"
    )
    suspend fun getByIdentifiers(
        propertyNumber: String,
        subNumber: String
    ): PropertyEntity?

    @Query(
        "select * FROM properties where " +
                "  (record_status = 78 or record_status = 83 or record_status = 90) " +
                "  and (:locality is null or :locality = '' or locality = :locality) " +
                "  and (:room is null or :room = '' or room = :room) " +
                "  and (:user is null or :user = '' or personal_number = :user) "
    )
    suspend fun findProcessedBySearchCriteria(
        locality: String?,
        room: String?,
        user: String?
    ): List<PropertyEntity>

    @Query(
        "select * FROM properties where " +
                "  (record_status = 88 or record_status = 67) " +
                "  and (:locality is null or :locality = '' or locality_new= :locality) " +
                "  and (:room is null or :room = '' or room_new = :room) " +
                "  and (:user is null or :user = '' or personal_number_new = :user) "
    )
    suspend fun findUnprocessedBySearchCriteria(
        locality: String?,
        room: String?,
        user: String?
    ): List<PropertyEntity>


    @Query(
        "select " +
                " case " +
                "        when (record_status = 88 or record_status = 67) then 'false' " +
                "        when (record_status = 78 or record_status = 83 or record_status = 90) then 'true' " +
                "    end as processed, " +
                " case " +
                "        when (record_status = 88 or record_status = 67) then locality " +
                "        when (record_status = 78 or record_status = 83 or record_status = 90) then locality_new " +
                "    end as locality, " +
                " case " +
                "        when (record_status = 88 or record_status = 67) then room " +
                "        when (record_status = 78 or record_status = 83 or record_status = 90) then room_new " +
                "    end as room " +
                " from properties "
    )
    suspend fun findLocalityRoomPairs(): List<LocalityRoomPair>

    @Query(
        "select subnumber from properties " +
                "where property_number = 'NOVY' order by subnumber desc limit 1"
    )
    suspend fun countNEW() : String
}
