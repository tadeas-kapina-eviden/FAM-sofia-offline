package sk.msvvas.sofia.fam.offline.ui.views.login

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
        } else if (_password.value!!.isEmpty()) {
            _lastError.value = "Zadajte heslo!"
        } else if (_client.value!!.isEmpty()) {
            _lastError.value = "Zadajte číslo klienta!"
        } else if (_client.value!!.length < 3) {
            _lastError.value = "Číslo klienta musí mať 3 znaky!"
        } else {
            changeView()
        }
    }
}