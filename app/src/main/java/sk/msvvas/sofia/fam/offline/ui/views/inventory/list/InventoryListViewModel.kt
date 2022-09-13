package sk.msvvas.sofia.fam.offline.ui.views.inventory.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.application.repository.InventoryRepository
import sk.msvvas.sofia.fam.offline.data.application.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.data.client.Client
import sk.msvvas.sofia.fam.offline.ui.navigation.Routes

class InventoryListViewModel(
    private val inventoryRepository: InventoryRepository,
    private val propertyRepository: PropertyRepository,
    private val navController: NavController
) : ViewModel() {
    private val _inventories: LiveData<List<InventoryEntity>> = inventoryRepository.allData
    val inventories: LiveData<List<InventoryEntity>> = _inventories

    private val _selectedInventoryId = MutableLiveData("")
    val selectedInventoryId: LiveData<String> = _selectedInventoryId

    private val _isDownloadConfirmShown = MutableLiveData(false)
    val isDownloadConfirmShown: LiveData<Boolean> = _isDownloadConfirmShown

    private val _downloadingData = MutableLiveData(false)
    val downloadingData: LiveData<Boolean> = _downloadingData

    fun onSelectInventory(inventoryId: String) {
        _selectedInventoryId.value = inventoryId
        _isDownloadConfirmShown.value = true
    }

    fun onSelectInventoryConfirm() {
        CoroutineScope(Dispatchers.Main).launch {
            _downloadingData.value = true
            _isDownloadConfirmShown.value = false
            propertyRepository.saveAll(Client.getPropertiesByInventoryID(_selectedInventoryId.value!!))
            navController.navigate(Routes.INVENTORY_DETAIL.withArgs(_selectedInventoryId.value!!))
        }
    }

    fun onSelectInventoryDecline() {
        _isDownloadConfirmShown.value = false
    }
}