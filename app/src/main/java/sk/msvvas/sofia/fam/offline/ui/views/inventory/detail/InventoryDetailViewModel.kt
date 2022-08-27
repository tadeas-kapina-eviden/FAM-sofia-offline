package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.data.repository.codebook.AllCodebooksRepository


class InventoryDetailViewModel(
    private val propertyRepository: PropertyRepository,
    private val allCodebooksRepository: AllCodebooksRepository,
    val changeToDetailOfProperty: (Long) -> Unit
) : ViewModel() {

    private val _properties: LiveData<List<PropertyEntity>> =
        propertyRepository.searchByInventoryIdResult
    val properties: LiveData<List<PropertyEntity>> = _properties

    private val _filteredProperties = MutableLiveData(listOf<PropertyEntity>())
    val filteredProperties: LiveData<List<PropertyEntity>> = _filteredProperties

    private val _isFiltersShown = MutableLiveData(false)
    val isFiltersShown: LiveData<Boolean> = _isFiltersShown

    private val _inventoryId = MutableLiveData("")
    val inventoryId: LiveData<String> = _inventoryId

    private val _statusFilter = MutableLiveData('S')
    val statusFilter: LiveData<Char> = _statusFilter

    private val _codeFilter = MutableLiveData("")
    val codeFilter: LiveData<String> = _codeFilter

    private val _localityFilter = MutableLiveData("")
    val localityFilter: LiveData<String> = _localityFilter

    private val _roomFilter = MutableLiveData("")
    val roomFilter: LiveData<String> = _roomFilter

    private val _userFilter = MutableLiveData("")
    val userFilter: LiveData<String> = _userFilter

    private val _scanWithoutDetail = MutableLiveData(false)
    val scanWithoutDetail: LiveData<Boolean> = _scanWithoutDetail

    private val _errorHeader = MutableLiveData("")
    val errorHeader: LiveData<String> = _errorHeader

    private val _errorText = MutableLiveData("")
    val errorText: LiveData<String> = _errorText

    private val _codeFilterLocality = MutableLiveData("")
    var codeFilterLocality: LiveData<String> = _codeFilterLocality

    private val _codeFilterRoom = MutableLiveData("")
    var codeFilterRoom: LiveData<String> = _codeFilterRoom

    fun findInventoryProperties(inventoryId: String) {
        _inventoryId.value = inventoryId
        propertyRepository.findByInventoryId(inventoryId = inventoryId)
    }

    fun onFiltersShowClick() {
        _isFiltersShown.value = !(_isFiltersShown.value!!)
    }

    fun onCodeFilterChange(newCode: String) {
        _codeFilter.value = newCode
    }

    fun onLocalityFilterChange(newCode: String) {
        _localityFilter.value = newCode
    }

    fun onRoomFilterChange(newCode: String) {
        _roomFilter.value = newCode
    }

    fun onUserFilterChange(newCode: String) {
        _userFilter.value = newCode
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
                //TODO show detail of new property
            } else {
                if (_scanWithoutDetail.value!!) {
                    //TODO change the status of property
                } else {
                    changeToDetailOfProperty(selectedList[0].id)
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
        if (allCodebooksRepository.allLocalities.value!!.any { it.id == _codeFilterLocality.value } && allCodebooksRepository.allRooms.value!!.any { it.id == codeFilterRoom.value }) {
            _localityFilter.value = _codeFilterLocality.value
            _roomFilter.value = codeFilterRoom.value
        } else {
            _errorHeader.value = "Chyba"
            _errorText.value = "Naskenovaná nesprávna lokalita! Nenachádza sa v zozname."
        }
        _codeFilterLocality.value = ""
        _codeFilterRoom.value = ""
    }

    fun runFilters() {
        filterOutValues()
    }

    fun filterOutValues() {
        if (_properties.value == null || _properties.value!!.isEmpty()) {
            _filteredProperties.value = emptyList()
        } else {
            _filteredProperties.value = _properties.value!!.filter {
                (_statusFilter.value == null || it.recordStatus == _statusFilter.value)
                        && (_localityFilter.value == null || _localityFilter.value!!.isEmpty() || it.locality == _localityFilter.value)
                        && (_roomFilter.value == null || _roomFilter.value!!.isEmpty() || it.room == _roomFilter.value)
                        && (_userFilter.value == null || _userFilter.value!!.isEmpty() || it.personalNumber == _userFilter.value)
            }
        }
    }

    fun closeErrorAlert() {
        _errorHeader.value = ""
        _errorText.value = ""
    }
}