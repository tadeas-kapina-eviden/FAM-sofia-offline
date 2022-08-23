package sk.msvvas.sofia.fam.offline.data.repository.codebook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.daos.codebook.UserCodebookDao
import sk.msvvas.sofia.fam.offline.data.entities.codebook.UserCodebookEntity

class UserCodebookRepository(private val userCodebookDao: UserCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val getAll: LiveData<List<UserCodebookEntity>> = userCodebookDao.getAll()
    val searchResult = MutableLiveData<UserCodebookEntity>()

    fun save(userCodebook: UserCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            userCodebookDao.save(userCodebook)
        }
    }

    fun saveAll(userCodebooks : List<UserCodebookEntity>){
        coroutineScope.launch(Dispatchers.IO) {
            userCodebookDao.saveAll(userCodebooks)
        }
    }

    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.IO) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<UserCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async userCodebookDao.findById(id)[0]
        }
}