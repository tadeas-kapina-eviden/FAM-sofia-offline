package sk.msvvas.sofia.fam.offline.data.repository.codebook

class AllCodebooksRepository(
    localityCodebookRepository: LocalityCodebookRepository,
    noteCodebookRepository: NoteCodebookRepository,
    placesCodebookRepository: PlacesCodebookRepository,
    roomCodebookRepository: RoomCodebookRepository,
    userCodebookRepository: UserCodebookRepository
) {
    val allLocalities = localityCodebookRepository.allData
    val allRooms = roomCodebookRepository.allData
    val allUsers = userCodebookRepository.allData
    val allPlaces = placesCodebookRepository.allData
    val allNotes = noteCodebookRepository.allData
}