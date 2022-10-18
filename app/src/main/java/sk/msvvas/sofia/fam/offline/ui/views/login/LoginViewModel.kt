package sk.msvvas.sofia.fam.offline.ui.views.login

import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sk.msvvas.sofia.fam.offline.data.application.repository.InventoryRepository
import sk.msvvas.sofia.fam.offline.data.client.Client
import sk.msvvas.sofia.fam.offline.data.client.ClientData
import sk.msvvas.sofia.fam.offline.ui.navigation.Routes

/**
 * View Model for Login view
 * @param navController NavController from application Navigation
 * @param inventoryIDParameter inventory id of downloaded inventory (empty if none)
 * @param inventoryRepository inventory repository for local database
 */
class LoginViewModel(
    private val navController: NavController,
    private val inventoryIDParameter: String,
    private val inventoryRepository: InventoryRepository,
    private val submitInventory: Boolean
) : ViewModel() {

    private val _loginName = MutableLiveData("v_developer")
    val loginName: LiveData<String> = _loginName

    private val _password = MutableLiveData("Vyvojar.123")
    val password: LiveData<String> = _password

    private val _client = MutableLiveData("110")
    val client: LiveData<String> = _client

    private val _lastError = MutableLiveData("")
    val lastError: LiveData<String> = _lastError

    private val _loginNameFocusRequester = MutableLiveData(FocusRequester())
    val loginNameFocusRequester: LiveData<FocusRequester> = _loginNameFocusRequester

    private val _passwordFocusRequester = MutableLiveData(FocusRequester())
    val passwordFocusRequester: LiveData<FocusRequester> = _passwordFocusRequester

    private val _clientFocusRequester = MutableLiveData(FocusRequester())
    val clientFocusRequester: LiveData<FocusRequester> = _clientFocusRequester

    /**
     * Variable hold
     */
    private val _downloadingData = MutableLiveData(false)
    val downloadingData: LiveData<Boolean> = _downloadingData

    /**
     * Function executed when loginName text field was changed
     */
    fun onLoginNameChanged(newName: String) {
        if (newName.length <= 12) {
            _loginName.value = newName
        }
    }

    /**
     * Function executed when password text field was changed
     */
    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    /**
     * Function executed when client text field was changed
     */
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

    /**
     * Function executed when login button was clicked
     * Validate inputs and try to connect to back-end
     */
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
            CoroutineScope(Dispatchers.Main).launch {
                _downloadingData.value = true
                try {
                    val response =
                        Client.validateLogin(
                            username = _loginName.value!!,
                            password = _password.value!!,
                            clientId = _client.value!!
                        )
                    if (response) {

                        ClientData.client = _client.value!!
                        ClientData.username = _loginName.value!!
                        ClientData.password = _password.value!!

                        if (inventoryIDParameter.isEmpty()) {
                            inventoryRepository.deleteAll()
                            inventoryRepository.saveAll(Client.getInventories())
                            navController.navigate(Routes.INVENTORY_LIST.value)
                        } else {
                            navController.navigate(
                                Routes.INVENTORY_DETAIL.withArgs(
                                    inventoryIDParameter,
                                ) + "?submit=${
                                    if (submitInventory) "1" else "0"
                                }"
                            )
                        }
                    } else {
                        _lastError.value =
                            "Klient, meno alebo heslo je nesprávne. Zopakujte prihlásenie!"
                        _downloadingData.value = false
                    }
                } catch (e: RuntimeException) {
                    _lastError.value =
                        "Prihlásenie sa nepodarilo. Skontrolujte pripojenie k internetu!"
                    _downloadingData.value = false
                }
            }
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