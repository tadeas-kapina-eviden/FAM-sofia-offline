package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.RoomCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.RoomCodebookEntity

class RoomCodebookRepository(private val roomCodebookDao: RoomCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allData = MutableLiveData<List<RoomCodebookEntity>>()
    val searchResult = MutableLiveData<RoomCodebookEntity>()

    init {
        getAll()
    }

    fun save(roomCodebook: RoomCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            roomCodebookDao.save(roomCodebook)
            getAll()
        }
    }

    fun saveAll(roomCodebooks: List<RoomCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            roomCodebookDao.saveAll(roomCodebooks)
            getAll()
        }
    }

    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<RoomCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async roomCodebookDao.findById(id)[0]
        }

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