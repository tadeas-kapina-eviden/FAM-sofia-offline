package sk.msvvas.sofia.fam.offline.ui.views.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import sk.msvvas.sofia.fam.offline.data.application.repository.ServerUrlRepository
import sk.msvvas.sofia.fam.offline.data.client.ClientData
import sk.msvvas.sofia.fam.offline.ui.navigation.Routes

/**
 *
 */
class SetUpUrlViewModel(
    private val serverUrlRepository: ServerUrlRepository,
    private val navController: NavController
) : ViewModel() {

    private val _url = MutableLiveData("")
    val url: LiveData<String> = _url

    private val _lastError = MutableLiveData("")
    val lastError: LiveData<String> = _lastError

    private val _savingData = MutableLiveData(false)
    val savingData: LiveData<Boolean> = _savingData

    fun navigateToLoginView() {
        navController.navigate(Routes.LOGIN_VIEW.value)
    }

    fun setUrl() {
        if (_url.value != null && _url.value!!.isNotEmpty()) {
            _savingData.value = true
            val url = url.value!!.let {
                if (it.split("://").size >= 2)
                    return@let it.split("://")[1].split("/")[0]
                else
                    return@let it.split("/")[0]
            }
            serverUrlRepository.save(url = url)
            ClientData.host = url
            navigateToLoginView()
        } else {
            _lastError.value = "Zadaj hodnotu Url"
        }
    }

    fun onChangeUrl(value: String) {
        _url.value = value
    }
}