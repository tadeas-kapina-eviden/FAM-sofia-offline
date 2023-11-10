package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.RoomCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.RoomCodebookEntity

/**
 * Repository for high-level interactions with database table room_codebook
 */
class RoomCodebookRepository(private val roomCodebookDao: RoomCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * Save one item to room_codebook table
     * @param roomCodebook room codebook data
     */
    fun save(roomCodebook: RoomCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            roomCodebookDao.save(roomCodebook)
        }
    }

    /**
     * Save multiple items to room_codebook table
     * @param roomCodebooks list of room codebook data
     */
    fun saveAll(roomCodebooks: List<RoomCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            roomCodebookDao.saveAll(roomCodebooks)
        }
    }

    /**
     * Find one item in locality_codebook table identified by id
     * Result is saved to searchResult
     */
    suspend fun findById(id: String): RoomCodebookEntity {
        return roomCodebookDao.findById(id)[0];
    }


    /**
     * Get all data from locality_codebook table
     */
    suspend fun getAll(): List<RoomCodebookEntity> {
        return roomCodebookDao.getAll()
    }
}