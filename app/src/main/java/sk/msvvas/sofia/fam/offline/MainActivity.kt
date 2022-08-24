package sk.msvvas.sofia.fam.offline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import sk.msvvas.sofia.fam.offline.ui.views.inventory.InventoryDetailView
import sk.msvvas.sofia.fam.offline.ui.views.login.LoginView
import sk.msvvas.sofia.fam.offline.ui.views.login.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginViewModel = LoginViewModel {
            setContent {
                InventoryDetailView(

                )
            }
        }

        setContent {
            LoginView(
                loginViewModel = loginViewModel
            )
        }
    }
}
