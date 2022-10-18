package sk.msvvas.sofia.fam.offline.ui.views.setup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.R
import sk.msvvas.sofia.fam.offline.ui.components.LoadingAnimationModalWindow
import sk.msvvas.sofia.fam.offline.ui.components.StyledTextButton

@Composable
fun SetUpUrlView(
    setUpUrlViewModel: SetUpUrlViewModel
) {

    val url by setUpUrlViewModel.url.observeAsState("")
    val lastError by setUpUrlViewModel.lastError.observeAsState("")
    val savingData by setUpUrlViewModel.savingData.observeAsState(false)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .padding(20.dp),
        )
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .background(color = Color(0x66ffffff), shape = RoundedCornerShape(10.dp))
                .fillMaxWidth(0.85f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (lastError.isNotEmpty()) {
                ErrorAlert(lastError = lastError)
            }
            OutlinedTextField(
                value = url,
                onValueChange = setUpUrlViewModel::onChangeUrl,
                colors = TextFieldDefaults
                    .textFieldColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        textColor = MaterialTheme.colors.primary,
                        placeholderColor = MaterialTheme.colors.primaryVariant,
                    ),
                label = {
                    Text(
                        text = "URL",
                        style = MaterialTheme.typography.body1
                    )
                },
                modifier = Modifier
                    .focusRequester(setUpUrlViewModel.focusRequester.value!!),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        setUpUrlViewModel.setUrl()
                    }),
                singleLine = true,
                textStyle = MaterialTheme.typography.body1
            )

            StyledTextButton(
                onClick = { setUpUrlViewModel.setUrl() },
                modifier = Modifier
                    .padding(
                        bottom = 40.dp,
                        start = 30.dp,
                        end = 30.dp
                    )
                    .fillMaxWidth(),
                text = "Uložiť"
            )
        }
        if (savingData) {
            LoadingAnimationModalWindow(header = "Načítavanie", "Ukladajú sa dáta...")
        }
    }
    DisposableEffect(Unit) {
        setUpUrlViewModel.requestFocus()
        onDispose { }
    }
}

@Composable
private fun ErrorAlert(
    lastError: String
) {
    TextField(
        value = lastError,
        onValueChange = { },
        colors = TextFieldDefaults
            .textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                textColor = MaterialTheme.colors.primary,
                placeholderColor = MaterialTheme.colors.primaryVariant
            ),
        textStyle = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.secondary
                )
            ),
        readOnly = true
    )
}