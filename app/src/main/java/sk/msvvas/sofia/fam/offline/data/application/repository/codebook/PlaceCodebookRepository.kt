package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.PlaceCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.PlaceCodebookEntity

/**
 * Repository for high-level interactions with database table place_codebook
 */
class PlaceCodebookRepository(private val placeCodebookDao: PlaceCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    /**
     * All data from place_codebook table
     * Get by getAll function
     * @see getAll
     */
    val allData = MutableLiveData<List<PlaceCodebookEntity>>()
    /**
     * Last searched item from place_codebook table
     * Get by findById function
     * @see findById
     */
    val searchResult = MutableLiveData<PlaceCodebookEntity>()

    init {
        getAll()
    }

    /**
     * Save one item to place_codebook table
     * @param placesCodebook place codebook data
     */
    fun save(placesCodebook: PlaceCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            placeCodebookDao.save(placesCodebook)
            getAll()
        }
    }

    /**
     * Save multiple items to place_codebook table
     * @param placesCodebooks list of place codebook data
     */
    fun saveAll(placesCodebooks: List<PlaceCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            placeCodebookDao.saveAll(placesCodebooks)
            getAll()
        }
    }

    /**
     * Find one item in place_codebook table identified by id
     * Result is saved to searchResult
     * @see searchResult
     * @param id place codebook id
     */
    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<PlaceCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async placeCodebookDao.findById(id)[0]
        }

    /**
     * Get all data from place_codebook table
     * Data are save to allData
     * @see allData
     */
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