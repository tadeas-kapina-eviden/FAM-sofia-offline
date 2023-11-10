package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.NoteCodebookDao
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.NoteCodebookEntity

/**
 * Repository for high-level interactions with database table note_codebook
 */
class NoteCodebookRepository(private val noteCodebookDao: NoteCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    /**
     * Save one item to note_codebook table
     * @param noteCodebook note codebook data
     */
    fun save(noteCodebook: NoteCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            noteCodebookDao.save(noteCodebook)
        }
    }

    /**
     * Save multiple items to note_codebook table
     * @param noteCodebooks list of note codebook data
     */
    fun saveAll(noteCodebooks: List<NoteCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            noteCodebookDao.saveAll(noteCodebooks)
        }
    }

    /**
     * Find one item in note_codebook table identified by id
     * @param id locality codebook id
     */
    suspend fun findById(id: String): NoteCodebookEntity {
        return noteCodebookDao.findById(id)[0];
    }


    /**
     * Get all data from note_codebook table
     */
    suspend fun getAll(): List<NoteCodebookEntity> {
        return noteCodebookDao.getAll()
    }
}