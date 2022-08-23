package sk.msvvas.sofia.fam.offline.data.repository.codebook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.daos.codebook.RoomCodebookDao
import sk.msvvas.sofia.fam.offline.data.entities.codebook.RoomCodebookEntity

class RoomCodebookRepository(private val roomCodebookDao: RoomCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val getAll: LiveData<List<RoomCodebookEntity>> = roomCodebookDao.getAll()
    val searchResult = MutableLiveData<RoomCodebookEntity>()

    fun save(roomCodebook: RoomCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            roomCodebookDao.save(roomCodebook)
        }
    }

    fun saveAll(roomCodebooks: List<RoomCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            roomCodebookDao.saveAll(roomCodebooks)
        }
    }

    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.IO) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<RoomCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async roomCodebookDao.findById(id)[0]
        }
}