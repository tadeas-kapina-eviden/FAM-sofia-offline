package sk.msvvas.sofia.fam.offline.ui.views.loading

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import sk.msvvas.sofia.fam.offline.data.application.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.data.application.repository.ServerUrlRepository
import sk.msvvas.sofia.fam.offline.ui.navigation.Routes

class LoadingScreenViewModel(
    private val propertyRepository: PropertyRepository,
    private val serverUrlRepository: ServerUrlRepository,
    private val navController: NavController
) : ViewModel() {

    private val _propertiesLoaded = propertyRepository.loaded
    val propertiesLoaded: LiveData<Boolean> = _propertiesLoaded

    private val _serverUrlLoaded = serverUrlRepository.loaded
    val serverUrlLoaded: LiveData<Boolean> = _serverUrlLoaded

    private val _serverUrl = serverUrlRepository.url
    val serverUrl: LiveData<String?> = _serverUrl

    fun navigateToSetUpUrl() {
        navController.navigate(Routes.SET_UP_URL.value)
    }

    fun navigateToLoginViewWithDownloadedInventory() {
        navController.navigate(Routes.LOGIN_VIEW.value + "?id=" + (propertyRepository.allData.value!![0].inventoryId))
    }

    fun navigateToLoginView() {
        navController.navigate(Routes.LOGIN_VIEW.value);
    }

    fun navigateToInventoriesList() {
        navController.navigate(
            Routes.INVENTORY_DETAIL.withArgs(
                propertyRepository.allData.value!![0].inventoryId
            )
        )
    }

    fun isDownloadedInventory(): Boolean {
        return propertyRepository.allData.value?.isNotEmpty() ?: false
    }
}