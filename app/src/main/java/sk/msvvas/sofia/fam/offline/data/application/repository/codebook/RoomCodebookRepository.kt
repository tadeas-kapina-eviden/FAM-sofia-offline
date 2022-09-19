package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.RoomCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.RoomCodebookEntity

/**
 * Repository for high-level interactions with database table room_codebook
 */
class RoomCodebookRepository(private val roomCodebookDao: RoomCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    /**
     * All data from room_codebook table
     * Get by getAll function
     * @see getAll
     */
    val allData = MutableLiveData<List<RoomCodebookEntity>>()
    /**
     * Last searched item from room_codebook table
     * Get by findById function
     * @see findById
     */
    val searchResult = MutableLiveData<RoomCodebookEntity>()

    init {
        getAll()
    }

    /**
     * Save one item to room_codebook table
     * @param roomCodebook room codebook data
     */
    fun save(roomCodebook: RoomCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            roomCodebookDao.save(roomCodebook)
            getAll()
        }
    }

    /**
     * Save multiple items to room_codebook table
     * @param roomCodebooks list of room codebook data
     */
    fun saveAll(roomCodebooks: List<RoomCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            roomCodebookDao.saveAll(roomCodebooks)
            getAll()
        }
    }

    /**
     * Find one item in room_codebook table identified by id
     * Result is saved to searchResult
     * @see searchResult
     * @param id room codebook id
     */
    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<RoomCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async roomCodebookDao.findById(id)[0]
        }

    /**
     * Get all data from room_codebook table
     * Data are save to allData
     * @see allData
     */
    fun getAll() {
        coroutineScope.launch(Dispatchers.Main) {
            allData.value = asyncGetAll().await()
        }
    }

    private fun asyncGetAll(): Deferred<List<RoomCodebookEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async roomCodebookDao.getAll()
        }
}