package sk.msvvas.sofia.fam.offline.data.application.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import sk.msvvas.sofia.fam.offline.data.application.daos.ServerUrlDao
import sk.msvvas.sofia.fam.offline.data.application.entities.ServerUrlEntity

class ServerUrlRepository(private val serverUrlDao: ServerUrlDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val url = MutableLiveData<String?>()

    val loaded = MutableLiveData(false)

    fun save(url : String){
        coroutineScope.launch(Dispatchers.IO){
            serverUrlDao.save(ServerUrlEntity(url))
        }
    }

    /**
     * Get all data from property table
     * Data are save to allData
     * @see url
     */
    fun get() {
        coroutineScope.launch(Dispatchers.Main) {
            url.value = asyncGet().await().let {
                if (it.isEmpty()){
                    return@let null
                }else{
                    return@let it[0].url
                }
            }
            loaded.value = true
        }
    }

    private fun asyncGet(): Deferred<List<ServerUrlEntity>> =
        coroutineScope.async(Dispatchers.IO) {
            return@async serverUrlDao.load()
        }
}