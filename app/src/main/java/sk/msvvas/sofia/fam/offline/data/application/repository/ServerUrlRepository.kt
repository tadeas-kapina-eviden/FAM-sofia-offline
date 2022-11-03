package sk.msvvas.sofia.fam.offline.data.application.repository

import androidx.lifecycle.MutableLiveData
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
     * server url loaded from local database
     */
    val url = MutableLiveData<String?>()

    /**
     * Check if url is loaded from database
     * @see get
     */
    val loaded = MutableLiveData(false)

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
     * Data are save to url
     * There is always only one url saved
     * @see url
     */
    fun get() {
        coroutineScope.launch(Dispatchers.Main) {
            url.value = asyncGet().await().let {
                if (it.isEmpty()) {
                    return@let null
                } else {
                    return@let it[0].url
                }
            }
            loaded.value = true
        }
    }

    /**
     * Get the url from local database
     * Runs on background thread
     */
    private fun asyncGet(): Deferred<List<ServerUrlEntity>> =
        coroutineScope.async(Dispatchers.IO) {
            return@async serverUrlDao.load()
        }
}