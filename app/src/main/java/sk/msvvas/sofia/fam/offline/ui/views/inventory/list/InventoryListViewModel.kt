package sk.msvvas.sofia.fam.offline.ui.views.inventory.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.application.repository.InventoryRepository
import sk.msvvas.sofia.fam.offline.data.application.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.data.application.repository.codebook.AllCodebooksRepository
import sk.msvvas.sofia.fam.offline.data.client.Client
import sk.msvvas.sofia.fam.offline.ui.navigation.Routes
import java.io.EOFException
import java.net.ConnectException

class InventoryListViewModel(
    private val inventoryRepository: InventoryRepository,
    private val propertyRepository: PropertyRepository,
    private val allCodebooksRepository: AllCodebooksRepository,
    private val navController: NavController
) : ViewModel() {

    private val _inventories = MutableLiveData<List<InventoryEntity>>()
    val inventories: LiveData<List<InventoryEntity>> = _inventories

    private val _selectedInventoryId = MutableLiveData("")
    val selectedInventoryId: LiveData<String> = _selectedInventoryId

    private val _isDownloadConfirmShown = MutableLiveData(false)
    val isDownloadConfirmShown: LiveData<Boolean> = _isDownloadConfirmShown

    private val _downloadingData = MutableLiveData(false)
    val downloadingData: LiveData<Boolean> = _downloadingData

    private val _loadingState = MutableLiveData("")
    val loadingState: LiveData<String> = _loadingState

    private val _exitModalShown = MutableLiveData(false)
    val exitModalShown: LiveData<Boolean> = _exitModalShown

    private val _loadingsInventories = MutableLiveData(true)
    val loadingsInventories: LiveData<Boolean> = _loadingsInventories

    private val _errorModalShown = MutableLiveData(false);
    val errorModalShown: LiveData<Boolean> = _errorModalShown

    init {
        CoroutineScope(Dispatchers.Main).launch {
            inventoryRepository.deleteAll()

            _inventories.value = Client.getInventories()

            inventoryRepository.saveAll(_inventories.value!!)

            _loadingsInventories.value = false
        }
    }

    fun onSelectInventory(inventoryId: String) {
        _selectedInventoryId.value = inventoryId
        _isDownloadConfirmShown.value = true
    }

    fun onSelectInventoryConfirm() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _downloadingData.value = true
                _isDownloadConfirmShown.value = false
                _loadingState.value = "Sťahujú sa inventúry... "
                propertyRepository.saveAll(Client.getPropertiesByInventoryID(_selectedInventoryId.value!!))
                _loadingState.value = "Sťahujú sa číselníky lokácií... "
                allCodebooksRepository.saveAllLocalities(Client.getLocalityCodebooks())
                _loadingState.value = "Sťahujú sa číselníky miestností... "
                allCodebooksRepository.saveAllRooms(Client.getRoomCodebooks())
                _loadingState.value = "Sťahujú sa číselníky pracovísk... "
                allCodebooksRepository.saveAllPlaces(Client.getPlaceCodebooks())
                _loadingState.value = "Sťahujú sa číselníky používateľov... "
                allCodebooksRepository.saveAllUsers(Client.getUserCodebooks())
                _loadingState.value = "Sťahujú sa číselníky fixných poznámok... "
                allCodebooksRepository.saveAllNotes(Client.getNoteCodebooks())
                navController.navigate(Routes.INVENTORY_DETAIL.withArgs(_selectedInventoryId.value!!))
            }catch (ex: Exception) {
                _downloadingData.value = false
                _errorModalShown.value = true
            }
        }
    }

    fun onSelectInventoryDecline() {
        _isDownloadConfirmShown.value = false
    }

    fun showExitModalWindow() {
        _exitModalShown.value = true
    }

    fun hideExitModalWindow() {
        _exitModalShown.value = false
    }

    fun hideErrorModalWindow() {
        _errorModalShown.value = false
    }
}