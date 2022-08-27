package sk.msvvas.sofia.fam.offline.ui.views.property.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.repository.PropertyRepository

//TODO
class PropertyDetailViewModel(
    private val propertyRepository: PropertyRepository,
    val id: Long
) : ViewModel() {

    init {
        propertyRepository.findById(id)
    }

    private val _property = propertyRepository.searchResult
    val property: LiveData<PropertyEntity> = _property

    private val _isCodebookSelectionViewShown = MutableLiveData(false)
    val isCodebookSelectionViewShown: LiveData<Boolean> = _isCodebookSelectionViewShown

}