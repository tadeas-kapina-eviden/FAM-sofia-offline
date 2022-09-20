package sk.msvvas.sofia.fam.offline.ui.views.loading

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import sk.msvvas.sofia.fam.offline.data.application.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.ui.components.ConfirmModalWindow
import sk.msvvas.sofia.fam.offline.ui.navigation.Routes

/**
 * Application intro view
 * Show loading screen until property repository is loaded,
 * Then show a screen for choose next steps
 * @param propertyRepository property repository of local database
 * @param navController nav controller from application Navigation
 */
@Composable
fun LoadingScreenView(
    propertyRepository: PropertyRepository,
    navController: NavController
) {

    val loaded by propertyRepository.loaded.observeAsState(false)
    var exitModalShown by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!loaded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Načítavanie",
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        } else {
            if (propertyRepository.allData.value!!.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Žiadna inventúra nie je stiahnutá",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    Button(
                        onClick = {
                            navController.navigate(Routes.LOGIN_VIEW.value)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text("Pokračovať na prihlásenie")
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Máte uloženú inventúru offline",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    Button(
                        onClick = {
                            navController.navigate(Routes.LOGIN_VIEW.value + "?id=" + (propertyRepository.allData.value!![0].inventoryId))
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text("Pokračovať na prihlásenie")
                    }
                    Button(
                        onClick = {
                            navController.navigate(
                                Routes.INVENTORY_DETAIL.withArgs(
                                    propertyRepository.allData.value!![0].inventoryId
                                )
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text("Pokračovať bez prihlásenia")
                    }
                }
            }
        }
        val activity = (LocalContext.current as? Activity)
        if (exitModalShown) {
            ConfirmModalWindow(
                header = "Opúšťate aplikáciu...",
                body = "Naozaj chcete opustiť aplikáciue?",
                confirmButtonText = "Áno",
                confirmButtonAction = {
                    activity?.finish()
                },
                declineButtonText = "Nie",
                declineButtonAction = {
                    exitModalShown = false
                }
            )
        }
    }
    BackHandler {
        exitModalShown = true
    }
}