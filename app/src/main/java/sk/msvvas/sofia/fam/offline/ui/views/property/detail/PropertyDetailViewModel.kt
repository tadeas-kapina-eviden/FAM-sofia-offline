package sk.msvvas.sofia.fam.offline.ui.views.property.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import sk.msvvas.sofia.fam.offline.data.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.entities.codebook.*
import sk.msvvas.sofia.fam.offline.data.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.data.repository.codebook.AllCodebooksRepository
import sk.msvvas.sofia.fam.offline.ui.views.navigation.Routes

//TODO
class PropertyDetailViewModel(
    private val propertyRepository: PropertyRepository,
    private val allCodebooksRepository: AllCodebooksRepository,
    val id: Long,
    private val navController: NavController,
    localityFilter: String,
    roomFilter: String,
    userFilter: String
) : ViewModel() {

    init {
        propertyRepository.findById(id)
    }

    private val _property = propertyRepository.searchResult
    val property: LiveData<PropertyEntity> = _property

    private var varsInitialized = false

    private val _locality = MutableLiveData(localityFilter)
    val locality: LiveData<String> = _locality

    private val _room = MutableLiveData(roomFilter)
    val room: LiveData<String> = _room

    private val _user = MutableLiveData(userFilter)
    val user: LiveData<String> = _user

    private val _place = MutableLiveData("")
    val place: LiveData<String> = _place

    private val _fixedNote = MutableLiveData("")
    val fixedNote: LiveData<String> = _fixedNote

    private val _variableNote = MutableLiveData("")
    val variableNote: LiveData<String> = _variableNote

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

    private val _codebookSelectionViewLastValue = MutableLiveData("")
    val codebookSelectionViewLastValue: LiveData<String> = _codebookSelectionViewLastValue

    fun closeCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = false
    }

    fun showLocationCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = allCodebooksRepository.allLocalities.value
        _codebookSelectionViewIdGetter.value = { (it as LocalityCodebookEntity).id }
        _codebookSelectionViewDescriptionGetter.value =
            { (it as LocalityCodebookEntity).description }
        _codebookSelectionViewLastValue.value = _property.value?.localityNew
        _selectCodebook.value = {
            closeCodebookSelectionView()
            property.value!!.localityNew = it
            _locality.value = it
        }
    }

    fun showRoomCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = allCodebooksRepository.allRooms.value
        _codebookSelectionViewIdGetter.value = { (it as RoomCodebookEntity).id }
        _codebookSelectionViewDescriptionGetter.value =
            { (it as RoomCodebookEntity).description }
        _codebookSelectionViewLastValue.value = _property.value?.roomNew
        _selectCodebook.value = {
            closeCodebookSelectionView()
            _room.value = it
            property.value!!.roomNew = it
        }
    }

    fun showUserCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = allCodebooksRepository.allUsers.value
        _codebookSelectionViewIdGetter.value = { (it as UserCodebookEntity).id }
        _codebookSelectionViewDescriptionGetter.value =
            { (it as UserCodebookEntity).fullName }
        _codebookSelectionViewLastValue.value = _property.value?.personalNumberNew
        _selectCodebook.value = {
            closeCodebookSelectionView()
            _user.value = it
            property.value!!.personalNumberNew = it
        }
    }

    fun showPlaceCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = allCodebooksRepository.allPlaces.value
        _codebookSelectionViewIdGetter.value = { (it as PlacesCodebookEntity).id }
        _codebookSelectionViewDescriptionGetter.value =
            { (it as PlacesCodebookEntity).description }
        _codebookSelectionViewLastValue.value = _property.value?.workplaceNew
        _selectCodebook.value = {
            closeCodebookSelectionView()
            _place.value = it
            property.value!!.workplaceNew = it
        }
    }

    fun showFixedNoteCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = allCodebooksRepository.allNotes.value
        _codebookSelectionViewIdGetter.value = { (it as NoteCodebookEntity).id }
        _codebookSelectionViewDescriptionGetter.value =
            { (it as NoteCodebookEntity).description }
        _codebookSelectionViewLastValue.value = _property.value?.fixedNote
        _selectCodebook.value = {
            closeCodebookSelectionView()
            _fixedNote.value = it
            property.value!!.fixedNote = it
        }
    }

    fun showVariableNoteCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = emptyList()
        _codebookSelectionViewIdGetter.value = { "" }
        _codebookSelectionViewDescriptionGetter.value =
            { "" }
        _codebookSelectionViewLastValue.value = _property.value?.variableNote
        _selectCodebook.value = {
            closeCodebookSelectionView()
            _variableNote.value = it
            property.value!!.variableNote = it
        }
    }

    fun lateInitVarsData() {
        if (!varsInitialized) {
            _property.value!!.let {
                if ("XC".contains(it.recordStatus)) {
                    it.localityNew = if (_locality.value == "") it.locality else _locality.value!!
                    it.roomNew = if (_room.value == "") it.room else _room.value!!
                    it.personalNumberNew =
                        if (_user.value == "") it.personalNumber else _user.value!!
                    it.workplaceNew = it.workplace
                }
                if (_locality.value == "")
                    _locality.value = it.localityNew
                if (_room.value == "")
                    _room.value = it.roomNew
                if (_user.value == "")
                    _user.value = it.personalNumberNew
                _place.value = it.workplaceNew
                _fixedNote.value = it.fixedNote
                _variableNote.value = it.variableNote
            }
        }
    }

    fun submit() {
        _property.value!!.let {
            if (it.locality == it.localityNew
                && it.room == it.roomNew
                && it.personalNumber == it.personalNumberNew
                && it.workplace == it.workplaceNew
            ) {
                it.recordStatus = 'S'
            } else {
                it.recordStatus = 'Z'
            }

            propertyRepository.update(property = it)
            navController.navigate(Routes.INVENTORY_DETAIL.withArgs(it.inventoryId))
        }
    }

    fun rollback() {
        _property.value!!.let {
            it.localityNew = ""
            it.roomNew = ""
            it.personalNumberNew = ""
            it.workplaceNew = ""
            it.recordStatus = 'C'
            it.variableNote = ""
            it.fixedNote = ""
            propertyRepository.update(property = it)
            navController.navigate(Routes.INVENTORY_DETAIL.withArgs(it.inventoryId))
        }
    }

}