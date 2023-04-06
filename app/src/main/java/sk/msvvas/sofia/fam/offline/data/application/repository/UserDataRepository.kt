package sk.msvvas.sofia.fam.offline.data.application.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.UserDataDao
import sk.msvvas.sofia.fam.offline.data.application.entities.UserDataEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.NoteCodebookEntity

class UserDataRepository(private val userDataDao: UserDataDao) {


    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * All data from user_data table
     * Get by getAll function
     * @see getAll
     */
    val allData = MutableLiveData<List<UserDataEntity>>()

    /**
     * Save one item to user_data table
     * @param userDataEntity user data
     */
    fun save(userDataEntity: UserDataEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            userDataDao.save(userDataEntity)
            getAll()
        }
    }

    /**
     * Get all data from user_data table
     * Data are save to allData
     * @see allData
     */
    fun getAll() {
        coroutineScope.launch(Dispatchers.Main) {
            allData.value = asyncGetAll().await()
        }
    }

    private fun asyncGetAll(): Deferred<List<UserDataEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async userDataDao.getAll()
        }

    /**
     * Delete all items from user_data table
     */
    fun deleteAll() {
        coroutineScope.launch(Dispatchers.IO) {
            userDataDao.deleteAll()
        }
    }

}