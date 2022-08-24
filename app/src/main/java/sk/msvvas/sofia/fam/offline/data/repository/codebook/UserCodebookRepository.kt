package sk.msvvas.sofia.fam.offline.data.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.daos.codebook.UserCodebookDao
import sk.msvvas.sofia.fam.offline.data.entities.codebook.UserCodebookEntity

class UserCodebookRepository(private val userCodebookDao: UserCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allData = MutableLiveData<List<UserCodebookEntity>>()
    val searchResult = MutableLiveData<UserCodebookEntity>()

    init {
        getAll()
    }

    fun save(userCodebook: UserCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            userCodebookDao.save(userCodebook)
        }
    }

    fun saveAll(userCodebooks: List<UserCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            userCodebookDao.saveAll(userCodebooks)
        }
    }

    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<UserCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async userCodebookDao.findById(id)[0]
        }

    fun getAll() {
        coroutineScope.launch(Dispatchers.Main) {
            allData.value = asyncGetAll().await()
        }
    }

    private fun asyncGetAll(): Deferred<List<UserCodebookEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async userCodebookDao.getAll()
        }
}