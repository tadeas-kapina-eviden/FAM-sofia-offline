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
import java.util.Collections.max

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

    companion object {
        public const val BATCH_SIZE = 1000;
    }

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

    private val _loadingState = MutableLiveData("")
    val loadingState: LiveData<String> = _loadingState

    private val _unprocessedCount = MutableLiveData(0)
    val unprocessedCount: LiveData<Int> = _unprocessedCount

    private val _processedCount = MutableLiveData(0)
    val processedCount: LiveData<Int> = _processedCount

    private val _locationNotSelectedModalShown = MutableLiveData(false)
    val locationNotSelectedModalShown: LiveData<Boolean> = _locationNotSelectedModalShown


    init {
        allCodebooksRepository.getAll()
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
        if (_codeFilter.value!!.isNotEmpty() && _codeFilter.value!!.last() == '\n') {
            _codeFilter.value = _codeFilter.value!!.removeSuffix("\n")
            runCodeFilter()
        }
    }

    fun onScanWithoutDetailButtonClick() {
        _scanWithoutDetail.value = !_scanWithoutDetail.value!!
    }

    fun runCodeFilter() {
        if (_codeFilter.value!!.isEmpty()) {
            return
        }
        if (_codeFilter.value!!.length == 20) {
            if (_localityFilter.value == null || _localityFilter.value!!.isEmpty() || _roomFilter.value == null || _roomFilter.value!!.isEmpty()) {
                showLocationNotSelectedModalWindow();
                _codeFilter.value = ""
                return;
            }
            var propertyNumber: String =
                _codeFilter.value!!.subSequence(4, 16).toString().toLong().toString()
            var subnumber: String =
                _codeFilter.value!!.subSequence(16, 20).toString().toLong().toString()

            val selectedList = _properties.value!!.filter {
                it.propertyNumber == propertyNumber && it.subnumber == subnumber
            }
            if (selectedList.isEmpty()) {
                propertyNumber = "000000000000".subSequence(0, 12 - propertyNumber.length)
                    .toString() + propertyNumber
                subnumber = "0000".subSequence(0, 4 - subnumber.length).toString() + subnumber
                propertyRepository.save(
                    PropertyEntity(
                        propertyNumber = propertyNumber,
                        subnumber = subnumber,
                        inventoryId = _inventoryId.value!!,
                        recordStatus = 'N',
                        localityNew = _localityFilter.value!!,
                        roomNew = _roomFilter.value!!,
                        personalNumberNew = _userFilter.value!!,
                        isNew = true
                    )
                )


                navController.navigate(
                    Routes.PROPERTY_DETAIL.withArgs(
                        "-1",
                    ) + "?locality=" + _localityFilter.value!! + "&room=" + _roomFilter.value!! + "&user=" + _userFilter.value!! + "&inventoryId=" + _inventoryId.value!! + "&statusFilter=" + _statusFilter.value + "&isManual=" + false.toString() + "&propertyNumber=" + propertyNumber + "&subnumber=" + subnumber
                )


            } else {
                if (_scanWithoutDetail.value!!) {
                    val propertyToUpdate = selectedList.first()
                    propertyToUpdate.let {
                        it.localityNew =
                            if (_localityFilter.value!!.isNotEmpty()) _localityFilter.value!! else if (it.localityNew.isNotEmpty()) it.localityNew else it.locality
                        it.roomNew =
                            if (_roomFilter.value!!.isNotEmpty()) _roomFilter.value!! else if (it.roomNew.isNotEmpty()) it.roomNew else it.room
                        it.personalNumberNew =
                            if (_userFilter.value!!.isNotEmpty()) _userFilter.value!! else if (it.personalNumberNew.isNotEmpty()) it.personalNumberNew else it.personalNumber
                        if (it.locality == it.localityNew && it.room == it.roomNew && it.personalNumber == it.personalNumberNew) {
                            it.recordStatus = 'S'
                        } else {
                            it.recordStatus = 'Z'
                        }
                    }
                    propertyRepository.update(property = propertyToUpdate)
                    filterOutValues()
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

    fun deleteFilters() {
        _localityFilter.value = ""
        _roomFilter.value = ""
        _userFilter.value = ""
        filterOutValues()
    }

    fun filterOutValues() {
        if (_properties.value == null || _properties.value!!.isEmpty()) {
            _filteredProperties.value = emptyList()
        } else {
            _filteredProperties.value = _properties.value!!.filter {
                (_statusFilter.value == 'U' && "XC".contains(it.recordStatus)
                        && (_localityFilter.value == null || _localityFilter.value!!.isEmpty() || it.locality == _localityFilter.value || (_localityFilter.value == "ziadna" && it.locality.isEmpty()))
                        && (_roomFilter.value == null || _roomFilter.value!!.isEmpty() || it.room == _roomFilter.value || (_roomFilter.value == "ziadna" && it.room.isEmpty()))
                        && (_userFilter.value == null || _userFilter.value!!.isEmpty() || it.personalNumber == _userFilter.value))
                        || ((_statusFilter.value == 'P' && "SZN".contains(it.recordStatus))
                        && (_localityFilter.value == null || _localityFilter.value!!.isEmpty() || it.localityNew == _localityFilter.value || (_localityFilter.value == "ziadna" && it.localityNew.isEmpty()))
                        && (_roomFilter.value == null || _roomFilter.value!!.isEmpty() || it.roomNew == _roomFilter.value || (_roomFilter.value == "ziadna" && it.roomNew.isEmpty()))
                        && (_userFilter.value == null || _userFilter.value!!.isEmpty() || it.personalNumberNew == _userFilter.value))
            }
        }
        countUnprocessed()
        countProcessed()
    }

    fun onSelectProperty(id: Long) {
        if (roomFilter.value == null || roomFilter.value!!.isEmpty() || localityFilter.value == null || localityFilter.value!!.isEmpty()) {
            showLocationNotSelectedModalWindow()
            return
        }
        if (id < 0) {
            val subnumber: String
            val newProperties = _properties.value!!.filter {
                it.propertyNumber == "NOVY"
            }
            if (newProperties.isEmpty()) {
                subnumber = "1"
            } else {
                subnumber = (max(newProperties.map { it.subnumber.toInt() }) + 1).toString()
            }
            propertyRepository.save(
                PropertyEntity(
                    propertyNumber = "NOVY",
                    subnumber = subnumber,
                    inventoryId = _inventoryId.value!!,
                    recordStatus = 'N',
                    localityNew = _localityFilter.value!!,
                    roomNew = _roomFilter.value!!,
                    personalNumberNew = _userFilter.value!!,
                    isNew = true
                )
            )
            navController.navigate(
                Routes.PROPERTY_DETAIL.withArgs(
                    "-1",
                ) + "?locality=" + _localityFilter.value!! + "&room=" + _roomFilter.value!! + "&user=" + _userFilter.value!! + "&inventoryId=" + _inventoryId.value!! + "&statusFilter=" + _statusFilter.value + "&isManual=" + true.toString() + "&propertyNumber=" + "NOVY" + "&subnumber=" + subnumber
            )
        } else {
            navController.navigate(
                Routes.PROPERTY_DETAIL.withArgs(
                    id.toString(),
                ) + "?locality=" + _localityFilter.value!! + "&room=" + _roomFilter.value!! + "&user=" + _userFilter.value!! + "&inventoryId=" + _inventoryId.value!! + "&statusFilter=" + _statusFilter.value + "&isManual=" + true.toString()
            )
        }
    }

    private fun showLocationNotSelectedModalWindow() {
        _errorHeader.value = "Nie je zadaná miestnosť"
        _errorText.value = "Nie je možné spracovať položku, kým nie je zvolaná miestnosť"
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
        _codebookSelectionViewData.value = allCodebooksRepository.allLocalities.value?.plus(
            LocalityCodebookEntity("ziadna", "Žiadna lokalita")
        )
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
        _codebookSelectionViewData.value =
            allCodebooksRepository.allRooms.value?.plus(
                RoomCodebookEntity(
                    "ziadna",
                    "ziadna",
                    "Žiadna miestnosť"
                )
            );
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
        _codeFilterLocality.value = if (locality.isBlank()) "ziadna" else locality
        _codeFilterRoom.value = if (room.isBlank()) "ziadna" else room
        filterOutValues()
    }

    fun countUnprocessed() {
        _unprocessedCount.value = if (_properties.value != null)
            _properties.value!!.count {
                (it.recordStatus == 'C' || it.recordStatus == 'X')
                        && (_localityFilter.value == null || _localityFilter.value!!.isEmpty() || it.locality == _localityFilter.value || (_localityFilter.value == "ziadna" && it.locality.isEmpty()))
                        && (_roomFilter.value == null || _roomFilter.value!!.isEmpty() || it.room == _roomFilter.value || (_roomFilter.value == "ziadna" && it.room.isEmpty()))
                        && (_userFilter.value == null || _userFilter.value!!.isEmpty() || it.personalNumber == _userFilter.value)

            } else 0

    }

    fun countProcessed() {
        _processedCount.value = if (_properties.value != null)
            _properties.value!!.count {
                (it.recordStatus == 'S' || it.recordStatus == 'Z' || it.recordStatus == 'N')
                        && (_localityFilter.value == null || _localityFilter.value!!.isEmpty() || it.localityNew == _localityFilter.value || (_localityFilter.value == "ziadna" && it.localityNew.isEmpty()))
                        && (_roomFilter.value == null || _roomFilter.value!!.isEmpty() || it.roomNew == _roomFilter.value || (_roomFilter.value == "ziadna" && it.roomNew.isEmpty()))
                        && (_userFilter.value == null || _userFilter.value!!.isEmpty() || it.personalNumberNew == _userFilter.value)

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
            _loadingState.value = "Spracúvajú sa dáta..."
            val toSendProperties = propertyRepository.searchByInventoryIdResult.value!!
                .filter {
                    "SZN".contains(it.recordStatus)
                }

            var batchesCount =
                toSendProperties.size / BATCH_SIZE + if (toSendProperties.size % BATCH_SIZE == 0) 0 else 1;

            for (i in 0 until batchesCount) {
                _loadingState.value = "Odosiela sa ${i + 1}. dávka..."
                val toSendBatch = toSendProperties.subList(
                    i * BATCH_SIZE,
                    if (BATCH_SIZE * (i + 1) <= toSendProperties.size) BATCH_SIZE * (i + 1) else toSendProperties.size
                )

                val responseStatus =
                    if (toSendProperties.isNotEmpty()) Client.submitProcessedProperties(
                        inventoryRepository.allData.value!!.filter {
                            it.id == toSendProperties[0].inventoryId
                        }[0],
                        toSendBatch
                    ) else HttpStatusCode.Created
                if (responseStatus != HttpStatusCode.Created) {
                    _loadingData.value = false
                    _errorHeader.value = "Chyba!"
                    _errorText.value =
                        "Nastala chyba - položby sa nepodarilo odoslať na server. Chyba: ${responseStatus.value} - ${responseStatus.description}, "
                    return@launch
                }
            }
            _loadingState.value = "Dáta boli odoslané..."
            _loadingState.value = "Resetuje sa lokálna databáza..."
            propertyRepository.deleteAll()
            navController.navigate(Routes.INVENTORY_LIST.value)
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