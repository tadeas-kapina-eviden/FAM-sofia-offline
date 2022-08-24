package sk.msvvas.sofia.fam.offline.data.daos.codebook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.entities.codebook.NoteCodebookEntity

@Dao
interface NoteCodebookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(note: NoteCodebookEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAll(notes: List<NoteCodebookEntity>)

    @Query("SELECT * FROM notes_codebook")
    fun getAll(): List<NoteCodebookEntity>

    @Query("SELECT * FROM notes_codebook WHERE id = :id")
    suspend fun findById(id: String): List<NoteCodebookEntity>
}