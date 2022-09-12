package sk.msvvas.sofia.fam.offline.data.application.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sk.msvvas.sofia.fam.offline.data.application.daos.InventoryDao
import sk.msvvas.sofia.fam.offline.data.application.daos.PropertyDao
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.*
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.*

@Database(
    entities = [
        InventoryEntity::class,
        PropertyEntity::class,
        UserCodebookEntity::class,
        RoomCodebookEntity::class,
        PlacesCodebookEntity::class,
        LocalityCodebookEntity::class,
        NoteCodebookEntity::class
    ],
    version = 1
)
abstract class FamOfflineDatabase : RoomDatabase() {

    abstract fun inventoryDao(): InventoryDao
    abstract fun propertyDao(): PropertyDao
    abstract fun userCodebookDao(): UserCodebookDao
    abstract fun roomCodebookDao(): RoomCodebookDao
    abstract fun placesCodebookDao(): PlacesCodebookDao
    abstract fun localityCodebookDao(): LocalityCodebookDao
    abstract fun noteCodebookDao(): NoteCodebookDao

    companion object {
        private var INSTANCE: FamOfflineDatabase? = null

        fun getInstance(context: Context): FamOfflineDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FamOfflineDatabase::class.java,
                        "fam_offline_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}