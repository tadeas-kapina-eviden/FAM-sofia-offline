package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.UserCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.UserCodebookEntity

/**
 * Repository for high-level interactions with database table user_codebook
 */
class UserCodebookRepository(private val userCodebookDao: UserCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * All data from user_codebook table
     * Get by getAll function
     * @see getAll
     */
    val allData = MutableLiveData<List<UserCodebookEntity>>()

    /**
     * Last searched item from user_codebook table
     * Get by findById function
     * @see findById
     */
    val searchResult = MutableLiveData<UserCodebookEntity>()

    /**
     * Save one item to user_codebook table
     * @param userCodebook user codebook data
     */
    fun save(userCodebook: UserCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            userCodebookDao.save(userCodebook)
            getAll()
        }
    }

    /**
     * Save multiple items to user_codebook table
     * @param userCodebooks list of locality codebook data
     */
    fun saveAll(userCodebooks: List<UserCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            userCodebookDao.saveAll(userCodebooks)
            getAll()
        }
    }

    /**
     * Find one item in user_codebook table identified by id
     * Result is saved to searchResult
     * @see searchResult
     * @param id room codebook id
     */
    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<UserCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async userCodebookDao.findById(id)[0]
        }

    /**
     * Get all data from user_codebook table
     * Data are save to allData
     * @see allData
     */
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