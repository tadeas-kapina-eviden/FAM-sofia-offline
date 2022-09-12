package sk.msvvas.sofia.fam.offline.data.application.daos.codebook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity

@Dao
interface LocalityCodebookDao {

    @Insert(onConflict = IGNORE)
    suspend fun save(locality: LocalityCodebookEntity)

    @Insert(onConflict = IGNORE)
    suspend fun saveAll(localities: List<LocalityCodebookEntity>)

    @Query("SELECT * FROM localities_codebook")
    fun getAll(): List<LocalityCodebookEntity>

    @Query("SELECT * FROM localities_codebook WHERE id = :id")
    suspend fun findById(id: String): List<LocalityCodebookEntity>
}