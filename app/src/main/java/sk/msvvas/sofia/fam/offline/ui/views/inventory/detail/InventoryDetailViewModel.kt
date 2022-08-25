package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.repository.PropertyRepository


class InventoryDetailViewModel(
    private val propertyRepository: PropertyRepository
) : ViewModel() {
    private val _properties: LiveData<List<PropertyEntity>> =
        propertyRepository.searchByInventoryIdResult
    val properties: LiveData<List<PropertyEntity>> = _properties

    private val _isFiltersShown = MutableLiveData(false)
    val isFiltersShown: LiveData<Boolean> = _isFiltersShown

    private val _inventoryId = MutableLiveData("")
    val inventoryId: LiveData<String> = _inventoryId

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
        /*TODO*/
    }

    fun runFilters() {
        /*TODO*/
    }

}