package sk.msvvas.sofia.fam.offline.ui.views.property.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.*
import sk.msvvas.sofia.fam.offline.data.application.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.data.application.repository.codebook.AllCodebooksRepository
import sk.msvvas.sofia.fam.offline.ui.navigation.Routes

/**
 * View model for property detail view
 * @param propertyRepository repository for working with property table
 * @param allCodebooksRepository repository for working with all codebooks tables
 * @param id id of property
 * @param navController navigation controller of application
 * @param localityFilter last locality filter from inventory detail view (used as new locality for property)
 * @param roomFilter last room filter from inventory detail view (used as new room for property)
 * @param userFilter last user filter from inventory detail view (used as new user for property)
 * @param inventoryId inventory id of inventory in which is property
 * @param statusFilter last status filter in inventory detail for going back to inventory detail
 * @param isManual holds if property is scanned or manually selected from list
 */
class PropertyDetailViewModel(
    private val propertyRepository: PropertyRepository,
    private val allCodebooksRepository: AllCodebooksRepository,
    val id: Long,
    private val navController: NavController,
    private val localityFilter: String,
    private val roomFilter: String,
    private val userFilter: String,
    private val inventoryId: String,
    private val statusFilter: Char,
    propertyNumber: String = "",
    subnumber: String = "",
    val isManual: Boolean,
) : ViewModel() {

    private val _property: MutableLiveData<PropertyEntity> = MutableLiveData(null)
    val property: LiveData<PropertyEntity>

    /**
     * Tells if all required variables are properly initialized
     */
    private var varsInitialized = false

    private val _locality = MutableLiveData(if (localityFilter != "ziadna") localityFilter else "")
    val locality: LiveData<String> = _locality

    private val _room = MutableLiveData(if (roomFilter != "ziadna") roomFilter else "")
    val room: LiveData<String> = _room

    private val _user = MutableLiveData(userFilter)
    val user: LiveData<String> = _user

    private val _userName = MutableLiveData("")
    val userName: LiveData<String> = _userName

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

    private val _deleteCodebook = MutableLiveData {}
    val deleteCodebook: LiveData<() -> Unit> = _deleteCodebook

    private val _checkFormatCodebook = MutableLiveData<(String) -> Boolean> {
        true
    }

    private val _wrongFormatTextCodebook = MutableLiveData("")
    val wrongFormatTextCodebook: LiveData<String> = _wrongFormatTextCodebook

    val checkFormatCodebook: LiveData<(String) -> Boolean> = _checkFormatCodebook

    private val _codebookSelectionViewLastValue = MutableLiveData("")
    val codebookSelectionViewLastValue: LiveData<String> = _codebookSelectionViewLastValue

    private val _errorHeader = MutableLiveData("")
    val errorHeader: LiveData<String> = _errorHeader

    private val _errorText = MutableLiveData("")
    val errorText: LiveData<String> = _errorText

    /**
     * Loads property from database and checks if it is new.
     */
    init {
        property = _property
        if (id < 0) {
            CoroutineScope(Dispatchers.Main).launch {
                _property.value = withContext(Dispatchers.IO) {
                    propertyRepository.getByIdentifiers(
                        propertyNumber,
                        subnumber
                    )
                }
            }
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                _property.value = withContext(Dispatchers.IO) {
                    propertyRepository.findById(id)
                }
            }
        }
        if (_property.value != null) {
            if (_property.value!!.localityNew == "ziadna") {
                _property.value!!.localityNew = ""
            }
            if (_property.value!!.roomNew == "ziadna") {
                _property.value!!.roomNew = ""
            }
            if (_property.value!!.locality == "ziadna") {
                _property.value!!.locality = ""
            }
        }

        if (userFilter.isNotBlank()) {
            val userCodebook = allCodebooksRepository.allUsers.value!!.filter {
                it.id == userFilter
            }.firstOrNull()
            if (userCodebook != null) {
                _userName.value = userCodebook!!.fullName
            }
        }
    }

    /**
     * Close codebook selection view
     */
    fun closeCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = false
    }

    /**
     * Show locality codebook selection view and init all parameters and functions for it
     */
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
        _deleteCodebook.value = {
            closeCodebookSelectionView()
            property.value!!.localityNew = ""
            _locality.value = ""
        }
        _checkFormatCodebook.value = {
            it.length <= 10
        }
        _wrongFormatTextCodebook.value = "Musí obsahovať maximálne 10 písmen a číslic!"
    }

    /**
     * Show room codebook selection view and init all parameters and functions for it
     */
    fun showRoomCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = allCodebooksRepository.allRooms.value!!.filter {
            if (locality.value == null || locality.value!!.isBlank()) true else it.localityId == locality.value!!
        }
        _codebookSelectionViewIdGetter.value = { (it as RoomCodebookEntity).id }
        _codebookSelectionViewDescriptionGetter.value =
            { (it as RoomCodebookEntity).description }
        _codebookSelectionViewLastValue.value = _property.value?.roomNew
        _selectCodebook.value = {
            closeCodebookSelectionView()
            _room.value = it
            property.value!!.roomNew = it
        }
        _deleteCodebook.value = {
            closeCodebookSelectionView()
            property.value!!.roomNew = ""
            _room.value = ""
        }
        _checkFormatCodebook.value = {
            it.length <= 8
        }
        _wrongFormatTextCodebook.value = "Musí obsahovať maximálne 8 písmen a číslic!"
    }

    /**
     * Show user codebook selection view and init all parameters and functions for it
     */
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

            val userCodebook = allCodebooksRepository.allUsers.value!!.filter { it2 ->
                it2.id == it
            }.firstOrNull()
            if (userCodebook != null) {
                _userName.value = userCodebook.fullName
            }
        }
        _deleteCodebook.value = {
            closeCodebookSelectionView()
            property.value!!.personalNumberNew = ""
            _user.value = ""
            _userName.value = ""
        }
        _checkFormatCodebook.value = {
            it.length == 8 && it.toIntOrNull() != null
        }
        _wrongFormatTextCodebook.value = "Musí obsahovať presne 8 číslic!"
    }

    /**
     * Show place codebook selection view and init all parameters and functions for it
     */
    fun showPlaceCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = allCodebooksRepository.allPlaces.value
        _codebookSelectionViewIdGetter.value = { (it as PlaceCodebookEntity).id }
        _codebookSelectionViewDescriptionGetter.value =
            { (it as PlaceCodebookEntity).description }
        _codebookSelectionViewLastValue.value = _property.value?.workplaceNew
        _selectCodebook.value = {
            closeCodebookSelectionView()
            property.value!!.workplaceNew = it
            _place.value = it
        }
        _deleteCodebook.value = {
            closeCodebookSelectionView()
            property.value!!.workplaceNew = ""
            _place.value = ""
        }
        _checkFormatCodebook.value = {
            true
        }
    }

    /**
     * Show fixed note codebook selection view and init all parameters and functions for it
     */
    fun showFixedNoteCodebookSelectionView() {
        _isCodebookSelectionViewShown.value = true
        _codebookSelectionViewData.value = allCodebooksRepository.allNotes.value
        _codebookSelectionViewIdGetter.value = { (it as NoteCodebookEntity).id }
        _codebookSelectionViewDescriptionGetter.value =
            { (it as NoteCodebookEntity).description }
        _codebookSelectionViewLastValue.value = _property.value?.fixedNote
        _selectCodebook.value = {
            closeCodebookSelectionView()
            val note = allCodebooksRepository.allNotes.value!!.filter { note ->
                note.id == it
            }[0]
            _fixedNote.value = "${note.id}/${note.description}"
            property.value!!.fixedNote = it
        }
        _deleteCodebook.value = {
            closeCodebookSelectionView()
            property.value!!.fixedNote = ""
            _fixedNote.value = ""
        }
        _checkFormatCodebook.value = {
            true
        }
    }

    /**
     * Show variable note input view and init all parameters and functions for it
     */
    fun showVariableNoteInputView() {
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
        _deleteCodebook.value = {
            closeCodebookSelectionView()
            property.value!!.variableNote = ""
            _variableNote.value = ""
        }
        _checkFormatCodebook.value = {
            it.length <= 50
        }

        _wrongFormatTextCodebook.value = "Musí obsahovať maximálne 50 písmen a číslic!"
    }

    /**
     * Init non-initialized variables after class initialization
     */
    fun lateInitVarsData() {
        if (!varsInitialized) {
            _property.value!!.let {
                if (_user.value == "") {
                    if (it.personalNumberNew != "") {
                        _user.value = it.personalNumberNew
                    } else {
                        _user.value = it.personalNumber
                    }
                }

                if (_user.value!!.isNotBlank()) {
                    val userCodebook = allCodebooksRepository.allUsers.value!!.filter { it2 ->
                        it2.id == _user.value
                    }.firstOrNull()
                    if (userCodebook != null) {
                        _userName.value = userCodebook!!.fullName
                    }
                }

                _place.value = it.workplaceNew

                if (it.fixedNote != "") {
                    val note = allCodebooksRepository.allNotes.value!!.filter { note ->
                        note.id == it.fixedNote
                    }[0]
                    _fixedNote.value = "${note.id}/${note.description}"
                }
                _variableNote.value = it.variableNote

                println("osoba: " + it.personalNumber)
                println("osoba n: " + it.personalNumberNew)
                println("lokalita: " + it.locality)
                println("lokalita: " + it.localityNew)
                println("room: " + it.room)
                println("room n: " + it.roomNew)
                println("pracovisko: " + it.workplace)
                println("pracovisko n: " + it.workplaceNew)
            }

            varsInitialized = true
        }
    }

    /**
     * Submit and save property
     */
    fun submit() {
        _property.value!!.let {
            it.localityNew = _locality.value!!
            it.roomNew = _room.value!!
            it.personalNumberNew = _user.value!!
            it.workplaceNew = _place.value!!
            if (!_property.value!!.isNew) {
                if (it.recordStatus != 'N') {
                    if (it.locality == it.localityNew
                        && it.room == it.roomNew
                        && it.personalNumber == it.personalNumberNew
                        && it.workplace == it.workplaceNew
                    ) {
                        it.recordStatus = 'S'
                    } else {
                        it.recordStatus = 'Z'
                    }
                }
                it.isManual = isManual
            } else {
                if (it.variableNote.trim().isEmpty()) {
                    _errorHeader.value = "Vyplňte vlastnú poznámku"
                    _errorText.value =
                        "Pri neidentifikovanom majetku je nutné vyplniť vlastnú poznámku - meno/popis majetku"
                    return
                } else {
                    it.textMainNumber = it.variableNote
                    it.isManual = isManual
                }
            }
            navController.navigate(
                Routes.INVENTORY_DETAIL.withArgs(it.inventoryId) + "?locality=" + localityFilter + "&room=" + roomFilter + "&user=" + userFilter + "&statusFilter=" + statusFilter
            )
        }
        propertyRepository.update(_property.value!!)
    }

    /**
     * Rollback property after - delete saved data
     */
    fun rollback() {
        _property.value!!.let {
            if (!it.isNew) {
                it.localityNew = ""
                it.roomNew = ""
                it.personalNumberNew = ""
                it.workplaceNew = ""
                it.recordStatus = 'C'
                it.variableNote = ""
                it.fixedNote = ""
                propertyRepository.update(property = it)
            } else {
                propertyRepository.delete(property = it)
            }
            navController.navigate(
                Routes.INVENTORY_DETAIL.withArgs(it.inventoryId) + "?locality=" + localityFilter + "&room=" + roomFilter + "&user=" + userFilter + "&statusFilter=" + statusFilter
            )
        }
    }

    /**
     * Function for closing error alert
     */
    fun closeErrorAlert() {
        _errorHeader.value = ""
        _errorText.value = ""
    }

    /**
     * Go back to inventory detail view
     */
    fun goBack() {
        navController.navigate(
            Routes.INVENTORY_DETAIL.withArgs(_property.value!!.inventoryId) + "?locality=" + localityFilter + "&room=" + roomFilter + "&user=" + userFilter + "&statusFilter=" + statusFilter
        )
    }

}