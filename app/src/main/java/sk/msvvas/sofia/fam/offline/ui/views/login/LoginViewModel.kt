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

    //TODO complete input check
    fun onLoginButtonClick() {
        changeView()
    }
}