package sk.msvvas.sofia.fam.offline.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sk.msvvas.sofia.fam.offline.data.daos.InventoryDao
import sk.msvvas.sofia.fam.offline.data.entities.InventoryEntity

/*TODO add other daos */
@Database(entities = [(InventoryEntity::class)], version = 1)
abstract class FamOfflineDatabase : RoomDatabase() {

    abstract fun inventoryDao() : InventoryDao

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