package sk.msvvas.sofia.fam.offline.data.application.daos.codebook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.NoteCodebookEntity

/**
 * Data access object for noteCodebook table
 */
@Dao
interface NoteCodebookDao {
    /**
     * Insert one item to noteCodebook table
     * @param note noteCodebook data
     */
    @Insert(onConflict = IGNORE)
    suspend fun save(note: NoteCodebookEntity)

    /**
     * Insert multiple items to noteCodebook table
     * @param notes list of noteCodebook data
     */
    @Insert(onConflict = IGNORE)
    suspend fun saveAll(notes: List<NoteCodebookEntity>)

    /**
     * Get all items from noteCodebook table
     */
    @Query("SELECT * FROM notes_codebook")
    fun getAll(): List<NoteCodebookEntity>

    /**
     * Get one item from noteCodebook table identified by id
     * @param id id of noteCodebook we want to get
     * @return list of noteCodebooks with id equal as parameter (should have only one item)
     */
    @Query("SELECT * FROM notes_codebook WHERE id = :id")
    suspend fun findById(id: String): List<NoteCodebookEntity>
}