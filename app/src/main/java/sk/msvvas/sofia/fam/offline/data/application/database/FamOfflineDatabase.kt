package sk.msvvas.sofia.fam.offline.data.application.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sk.msvvas.sofia.fam.offline.data.application.daos.InventoryDao
import sk.msvvas.sofia.fam.offline.data.application.daos.PropertyDao
import sk.msvvas.sofia.fam.offline.data.application.daos.ServerUrlDao
import sk.msvvas.sofia.fam.offline.data.application.daos.UserDataDao
import sk.msvvas.sofia.fam.offline.data.application.daos.codebook.*
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.ServerUrlEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.UserDataEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.*


/**
 * Room database for store data in device
 * Holds instances for every DAO in database
 */
@Database(
    entities = [
        InventoryEntity::class,
        PropertyEntity::class,
        UserCodebookEntity::class,
        RoomCodebookEntity::class,
        PlaceCodebookEntity::class,
        LocalityCodebookEntity::class,
        NoteCodebookEntity::class,
        ServerUrlEntity::class,
        UserDataEntity::class
    ],
    version = 3
)
abstract class FamOfflineDatabase : RoomDatabase() {

    /**
     * @return database instance of inventory DAO
     */
    abstract fun inventoryDao(): InventoryDao

    /**
     * @return database instance of property DAO
     */
    abstract fun propertyDao(): PropertyDao

    /**
     * @return database instance of user codebook DAO
     */
    abstract fun userCodebookDao(): UserCodebookDao

    /**
     * @return database instance of room codebook DAO
     */
    abstract fun roomCodebookDao(): RoomCodebookDao

    /**
     * @return database instance of place codebook DAO
     */
    abstract fun placeCodebookDao(): PlaceCodebookDao

    /**
     * @return database instance of locality codebook DAO
     */
    abstract fun localityCodebookDao(): LocalityCodebookDao

    /**
     * @return database instance of note codebook DAO
     */
    abstract fun noteCodebookDao(): NoteCodebookDao

    /**
     * @return database instance of server url DAO
     */
    abstract fun serverUrlDao(): ServerUrlDao

    /**
     * @return database instance of user data DAO
     */
    abstract fun userDataDao(): UserDataDao

    companion object {
        private var INSTANCE: FamOfflineDatabase? = null

        /**
         * Return Instance of Database
         * @return already running instance or create new if no one exists
         * @param context application context
         */
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