package sk.msvvas.sofia.fam.offline.data.application.model

/**
 * Model that holds counts of items by locality-room combination
 * Equals function only compare locality-room combination, not counts
 * @param locality id of locality
 * @param room id of room
 * @param processed count of processed items with this locality-room combination
 * @param all count of all items with this locality-room combination
 */
data class LocalityRoomCountPair(
    val locality: String,
    val room: String,
    var processed: Int = 0,
    var all: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        return if (other is LocalityRoomCountPair) {
            other.locality == this.locality && other.room == this.room
        } else
            false
    }

    override fun hashCode(): Int {
        var result = locality.hashCode()
        result = 31 * result + room.hashCode()
        result = 31 * result + processed
        result = 31 * result + all
        return result
    }
}
