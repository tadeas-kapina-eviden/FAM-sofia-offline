package sk.msvvas.sofia.fam.offline.data.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.daos.codebook.NoteCodebookDao
import sk.msvvas.sofia.fam.offline.data.entities.codebook.NoteCodebookEntity

class NoteCodebookRepository(private val noteCodebookDao: NoteCodebookDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allData = MutableLiveData<List<NoteCodebookEntity>>()
    val searchResult = MutableLiveData<NoteCodebookEntity>()

    fun save(noteCodebook: NoteCodebookEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            noteCodebookDao.save(noteCodebook)
        }
    }

    fun saveAll(noteCodebooks: List<NoteCodebookEntity>) {
        coroutineScope.launch(Dispatchers.IO) {
            noteCodebookDao.saveAll(noteCodebooks)
        }
    }

    fun findById(id: String) {
        coroutineScope.launch(Dispatchers.IO) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: String): Deferred<NoteCodebookEntity?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async noteCodebookDao.findById(id)[0]
        }

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