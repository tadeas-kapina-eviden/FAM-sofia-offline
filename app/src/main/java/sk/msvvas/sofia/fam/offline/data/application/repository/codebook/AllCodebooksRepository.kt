package sk.msvvas.sofia.fam.offline.data.application.repository.codebook

import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.*

class AllCodebooksRepository(
    private val localityCodebookRepository: LocalityCodebookRepository,
    private val noteCodebookRepository: NoteCodebookRepository,
    private val placeCodebookRepository: PlaceCodebookRepository,
    private val roomCodebookRepository: RoomCodebookRepository,
    private val userCodebookRepository: UserCodebookRepository
) {
    val allLocalities = localityCodebookRepository.allData
    val allRooms = roomCodebookRepository.allData
    val allUsers = userCodebookRepository.allData
    val allPlaces = placeCodebookRepository.allData
    val allNotes = noteCodebookRepository.allData

    fun saveLocality(locality: LocalityCodebookEntity) {
        localityCodebookRepository.save(locality)
    }

    fun saveAllLocalities(localities: List<LocalityCodebookEntity>) {
        localityCodebookRepository.saveAll(localities)
    }

    fun saveRoom(room: RoomCodebookEntity) {
        roomCodebookRepository.save(room)
    }

    fun saveAllRooms(rooms: List<RoomCodebookEntity>) {
        roomCodebookRepository.saveAll(rooms)
    }

    fun savePlace(place: PlaceCodebookEntity) {
        placeCodebookRepository.save(place)
    }

    fun saveAllPlaces(places: List<PlaceCodebookEntity>) {
        placeCodebookRepository.saveAll(places)
    }

    fun saveUser(user: UserCodebookEntity) {
        userCodebookRepository.save(user)
    }

    fun saveAllUsers(users: List<UserCodebookEntity>) {
        userCodebookRepository.saveAll(users)
    }

    fun saveNote(note: NoteCodebookEntity) {
        noteCodebookRepository.save(note)
    }

    fun saveAllNotes(notes: List<NoteCodebookEntity>) {
        noteCodebookRepository.saveAll(notes)
    }
}