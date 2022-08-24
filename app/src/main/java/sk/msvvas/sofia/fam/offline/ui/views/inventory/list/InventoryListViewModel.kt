package sk.msvvas.sofia.fam.offline.ui.views.inventory.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sk.msvvas.sofia.fam.offline.data.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.repository.InventoryRepository

class InventoryListViewModel(
    inventoryRepository: InventoryRepository,
    private val viewChange: (String) -> Unit
) : ViewModel() {
    private val _inventories: LiveData<List<InventoryEntity>> = inventoryRepository.allData
    val inventories: LiveData<List<InventoryEntity>> = _inventories

    fun onSelectInventory(inventoryId: String) {
        viewChange(inventoryId)
    }
}