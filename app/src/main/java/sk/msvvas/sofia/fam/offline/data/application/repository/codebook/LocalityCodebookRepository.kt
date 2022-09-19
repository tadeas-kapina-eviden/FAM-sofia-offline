package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.LocalityCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity

/**
 * Repository for high-level interactions with database table locality_codebook
 */
class LocalityCodebookRepository(private val localityCodebookDao: LocalityCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * All data from locality_codebook table
     * Get by getAll function
     * @see getAll
     */
    val allData = MutableLiveData<List<LocalityCodebookEntity>>()

    /**
     * Last searched item from locality_codebook table
     * Get by findById function
     * @see findById
     */
    val searchResult = MutableLiveData<LocalityCodebookEntity>()

    init {
        getAll()
    }

    /**
     * Save one item to locality_codebook table
     * @param localityCodebook locality codebook data
     */
    fun save(localityCodebook: LocalityCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            localityCodebookDao.save(localityCodebook)
            getAll()
        }
    }

    /**
     * Save multiple items to locality_codebook table
     * @param localityCodebooks list of locality codebook data
     */
    fun saveAll(localityCodebooks: List<LocalityCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            localityCodebookDao.saveAll(localityCodebooks)
            getAll()
        }
    }

    /**
     * Find one item in locality_codebook table identified by id
     * Result is saved to searchResult
     * @see searchResult
     * @param id locality codebook id
     */
    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<LocalityCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async localityCodebookDao.findById(id)[0]
        }

    /**
     * Get all data from locality_codebook table
     * Data are save to allData
     * @see allData
     */
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