package sk.msvvas.sofia.fam.offline.ui.views.loading

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import sk.msvvas.sofia.fam.offline.R
import sk.msvvas.sofia.fam.offline.data.application.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.ui.components.ConfirmModalWindow
import sk.msvvas.sofia.fam.offline.ui.components.LoadingAnimationModalWindow
import sk.msvvas.sofia.fam.offline.ui.components.StyledTextButton
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
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .padding(20.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        if (!loaded) {
            LoadingAnimationModalWindow(header = "Načítavanie")
        } else {
            if (propertyRepository.allData.value!!.isEmpty()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(color = Color(0x66ffffff), shape = RoundedCornerShape(10.dp))
                        .fillMaxWidth(0.7f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Žiadna inventúra nie je stiahnutá",
                        modifier = Modifier
                            .padding(
                                top = 40.dp,
                                start = 30.dp,
                                end = 30.dp,
                                bottom = 20.dp
                            )
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center)
                    )
                    StyledTextButton(
                        onClick = {
                            navController.navigate(Routes.LOGIN_VIEW.value)
                        },
                        modifier = Modifier
                            .padding(
                                top = 0.dp,
                                start = 30.dp,
                                end = 30.dp,
                                bottom = 40.dp
                            )
                            .fillMaxWidth(),
                        text = "Pokračovať na prihlásenia"
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(color = Color(0x66ffffff), shape = RoundedCornerShape(10.dp))
                        .fillMaxWidth(0.8f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Máte uloženú inventúru offline",
                        modifier = Modifier
                            .padding(
                                top = 40.dp,
                                start = 30.dp,
                                end = 30.dp,
                                bottom = 20.dp
                            )
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    StyledTextButton(
                        onClick = {
                            navController.navigate(Routes.LOGIN_VIEW.value + "?id=" + (propertyRepository.allData.value!![0].inventoryId))
                        },
                        modifier = Modifier
                            .padding(
                                top = 0.dp,
                                start = 30.dp,
                                end = 30.dp,
                                bottom = 20.dp
                            )
                            .fillMaxWidth(),
                        text = "Pokračovať na prihlásenie"
                    )
                    StyledTextButton(
                        onClick = {
                            navController.navigate(
                                Routes.INVENTORY_DETAIL.withArgs(
                                    propertyRepository.allData.value!![0].inventoryId
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(
                                top = 0.dp,
                                start = 30.dp,
                                end = 30.dp,
                                bottom = 40.dp
                            )
                            .fillMaxWidth(),
                        text = "Pokračovať bez prihlásenia"
                    )
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