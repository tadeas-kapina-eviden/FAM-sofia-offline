package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.*

/**
 * Common repository for all codebooks
 * Parameters are all codebooks
 * @param localityCodebookRepository
 * @param noteCodebookRepository
 * @param placeCodebookRepository
 * @param roomCodebookRepository
 * @param userCodebookRepository
 */
class AllCodebooksRepository(
    private val localityCodebookRepository: LocalityCodebookRepository,
    private val noteCodebookRepository: NoteCodebookRepository,
    private val placeCodebookRepository: PlaceCodebookRepository,
    private val roomCodebookRepository: RoomCodebookRepository,
    private val userCodebookRepository: UserCodebookRepository
) {
    /**
     * All items from localityCodebook table
     */
    val allLocalities = MutableLiveData<List<LocalityCodebookEntity>>()

    /**
     * All items from roomCodebook table
     */
    val allRooms = MutableLiveData<List<RoomCodebookEntity>>()

    /**
     * All items from userCodebook table
     */
    val allUsers = MutableLiveData<List<UserCodebookEntity>>()

    /**
     * All items from placeCodebook table
     */
    val allPlaces = MutableLiveData<List<PlaceCodebookEntity>>()

    /**
     * All items from noteCodebook table
     */
    val allNotes = MutableLiveData<List<NoteCodebookEntity>>()

    /**
     * Get all codebooks values
     */
    fun getAll() {
        CoroutineScope(Dispatchers.Main).launch {
            allLocalities.value = withContext(Dispatchers.IO) { localityCodebookRepository.getAll() }
            allRooms.value = withContext(Dispatchers.IO) { roomCodebookRepository.getAll() }
            allPlaces.value = withContext(Dispatchers.IO) { placeCodebookRepository.getAll() }
            allNotes.value = withContext(Dispatchers.IO) { noteCodebookRepository.getAll() }
            allUsers.value = withContext(Dispatchers.IO) { userCodebookRepository.getAll() }
        }
    }

    /**
     * Save one item to localityCodebook table
     * @param locality localityCodebook data
     */
    fun saveLocality(locality: LocalityCodebookEntity) {
        localityCodebookRepository.save(locality)
    }

    /**
     * Save one item to localityCodebook table
     * @param localities list of locality data
     */
    fun saveAllLocalities(localities: List<LocalityCodebookEntity>) {
        localityCodebookRepository.saveAll(localities)
    }

    /**
     * Save one item to roomCodebook table
     * @param room roomCodebook data
     */
    fun saveRoom(room: RoomCodebookEntity) {
        roomCodebookRepository.save(room)
    }

    /**
     * Save multiple items to roomCodebook table
     * @param rooms list of roomCodebook data
     */
    fun saveAllRooms(rooms: List<RoomCodebookEntity>) {
        roomCodebookRepository.saveAll(rooms)
    }

    /**
     * Save one item to placeCodebook table
     * @param place placeCodebook data
     */
    fun savePlace(place: PlaceCodebookEntity) {
        placeCodebookRepository.save(place)
    }

    /**
     * Save multiple items to roomCodebook table
     * @param places list of placeCodebook data
     */
    fun saveAllPlaces(places: List<PlaceCodebookEntity>) {
        placeCodebookRepository.saveAll(places)
    }

    /**
     * Save one item to userCodebook table
     * @param user userCodebook data
     */
    fun saveUser(user: UserCodebookEntity) {
        userCodebookRepository.save(user)
    }

    /**
     * Save multiple items to userCodebook table
     * @param users list of userCodebook data
     */
    fun saveAllUsers(users: List<UserCodebookEntity>) {
        userCodebookRepository.saveAll(users)
    }

    /**
     * Save one item to noteCodebook table
     * @param note noteCodebook data
     */
    fun saveNote(note: NoteCodebookEntity) {
        noteCodebookRepository.save(note)
    }

    /**
     * Save multiple items to noteCodebook table
     * @param notes list of noteCodebook data
     */
    fun saveAllNotes(notes: List<NoteCodebookEntity>) {
        noteCodebookRepository.saveAll(notes)
    }
}