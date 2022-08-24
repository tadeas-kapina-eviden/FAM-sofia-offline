package sk.msvvas.sofia.fam.offline.data.repository.codebook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.daos.codebook.PlacesCodebookDao
import sk.msvvas.sofia.fam.offline.data.entities.codebook.PlacesCodebookEntity

class PlacesCodebookRepository(private val placesCodebookDao: PlacesCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val getAll: LiveData<List<PlacesCodebookEntity>> = placesCodebookDao.getAll()
    private val searchResult = MutableLiveData<PlacesCodebookEntity>()

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

    fun findById(id: String): LiveData<PlacesCodebookEntity> {
        coroutineScope.launch(Dispatchers.IO) {
            searchResult.value = asyncFind(id).await()
        }
        return searchResult
    }

    private fun asyncFind(id: String): Deferred<PlacesCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async placesCodebookDao.findById(id)[0]
        }
}