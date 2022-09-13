package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.PlaceCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.PlaceCodebookEntity

class PlaceCodebookRepository(private val placeCodebookDao: PlaceCodebookDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allData = MutableLiveData<List<PlaceCodebookEntity>>()
    val searchResult = MutableLiveData<PlaceCodebookEntity>()

    init {
        getAll()
    }

    fun save(placesCodebook: PlaceCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            placeCodebookDao.save(placesCodebook)
        }
    }

    fun saveAll(placesCodebooks: List<PlaceCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            placeCodebookDao.saveAll(placesCodebooks)
        }
    }

    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<PlaceCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async placeCodebookDao.findById(id)[0]
        }

    fun getAll() {
        coroutineScope.launch(Dispatchers.Main) {
            allData.value = asyncGetAll().await()
        }
    }

    private fun asyncGetAll(): Deferred<List<PlaceCodebookEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async placeCodebookDao.getAll()
        }
}