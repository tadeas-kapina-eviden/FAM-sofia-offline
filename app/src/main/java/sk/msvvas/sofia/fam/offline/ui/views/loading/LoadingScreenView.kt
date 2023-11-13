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
import sk.msvvas.sofia.fam.offline.R
import sk.msvvas.sofia.fam.offline.data.client.ClientData
import sk.msvvas.sofia.fam.offline.ui.components.ConfirmModalWindow
import sk.msvvas.sofia.fam.offline.ui.components.LoadingAnimationModalWindow
import sk.msvvas.sofia.fam.offline.ui.components.StyledTextButton

/**
 * Application intro view
 * Show loading screen until property repository is loaded,
 * Then show a screen for choose next steps
 */
@Composable
fun LoadingScreenView(
    loadingScreenViewModel: LoadingScreenViewModel
) {

    val serverUrl by loadingScreenViewModel.serverUrl.observeAsState(null)
    val inventoryIdFromProperty by loadingScreenViewModel.inventoryIdFromProperty.observeAsState(null)
    var exitModalShown by remember { mutableStateOf(false) }


    var navigatedToServerUrlView by remember {
        mutableStateOf(false)
    }

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
        if (!navigatedToServerUrlView) {
            if (serverUrl == null) {
                LoadingAnimationModalWindow(header = "Načítavanie", "Načítava sa url servera...")
            } else {
                if (serverUrl == "") {
                    loadingScreenViewModel.navigateToSetUpUrl()
                    navigatedToServerUrlView = true
                } else {
                    ClientData.host = serverUrl!!
                    if (inventoryIdFromProperty == "") {
                        LoadingAnimationModalWindow(
                            header = "Načítavanie",
                            text = "Načítava sa uložená inventúra..."
                        )
                    } else {
                        if (inventoryIdFromProperty == null) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .background(
                                        color = Color(0x66ffffff),
                                        shape = RoundedCornerShape(10.dp)
                                    )
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
                                        loadingScreenViewModel.navigateToLoginView()
                                    },
                                    modifier = Modifier
                                        .padding(
                                            top = 0.dp,
                                            start = 30.dp,
                                            end = 30.dp,
                                            bottom = 40.dp
                                        )
                                        .fillMaxWidth(),
                                    text = "Pokračovať na prihlásenie"
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .background(
                                        color = Color(0x66ffffff),
                                        shape = RoundedCornerShape(10.dp)
                                    )
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
                                        loadingScreenViewModel.navigateToLoginViewWithDownloadedInventory()
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
                                        loadingScreenViewModel.navigateToInventoriesList()
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
                }

            }
        }
        val activity = (LocalContext.current as? Activity)
        if (exitModalShown) {
            ConfirmModalWindow(
                header = "Opúšťate aplikáciu...",
                body = "Naozaj chcete opustiť aplikáciu",
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