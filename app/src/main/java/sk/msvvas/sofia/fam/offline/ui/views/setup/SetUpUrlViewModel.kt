package sk.msvvas.sofia.fam.offline.ui.views.setup

import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import sk.msvvas.sofia.fam.offline.data.application.repository.ServerUrlRepository
import sk.msvvas.sofia.fam.offline.data.client.ClientData
import sk.msvvas.sofia.fam.offline.ui.navigation.Routes

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

    private val _focusRequester = MutableLiveData(FocusRequester())
    val focusRequester: LiveData<FocusRequester> = _focusRequester

    fun navigateToLoginView() {
        navController.navigate(Routes.LOGIN_VIEW.value)
    }

    fun setUrl() {
        if (_url.value != null && _url.value!!.isNotEmpty()) {
            _savingData.value = true
            serverUrlRepository.save(url = url.value!!)
            ClientData.host = url.value!!
            navigateToLoginView()
        }else{
            _lastError.value = "Zadaj hodnotu Url"
        }
    }

    fun onChangeUrl(value: String) {
        _url.value = value
    }

    fun requestFocus(){
        _focusRequester.value!!.requestFocus()
    }
}