package sk.msvvas.sofia.fam.offline.ui.views.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.ui.components.ModalWindow

@Composable
fun LoginView(
    loginViewModel: LoginViewModel
) {
    val loginName: String by loginViewModel.loginName.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val client: String by loginViewModel.client.observeAsState("")
    val lastError: String by loginViewModel.lastError.observeAsState("")

    val loginNameFocusRequester by loginViewModel.loginNameFocusRequester.observeAsState()
    val passwordFocusRequester by loginViewModel.passwordFocusRequester.observeAsState()
    val clientFocusRequester by loginViewModel.clientFocusRequester.observeAsState()

    val downloadingData by loginViewModel.downloadingData.observeAsState(false)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (lastError.isNotEmpty()) {
                ErrorAlert(lastError = lastError)
            }
            InputField(
                value = loginName,
                onChange = { loginViewModel.onLoginNameChanged(it) },
                placeholder = "Užívateľ",
                focusRequester = loginNameFocusRequester!!,
                keyboardType = KeyboardType.Ascii,
                onDone = { loginViewModel.requestPasswordFocus() }
            )
            InputField(
                value = password,
                onChange = { loginViewModel.onPasswordChange(it) },
                placeholder = "Heslo",
                focusRequester = passwordFocusRequester!!,
                keyboardType = KeyboardType.Password,
                onDone = { loginViewModel.requestClientFocus() },
                visualTransformation = PasswordVisualTransformation()
            )
            InputField(
                value = client,
                onChange = { loginViewModel.onClientChange(it) },
                placeholder = "Klient",
                focusRequester = clientFocusRequester!!,
                keyboardType = KeyboardType.Number,
                onDone = { loginViewModel.onLoginButtonClick() }
            )
            Button(
                onClick = { loginViewModel.onLoginButtonClick() },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Text(
                    text = "Prihlásenie",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.h6.fontSize
                    )
                )
            }
        }
        if (downloadingData) {
            ModalWindow(
                header = "Načítavanie",
                body = "Sťahujú sa dáta, prosím počkajte",
                buttonText = "",
                confirm = {}
            )
        }
    }
    DisposableEffect(Unit) {
        loginViewModel.requestLoginNameFocus()
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
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(bottom = 5.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.secondary
                )
            ),
        readOnly = true
    )
}

@Composable
private fun InputField(
    value: String,
    onChange: (String) -> Unit,
    placeholder: String,
    focusRequester: FocusRequester,
    keyboardType: KeyboardType,
    onDone: () -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = { onChange(it) },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
        placeholder = { Text(text = placeholder) },
        visualTransformation = visualTransformation,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(bottom = 5.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary
                )
            )
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }),
        singleLine = true
    )
}