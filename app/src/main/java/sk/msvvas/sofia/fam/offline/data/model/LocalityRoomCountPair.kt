package sk.msvvas.sofia.fam.offline.data.model

data class LocalityRoomCountPair(
    val locality: String,
    val room: String,
    var processed: Int = 0,
    var all: Int = 0
){
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
