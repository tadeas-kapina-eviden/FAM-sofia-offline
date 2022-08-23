package sk.msvvas.sofia.fam.offline.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sk.msvvas.sofia.fam.offline.data.daos.InventoryDao
import sk.msvvas.sofia.fam.offline.data.daos.codebook.UserCodebookDao
import sk.msvvas.sofia.fam.offline.data.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.entities.codebook.UserCodebookEntity

/*TODO add other daos */
@Database(entities = [(InventoryEntity::class), UserCodebookEntity::class], version = 1)
abstract class FamOfflineDatabase : RoomDatabase() {

    abstract fun inventoryDao() : InventoryDao
    abstract fun userCodebookDao() : UserCodebookDao

    companion object{
        private var INSTANCE : FamOfflineDatabase? = null

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