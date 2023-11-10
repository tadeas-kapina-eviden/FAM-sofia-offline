package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.UserCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.UserCodebookEntity

/**
 * Repository for high-level interactions with database table user_codebook
 */
class UserCodebookRepository(private val userCodebookDao: UserCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * Save one item to user_codebook table
     * @param userCodebook user codebook data
     */
    fun save(userCodebook: UserCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            userCodebookDao.save(userCodebook)
        }
    }

    /**
     * Save multiple items to user_codebook table
     * @param userCodebooks list of locality codebook data
     */
    fun saveAll(userCodebooks: List<UserCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            userCodebookDao.saveAll(userCodebooks)
        }
    }

    /**
     * Find one item in locality_codebook table identified by id
     * @param id locality codebook id
     */
    suspend fun findById(id: String): UserCodebookEntity {
        return userCodebookDao.findById(id)[0];
    }


    /**
     * Get all data from locality_codebook table
     */
    suspend fun getAll(): List<UserCodebookEntity> {
        return userCodebookDao.getAll()
    }
}