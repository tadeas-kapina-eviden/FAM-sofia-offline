package sk.msvvas.sofia.fam.offline.data.application.repository

import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.ServerUrlDao
import sk.msvvas.sofia.fam.offline.data.application.entities.ServerUrlEntity

/**
 * Repository for high-level interactions with server url table
 * @param serverUrlDao DAO for server url table
 */
class ServerUrlRepository(private val serverUrlDao: ServerUrlDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    /**
     * Convert String to ServerUrlEntity and save it to local database
     */
    fun save(url: String) {
        coroutineScope.launch(Dispatchers.IO) {
            serverUrlDao.save(ServerUrlEntity(url))
        }
    }

    /**
     * Get url from server url table
     */
    suspend fun get(): ServerUrlEntity?{
        return serverUrlDao.load()
    }

}