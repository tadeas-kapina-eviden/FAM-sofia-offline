package sk.msvvas.sofia.fam.offline.ui.views.inventory.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sk.msvvas.sofia.fam.offline.data.database.FamOfflineDatabase
import sk.msvvas.sofia.fam.offline.data.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.repository.InventoryRepository

class InventoryListViewModel(
    context: Context
) : ViewModel() {
    val database = FamOfflineDatabase.getInstance(context = context)
    private val inventoryRepository = InventoryRepository(database.inventoryDao())

    private val _inventories: LiveData<List<InventoryEntity>> = inventoryRepository.allData
    val inventories: LiveData<List<InventoryEntity>> = _inventories
}