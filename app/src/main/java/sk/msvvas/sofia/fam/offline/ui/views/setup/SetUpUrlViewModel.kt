package sk.msvvas.sofia.fam.offline.ui.views.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import sk.msvvas.sofia.fam.offline.data.application.repository.ServerUrlRepository
import sk.msvvas.sofia.fam.offline.data.client.ClientData
import sk.msvvas.sofia.fam.offline.ui.navigation.Routes

/**
 * View model for set up url view
 * @param serverUrlRepository repository for saving url to database
 * @param navController application navigation controller
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

    /**
     * Invokes after submitting url.
     * Check if url input is not empty,
     * save url to local database and  navigate to login view
     */
    fun setUrl() {
        _url.value = _url.value!!.trim()
        if (_url.value != null && _url.value!!.isNotEmpty()) {
            _savingData.value = true
            _url.value = _url.value!!.removeSuffix("\n")
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

    /**
     * Function invokes after value in text field changes
     */
    fun onChangeUrl(value: String) {
        _url.value = value
        if (_url.value!!.isNotEmpty() && _url.value!!.last() == '\n') {
            _url.value = _url.value!!.removeSuffix("\n")
            setUrl()
        }
    }
}