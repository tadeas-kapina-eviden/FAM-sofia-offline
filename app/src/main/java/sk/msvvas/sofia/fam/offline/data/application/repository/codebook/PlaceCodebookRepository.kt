package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.PlaceCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.PlaceCodebookEntity

/**
 * Repository for high-level interactions with database table place_codebook
 */
class PlaceCodebookRepository(private val placeCodebookDao: PlaceCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * Save one item to place_codebook table
     * @param placesCodebook place codebook data
     */
    fun save(placesCodebook: PlaceCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            placeCodebookDao.save(placesCodebook)
        }
    }

    /**
     * Save multiple items to place_codebook table
     * @param placesCodebooks list of place codebook data
     */
    fun saveAll(placesCodebooks: List<PlaceCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            placeCodebookDao.saveAll(placesCodebooks)
        }
    }

    /**
     * Find one item in place_codebook table identified by id
     * @param id locality codebook id
     */
    suspend fun findById(id: String): PlaceCodebookEntity {
        return placeCodebookDao.findById(id)[0];
    }


    /**
     * Get all data from place_codebook table
     */
    suspend fun getAll(): List<PlaceCodebookEntity> {
        return placeCodebookDao.getAll()
    }
}