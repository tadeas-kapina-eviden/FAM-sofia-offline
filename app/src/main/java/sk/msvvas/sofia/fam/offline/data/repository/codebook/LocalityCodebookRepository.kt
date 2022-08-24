package sk.msvvas.sofia.fam.offline.data.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.daos.codebook.LocalityCodebookDao
import sk.msvvas.sofia.fam.offline.data.entities.codebook.LocalityCodebookEntity

class LocalityCodebookRepository(private val localityCodebookDao: LocalityCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allData = MutableLiveData<List<LocalityCodebookEntity>>()
    val searchResult = MutableLiveData<LocalityCodebookEntity>()

    init {
        getAll()
    }

    fun save(localityCodebook: LocalityCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            localityCodebookDao.save(localityCodebook)
        }
    }

    fun saveAll(localityCodebooks: List<LocalityCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            localityCodebookDao.saveAll(localityCodebooks)
        }
    }

    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.IO) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<LocalityCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async localityCodebookDao.findById(id)[0]
        }

    fun getAll() {
        coroutineScope.launch(Dispatchers.Main) {
            allData.value = asyncGetAll().await()
        }
    }

    private fun asyncGetAll(): Deferred<List<LocalityCodebookEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async localityCodebookDao.getAll()
        }
}