package sk.msvvas.sofia.fam.offline.ui.views.loading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sk.msvvas.sofia.fam.offline.data.application.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.data.application.repository.ServerUrlRepository
import sk.msvvas.sofia.fam.offline.ui.navigation.Routes

class LoadingScreenViewModel(
    private val propertyRepository: PropertyRepository,
    private val serverUrlRepository: ServerUrlRepository,
    private val navController: NavController
) : ViewModel() {

    private val _serverUrlLoaded = serverUrlRepository.loaded
    val serverUrlLoaded: LiveData<Boolean> = _serverUrlLoaded

    private val _serverUrl = serverUrlRepository.url
    val serverUrl: LiveData<String?> = _serverUrl

    private val _inventoryId = MutableLiveData<String>("")
    val inventoryIdFromProperty: LiveData<String> = _inventoryId


    init {
        CoroutineScope(Dispatchers.Main).launch {
            _inventoryId.value = propertyRepository.getInventoryId()
        }
    }

    fun loadUrl() {
        serverUrlRepository.get()
    }

    fun navigateToSetUpUrl() {
        navController.navigate(Routes.SET_UP_URL.value)
    }

    fun navigateToLoginViewWithDownloadedInventory() {
            navController.navigate(
                Routes.LOGIN_VIEW.value + "?id=" + _inventoryId.value
            )
    }

    fun navigateToLoginView() {
        navController.navigate(Routes.LOGIN_VIEW.value)
    }

    fun navigateToInventoriesList() {
            navController.navigate(
                Routes.INVENTORY_DETAIL.withArgs(
                    _inventoryId.value!!
                )
            )

    }

    suspend fun isDownloadedInventory(): Boolean {
        return withContext(Dispatchers.IO) {
            propertyRepository.getAll().isNotEmpty()
        }
    }
}