package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.PlacesCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.PlacesCodebookEntity

class PlacesCodebookRepository(private val placesCodebookDao: PlacesCodebookDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allData = MutableLiveData<List<PlacesCodebookEntity>>()
    val searchResult = MutableLiveData<PlacesCodebookEntity>()

    init {
        getAll()
    }

    fun save(placesCodebook: PlacesCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            placesCodebookDao.save(placesCodebook)
        }
    }

    fun saveAll(placesCodebooks: List<PlacesCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            placesCodebookDao.saveAll(placesCodebooks)
        }
    }

    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<PlacesCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async placesCodebookDao.findById(id)[0]
        }

    fun getAll() {
        coroutineScope.launch(Dispatchers.Main) {
            allData.value = asyncGetAll().await()
        }
    }

    private fun asyncGetAll(): Deferred<List<PlacesCodebookEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async placesCodebookDao.getAll()
        }
}