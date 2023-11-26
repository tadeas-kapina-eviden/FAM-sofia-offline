package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.LocalityCodebookEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.RoomCodebookEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.UserCodebookEntity
import sk.msvvas.sofia.fam.offline.data.application.model.LocalityRoomCountPair
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

    companion object {
        const val BATCH_SIZE = 1000
    }

    private val _filteredProperties = MutableLiveData(listOf<PropertyEntity>())
    val filteredProperties: LiveData<List<PropertyEntity>> = _filteredProperties

    private val _localityRoomPairsCount = MutableLiveData(listOf<LocalityRoomCountPair>())
    val localityRoomPairsCount: LiveData<List<LocalityRoomCountPair>> = _localityRoomPairsCount

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

    init {
        allCodebooksRepository.getAll()
        CoroutineScope(Dispatchers.Main).launch {
            if (!submitInventory) {
                filterOutValues()
            } else if (withContext(Dispatchers.IO) {
                    propertyRepository.getInventoryId()
                } != null) {
                submitInventory(true)
            }
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
            val propertyId = _codeFilter.value!!
            CoroutineScope(Dispatchers.Main).launch {
                if (_localityFilter.value == null || _localityFilter.value!!.isEmpty() || _roomFilter.value == null || _roomFilter.value!!.isEmpty()) {
                    showLocationNotSelectedModalWindow()
                    _codeFilter.value = ""
                    return@launch
                }
                val propertyNumber: String =
                    propertyId.subSequence(4, 16).toString().toLong().toString()
                val subnumber: String =
                    propertyId.subSequence(16, 20).toString().toLong().toString()

                val selected = withContext(Dispatchers.IO) {
                    propertyRepository.getByIdentifiers(
                        propertyNumber,
                        subnumber
                    )
                }
                if (selected == null) {
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
                        selected.let {
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
                        propertyRepository.update(property = selected)
                        filterOutValues()
                    } else {
                        navController.navigate(
                            Routes.PROPERTY_DETAIL.withArgs(
                                selected.id.toString()
                            ) + "?locality=" + _localityFilter.value!! + "&room=" + _roomFilter.value!! + "&user=" + _userFilter.value!! + "&statusFilter=" + _statusFilter.value!! + "&isManual=" + false.toString()
                        )
                    }
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
        CoroutineScope(Dispatchers.Main).launch {
            if (statusFilter.value == 'P') {
                _filteredProperties.value = withContext(Dispatchers.IO) {
                    propertyRepository.findProcessedBySearchCriteria(
                        localityFilter.value,
                        roomFilter.value,
                        userFilter.value
                    )
                }
            }
            if (statusFilter.value == 'U') {
                _filteredProperties.value = withContext(Dispatchers.IO) {
                    propertyRepository.findUnprocessedBySearchCriteria(
                        localityFilter.value,
                        roomFilter.value,
                        userFilter.value
                    )
                }
            }
            if (statusFilter.value == 'S') {
                countLocalityRoomPairs()
            }
        }

        countUnprocessed()
        countProcessed()
    }

    fun onSelectProperty(id: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            if (roomFilter.value == null || roomFilter.value!!.isEmpty() || localityFilter.value == null || localityFilter.value!!.isEmpty()) {
                showLocationNotSelectedModalWindow()
                return@launch
            }
            if (id < 0) {
                val subnumber = withContext(Dispatchers.IO) { propertyRepository.countNEW() + 1 }
                propertyRepository.save(
                    PropertyEntity(
                        propertyNumber = "NOVY",
                        subnumber = subnumber.toString(),
                        inventoryId = _inventoryId.value!!,
                        recordStatus = 'N',
                        localityNew = if (_localityFilter.value!! != "ziadna") _localityFilter.value!! else "",
                        roomNew = if (_roomFilter.value!! != "ziadna") _roomFilter.value!! else "",
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
        countLocalityRoomPairs()
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
            allCodebooksRepository.allRooms.value?.filter {
                if (localityFilter.value == null || localityFilter.value!!.isBlank()) true else it.localityId == localityFilter.value!!
            }!!.plus(
                RoomCodebookEntity(
                    "ziadna",
                    "ziadna",
                    "Žiadna miestnosť"
                )
            )
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
        _codeFilterLocality.value = locality.ifBlank { "ziadna" }
        _codeFilterRoom.value = room.ifBlank { "ziadna" }
        filterOutValues()
    }

    private fun countUnprocessed() {
        CoroutineScope(Dispatchers.Main).launch {
            _unprocessedCount.value = withContext(Dispatchers.IO) {
                propertyRepository.getCountUnProcessedSearchCriteria(
                    localityFilter.value,
                    roomFilter.value,
                    userFilter.value
                )
            }
        }
    }

    private fun countProcessed() {
        CoroutineScope(Dispatchers.Main).launch {
            _processedCount.value = withContext(Dispatchers.IO) {
                propertyRepository.getCountProcessedSearchCriteria(
                    localityFilter.value,
                    roomFilter.value,
                    userFilter.value
                )
            }
        }
    }

    fun showExitModalWindow() {
        _exitModalShown.value = true
    }

    fun hideExitModalWindow() {
        _exitModalShown.value = false
    }

    fun submitInventory(fromStart: Boolean = false) {
        if (ClientData.username.isEmpty()) {
            requireLoginModalShow()
            submitInventoryConfirmModalHide()
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            _submitInventoryConfirmModalShown.value = false
            _loadingData.value = true
            _loadingState.value = "Spracúvajú sa dáta..."
            val toSendProperties =
                withContext(Dispatchers.IO) {
                    propertyRepository.findProcessedBySearchCriteria(
                        null,
                        null,
                        null
                    )
                }
            val batchesCount =
                toSendProperties.size / BATCH_SIZE + if (toSendProperties.size % BATCH_SIZE == 0) 0 else 1

            for (i in 0 until batchesCount) {
                _loadingState.value = "Odosiela sa ${i + 1}. dávka..."
                val toSendBatch = toSendProperties.subList(
                    i * BATCH_SIZE,
                    if (BATCH_SIZE * (i + 1) <= toSendProperties.size) BATCH_SIZE * (i + 1) else toSendProperties.size
                )

                val responseStatus =
                    if (toSendProperties.isNotEmpty()) Client.submitProcessedProperties(
                        withContext(Dispatchers.IO) { inventoryRepository.findById(toSendBatch[0].inventoryId) },
                        toSendBatch
                    ) else HttpStatusCode.Created
                if (responseStatus != HttpStatusCode.Created) {
                    if (!fromStart) {
                        _loadingData.value = false
                        _errorHeader.value = "Chyba!"
                        _errorText.value =
                            "Nastala chyba - položky sa nepodarilo odoslať na server. Chyba: ${responseStatus.value} - ${responseStatus.description}, "
                    }
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

    fun countLocalityRoomPairs() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = mutableListOf<LocalityRoomCountPair>()
            withContext(Dispatchers.IO) {
                propertyRepository.findLocalityRoomPairs().forEach {
                    val pair = LocalityRoomCountPair(
                        it.locality,
                        it.room
                    )
                    if (pair in result) {
                        val toAdd = result[result.indexOf(pair)]
                        toAdd.all++
                        toAdd.processed += if (it.processed) 1 else 0
                    } else {
                        pair.all = 1
                        pair.processed = if (it.processed) 1 else 0
                        result.add(pair)
                    }
                }
            }
            _localityRoomPairsCount.value = result.sortedBy {
                it.locality
            }
        }
    }
}