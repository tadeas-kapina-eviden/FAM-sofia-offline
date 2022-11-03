package sk.msvvas.sofia.fam.offline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import sk.msvvas.sofia.fam.offline.data.application.database.FamOfflineDatabase
import sk.msvvas.sofia.fam.offline.ui.navigation.Navigation
import sk.msvvas.sofia.fam.offline.ui.theme.FAMInventuraOfflineClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = FamOfflineDatabase.getInstance(applicationContext)

        setContent {
            FAMInventuraOfflineClientTheme {
                Navigation(database = database)
            }
        }
    }
}