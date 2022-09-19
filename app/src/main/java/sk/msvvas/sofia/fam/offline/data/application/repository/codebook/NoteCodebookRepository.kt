package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.NoteCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.NoteCodebookEntity

/**
 * Repository for high-level interactions with database table note_codebook
 */
class NoteCodebookRepository(private val noteCodebookDao: NoteCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * All data from note_codebook table
     * Get by getAll function
     * @see getAll
     */
    val allData = MutableLiveData<List<NoteCodebookEntity>>()

    /**
     * Last searched item from note_codebook table
     * Get by findById function
     * @see findById
     */
    val searchResult = MutableLiveData<NoteCodebookEntity>()

    init {
        getAll()
    }

    /**
     * Save one item to note_codebook table
     * @param noteCodebook note codebook data
     */
    fun save(noteCodebook: NoteCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            noteCodebookDao.save(noteCodebook)
            getAll()
        }
    }

    /**
     * Save multiple items to note_codebook table
     * @param noteCodebooks list of note codebook data
     */
    fun saveAll(noteCodebooks: List<NoteCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            noteCodebookDao.saveAll(noteCodebooks)
            getAll()
        }
    }

    /**
     * Find one item in note_codebook table identified by id
     * Result is saved to searchResult
     * @see searchResult
     * @param id note codebook id
     */
    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<NoteCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async noteCodebookDao.findById(id)[0]
        }

    /**
     * Get all data from note_codebook table
     * Data are save to allData
     * @see allData
     */
    fun getAll() {
        coroutineScope.launch(Dispatchers.Main) {
            allData.value = asyncGetAll().await()
        }
    }

    private fun asyncGetAll(): Deferred<List<NoteCodebookEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async noteCodebookDao.getAll()
        }
}