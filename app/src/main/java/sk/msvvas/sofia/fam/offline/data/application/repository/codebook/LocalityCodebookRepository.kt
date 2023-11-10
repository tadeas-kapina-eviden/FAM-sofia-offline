package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.LocalityCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity

/**
 * Repository for high-level interactions with database table locality_codebook
 */
class LocalityCodebookRepository(private val localityCodebookDao: LocalityCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * Save one item to locality_codebook table
     * @param localityCodebook locality codebook data
     */
    fun save(localityCodebook: LocalityCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            localityCodebookDao.save(localityCodebook)
        }
    }

    /**
     * Save multiple items to locality_codebook table
     * @param localityCodebooks list of locality codebook data
     */
    fun saveAll(localityCodebooks: List<LocalityCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            localityCodebookDao.saveAll(localityCodebooks)
        }
    }

    /**
     * Find one item in locality_codebook table identified by id
     * @param id locality codebook id
     */
    suspend fun findById(id: String): LocalityCodebookEntity {
        return localityCodebookDao.findById(id)[0];
    }


    /**
     * Get all data from locality_codebook table
     */
    suspend fun getAll(): List<LocalityCodebookEntity> {
        return localityCodebookDao.getAll()
    }

}