package sk.msvvas.sofia.fam.offline.ui.views.login

import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel(
    private val changeView: () -> Unit
) : ViewModel() {
    private val _loginName = MutableLiveData("")
    val loginName: LiveData<String> = _loginName

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _client = MutableLiveData("")
    val client: LiveData<String> = _client

    private val _lastError = MutableLiveData("")
    val lastError: LiveData<String> = _lastError

    private val _loginNameFocusRequester = MutableLiveData(FocusRequester())
    val loginNameFocusRequester: LiveData<FocusRequester> = _loginNameFocusRequester

    private val _passwordFocusRequester = MutableLiveData(FocusRequester())
    val passwordFocusRequester: LiveData<FocusRequester> = _passwordFocusRequester

    private val _clientFocusRequester = MutableLiveData(FocusRequester())
    val clientFocusRequester: LiveData<FocusRequester> = _clientFocusRequester

    fun onLoginNameChanged(newName: String) {
        if (newName.length <= 12) {
            _loginName.value = newName
        }
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onClientChange(newClient: String) {
        if (newClient.length <= 3) {
            newClient.forEach {
                if (!("0123456789".contains(it))) {
                    return
                }
            }
            _client.value = newClient
        }
    }

    //TODO complete login
    fun onLoginButtonClick() {
        if (_loginName.value!!.isEmpty()) {
            _lastError.value = "Zadajte meno užívateľa!"
            requestLoginNameFocus()
        } else if (_password.value!!.isEmpty()) {
            _lastError.value = "Zadajte heslo!"
            requestPasswordFocus()
        } else if (_client.value!!.isEmpty()) {
            _lastError.value = "Zadajte číslo klienta!"
            requestClientFocus()
        } else if (_client.value!!.length < 3) {
            _lastError.value = "Číslo klienta musí mať 3 znaky!"
            requestClientFocus()
        } else {
            changeView()
        }
    }

    fun requestLoginNameFocus() {
        _loginNameFocusRequester.value!!.requestFocus()
    }

    fun requestPasswordFocus() {
        _passwordFocusRequester.value!!.requestFocus()
    }

    fun requestClientFocus() {
        _clientFocusRequester.value!!.requestFocus()
    }
}