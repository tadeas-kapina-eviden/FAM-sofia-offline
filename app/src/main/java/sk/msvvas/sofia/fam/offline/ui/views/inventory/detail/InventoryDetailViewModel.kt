package sk.msvvas.sofia.fam.offline.ui.views.inventory.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.repository.PropertyRepository


class InventoryDetailViewModel(
    private val propertyRepository: PropertyRepository
) : ViewModel() {
    private val _properties: LiveData<List<PropertyEntity>> =
        propertyRepository.searchByInventoryIdResult
    val properties: LiveData<List<PropertyEntity>> = _properties

    fun findInventoryProperties(inventoryId: String) {
        propertyRepository.findByInventoryId(inventoryId = inventoryId)
    }

}