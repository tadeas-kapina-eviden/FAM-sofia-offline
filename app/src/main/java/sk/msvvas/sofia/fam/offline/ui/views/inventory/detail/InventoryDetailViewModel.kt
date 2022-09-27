package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.RoomCodebookEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.UserCodebookEntity
import sk.msvvas.sofia.fam.offline.data.application.repository.InventoryRepository
import sk.msvvas.sofia.fam.offline.data.application.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.data.application.repository.codebook.AllCodebooksRepository
import sk.msvvas.sofia.fam.offline.data.client.Client
import sk.msvvas.sofia.fam.offline.data.client.ClientData
import sk.msvvas.sofia.fam.offline.ui.navigation.Routes

class InventoryDetailViewModel(
    private val propertyRepository: PropertyRepository,
    private val inventoryRepository: InventoryRepository,
    private val allCodebooksRepository: AllCodebooksRepository,
    val navController: NavController,
    inventoryIdParameter: String,
    localityFilterParameter: String,
    roomFilterParameter: String,
    userFilterParameter: String,
    statusFilterParameter: Char,
    submitInventory: Boolean
) : ViewModel() {

    private val _properties =
        propertyRepository.searchByInventoryIdResult
    val properties: LiveData<List<PropertyEntity>> =
        propertyRepository.searchByInventoryIdResult

    private val _filteredProperties = MutableLiveData(listOf<PropertyEntity>())
    val filteredProperties: LiveData<List<PropertyEntity>> = _filteredProperties

    private val _isFiltersShown = MutableLiveData(false)
    val isFiltersShown: LiveData<Boolean> = _isFiltersShown

    private val _inventoryId = MutableLiveData(inventoryIdParameter)
    val inventoryId: LiveData<String> = _inventoryId

    private val _statusFilter = MutableLiveData(statusFilterParameter)
    val statusFilter: LiveData<Char> = _statusFilter

    private val _codeFilter = MutableLiveData("")
    val codeFilter: LiveData<String> = _codeFilter

    private val _localityFilter = MutableLiveData(localityFilterParameter)
    val localityFilter: LiveData<String> = _localityFilter

    private val _roomFilter = MutableLiveData(roomFilterParameter)
    val roomFilter: LiveData<String> = _roomFilter

    private val _userFilter = MutableLiveData(userFilterParameter)
    val userFilter: LiveData<String> = _userFilter

    private val _scanWithoutDetail = MutableLiveData(false)
    val scanWithoutDetail: LiveData<Boolean> = _scanWithoutDetail

    private val _errorHeader = MutableLiveData("")
    val errorHeader: LiveData<String> = _errorHeader

    private val _errorText = MutableLiveData("")
    val errorText: LiveData<String> = _errorText

    private val _codeFilterLocality = MutableLiveData<String?>(null)
    var codeFilterLocality: LiveData<String?> = _codeFilterLocality

    private val _codeFilterRoom = MutableLiveData<String?>(null)
    var codeFilterRoom: LiveData<String?> = _codeFilterRoom

    private val _isCodebookSelectionViewShown = MutableLiveData(false)
    val isCodebookSelectionViewShown: LiveData<Boolean> = _isCodebookSelectionViewShown

    private val _codebookSelectionViewData = MutableLiveData(listOf<Any>())
    val codebookSelectionViewData: LiveData<List<Any>> = _codebookSelectionViewData

    private val _codebookSelectionViewIdGetter = MutableLiveData<(Any) -> String> { "" }
    val codebookSelectionViewIdGetter: LiveData<(Any) -> String> = _codebookSelectionViewIdGetter

    private val _codebookSelectionViewDescriptionGetter = MutableLiveData<(Any) -> String> { "" }
    val codebookSelectionViewDescriptionGetter: LiveData<(Any) -> String> =
        _codebookSelectionViewDescriptionGetter

    private val _selectCodebook = MutableLiveData<(String) -> Unit> {}
    val selectCodebook: LiveData<(String) -> Unit> = _selectCodebook

    private val _deleteCodebook = MutableLiveData {}
    val deleteCodebook: LiveData<() -> Unit> = _deleteCodebook

    private val _codebookSelectionViewLastValue = MutableLiveData("")
    val codebookSelectionViewLastValue: LiveData<String> = _codebookSelectionViewLastValue

    private val _exitModalShown = MutableLiveData(false)
    val exitModalShown: LiveData<Boolean> = _exitModalShown

    private val _submitInventoryConfirmModalShown = MutableLiveData(false)
    val submitInventoryConfirmModalShown: LiveData<Boolean> = _submitInventoryConfirmModalShown

    private val _requireLoginModalShown = MutableLiveData(false)
    val requireLoginModalShown: LiveData<Boolean> = _requireLoginModalShown

    private val _loadingData = MutableLiveData(false)
    val loadingData: LiveData<Boolean> = _loadingData

    init {
        if (!submitInventory) {
            propertyRepository.findByInventoryId(inventoryId = inventoryIdParameter)
        } else {
            submitInventory()
        }
    }

    fun onFiltersShowClick() {
        _isFiltersShown.value = !(_isFiltersShown.value!!)
    }

    fun onCodeFilterChange(newCode: String) {
        _codeFilter.value = newCode
    }

    fun onScanWithoutDetailButtonClick() {
        _scanWithoutDetail.value = !_scanWithoutDetail.value!!
    }

    fun runCodeFilter() {
        if (_codeFilter.value!!.length == 20) {
            val propertyNumber: String = _codeFilter.value!!.subSequence(4, 16).toString()
            val subnumber: String = _codeFilter.value!!.subSequence(16, 20).toString()

            val selectedList = _properties.value!!.filter {
                it.propertyNumber == propertyNumber && it.subnumber == subnumber
            }
            if (selectedList.isEmpty()) {
                val newCount = _properties.value!!.filter {
                    it.propertyNumber.trim() == "NOVY"
                }.size + 1
                navController.navigate(
                    Routes.PROPERTY_DETAIL.withArgs(
                        (-newCount).toString(),
                    ) + "?locality=" + _localityFilter.value!! + "&room=" + _roomFilter.value!! + "&user=" + _userFilter.value!! + "&inventoryId=" + _inventoryId.value!! + "&statusFilter=" + _statusFilter.value + "&isManual=" + false.toString()
                )
            } else {
                if (_scanWithoutDetail.value!!) {
                    val propertyToUpdate = selectedList.first()
                    propertyToUpdate.localityNew = _localityFilter.value!!
                    propertyToUpdate.roomNew = _roomFilter.value!!
                    propertyToUpdate.personalNumberNew = _userFilter.value!!
                    propertyRepository.update(property = propertyToUpdate)
                } else {
                    navController.navigate(
                        Routes.PROPERTY_DETAIL.withArgs(
                            selectedList.first().id.toString()
                        ) + "?locality=" + _localityFilter.value!! + "&room=" + _roomFilter.value!! + "&user=" + _userFilter.value!! + "&statusFilter=" + _statusFilter.value!! + "&isManual=" + false.toString()
                    )
                }
            }
        } else if (_codeFilter.value!!.length == 22) {
            val localityId: String = _codeFilter.value!!.subSequence(4, 14).toString().trim()
            val roomId: String = _codeFilter.value!!.subSequence(14, 22).toString().trim()

            _codeFilterLocality.value = localityId
            _codeFilterRoom.value = roomId
        } else {
            _errorHeader.value = "Nesprávny kód..."
            _errorText.value =
                "Zadaný kód je nesprávny, skúste znova, alebo kontaktujte administrátora!"
        }
        _codeFilter.value = ""
    }

    fun confirmLocalityChange() {
        _localityFilter.value = _codeFilterLocality.value
        _roomFilter.value = codeFilterRoom.value
        _codeFilterLocality.value = null
        _codeFilterRoom.value = null
    }

    fun runFilters() {
        filterOutValues()
    }

    fun filterOutValues() {
        if (_properties.value == null || _properties.value!!.isEmpty()) {
            _filteredProperties.value = emptyList()
        } else {
            _filteredProperties.value = _properties.value!!.filter {
                (_localityFilter.value == null || _localityFilter.value!!.isEmpty() || it.locality == _localityFilter.value)
                        && (_roomFilter.value == null || _roomFilter.value!!.isEmpty() || it.room == _roomFilter.value)
                        && (_userFilter.value == null || _userFilter.value!!.isEmpty() || it.personalNumber == _userFilter.value)
                        && ((_statusFilter.value == 'U' && (it.recordStatus == 'X' || it.recordStatus == 'C'))
                        || (_statusFilter.value == 'P' && (it.recordStatus == 'Z' || it.recordStatus == 'S' || it.recordStatus == 'N')))
            }
        }
    }

    fun onSelectProperty(id: Long) {
        val computedId: Long =
            if (id < 0) {
                -(_properties.value!!.filter {
                    it.propertyNumber.trim() == "NOVY"
                }.size + 1).toLong()
            } else id

        navController.navigate(
            Routes.PROPERTY_DETAIL.withArgs(
                computedId.toString(),
            ) + "?locality=" + _localityFilter.value!! + "&room=" + _roomFilter.value!! + "&user=" + _userFilter.value!! + "&inventoryId=" + _inventoryId.value + "&statusFilter=" + _statusFilter.value + "&isManual=" + true.toString()
        )
    }

    fun closeErrorAlert() {
        _errorHeader.value = ""
        _errorText.value = ""
    }

    fun statusFilterUnprocessed() {
        _statusFilter.value = 'U'
        filterOutValues()
    }

    fun statusFilterProcessed() {
        _statusFilter.value = 'P'
        filterOutValues()
    }

    fun statusFilterStatus() {
        _statusFilter.value = 'S'
        filterOutValues()
    }

    fun closeCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = false
    }

    fun showLocationCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = allCodebooksRepository.allLocalities.value
        _codebookSelectionViewIdGetter.value = { (it as LocalityCodebookEntity).id }
        _codebookSelectionViewDescriptionGetter.value =
            { (it as LocalityCodebookEntity).description }
        _codebookSelectionViewLastValue.value = _localityFilter.value
        _selectCodebook.value = {
            closeCodebookSelectionView()
            _localityFilter.value = it
        }
        _deleteCodebook.value = {
            closeCodebookSelectionView()
            _localityFilter.value = ""
        }
    }

    fun showRoomCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = allCodebooksRepository.allRooms.value
        _codebookSelectionViewIdGetter.value = { (it as RoomCodebookEntity).id }
        _codebookSelectionViewDescriptionGetter.value =
            { (it as RoomCodebookEntity).description }
        _codebookSelectionViewLastValue.value = _roomFilter.value
        _selectCodebook.value = {
            closeCodebookSelectionView()
            _roomFilter.value = it
        }
        _deleteCodebook.value = {
            closeCodebookSelectionView()
            _roomFilter.value = ""
        }
    }

    fun showUserCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = allCodebooksRepository.allUsers.value
        _codebookSelectionViewIdGetter.value = { (it as UserCodebookEntity).id }
        _codebookSelectionViewDescriptionGetter.value =
            { (it as UserCodebookEntity).fullName }
        _codebookSelectionViewLastValue.value = _userFilter.value
        _selectCodebook.value = {
            closeCodebookSelectionView()
            _userFilter.value = it
        }
        _deleteCodebook.value = {
            closeCodebookSelectionView()
            _userFilter.value = ""
        }
    }

    fun onLocalityRoomStatusSelect(locality: String, room: String) {
        _codeFilterLocality.value = locality
        _codeFilterRoom.value = room
        filterOutValues()
    }

    fun countUnprocessed(): Int {
        return if (_properties.value != null)
            _properties.value!!.count {
                (it.recordStatus == 'C' || it.recordStatus == 'X') &&
                        (_localityFilter.value == null || _localityFilter.value!!.isEmpty() || it.locality == _localityFilter.value)
                        && (_roomFilter.value == null || _roomFilter.value!!.isEmpty() || it.room == _roomFilter.value)
                        && (_userFilter.value == null || _userFilter.value!!.isEmpty() || it.personalNumber == _userFilter.value)

            } else 0

    }

    fun countProcessed(): Int {
        return if (_properties.value != null)
            _properties.value!!.count {
                (it.recordStatus == 'S' || it.recordStatus == 'Z' || it.recordStatus == 'N') &&
                        (_localityFilter.value == null || _localityFilter.value!!.isEmpty() || it.locality == _localityFilter.value)
                        && (_roomFilter.value == null || _roomFilter.value!!.isEmpty() || it.room == _roomFilter.value)
                        && (_userFilter.value == null || _userFilter.value!!.isEmpty() || it.personalNumber == _userFilter.value)

            } else 0
    }

    fun showExitModalWindow() {
        _exitModalShown.value = true
    }

    fun hideExitModalWindow() {
        _exitModalShown.value = false
    }

    fun submitInventory() {
        if (ClientData.username.isEmpty()) {
            requireLoginModalShow()
            submitInventoryConfirmModalHide()
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            _submitInventoryConfirmModalShown.value = false
            _loadingData.value = true
            val responseStatus = Client.submitProcessedProperties(
                inventoryRepository.allData.value!!.filter { it.id == propertyRepository.searchByInventoryIdResult.value!![0].inventoryId }[0],
                propertyRepository.searchByInventoryIdResult.value!!
            )
            if (responseStatus == HttpStatusCode.Created) {
                propertyRepository.deleteAll()
                navController.navigate(Routes.INVENTORY_LIST.value)
            } else {
                _loadingData.value = false
                _errorHeader.value = "Chyba!"
                _errorText.value =
                    "Nastala chyba - položby sa nepodarilo odoslať na server. Chyba: ${responseStatus.value} - ${responseStatus.description}, "
            }
        }
    }

    fun requireLoginModalShow() {
        _requireLoginModalShown.value = true
    }

    fun requireLoginModalHide() {
        _requireLoginModalShown.value = false
    }

    fun submitInventoryConfirmModalShow() {
        _submitInventoryConfirmModalShown.value = true
    }

    fun submitInventoryConfirmModalHide() {
        _submitInventoryConfirmModalShown.value = false
    }

    fun toLogin() {
        navController.navigate(Routes.LOGIN_VIEW.value + "?id=${inventoryId.value}&submit=1")
    }
}