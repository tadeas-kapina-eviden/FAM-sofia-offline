package sk.msvvas.sofia.fam.offline.ui.views.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.R
import sk.msvvas.sofia.fam.offline.ui.components.InformationNonCloseableModalWindow
import sk.msvvas.sofia.fam.offline.ui.components.StyledTextButton

/**
 * View for login to back-end
 * @param loginViewModel view model for view
 */
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
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
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
            InputField(
                value = loginName,
                onChange = { loginViewModel.onLoginNameChanged(it) },
                placeholder = "Užívateľ",
                focusRequester = loginNameFocusRequester!!,
                keyboardType = KeyboardType.Ascii,
                onDone = { loginViewModel.requestPasswordFocus() },
                modifier = Modifier.padding(
                    top = 40.dp,
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 10.dp
                )
            )
            InputField(
                value = password,
                onChange = { loginViewModel.onPasswordChange(it) },
                placeholder = "Heslo",
                focusRequester = passwordFocusRequester!!,
                keyboardType = KeyboardType.Password,
                onDone = { loginViewModel.requestClientFocus() },
                visualTransformation = PasswordVisualTransformation(),
            )
            InputField(
                value = client,
                onChange = { loginViewModel.onClientChange(it) },
                placeholder = "Klient",
                focusRequester = clientFocusRequester!!,
                keyboardType = KeyboardType.Number,
                onDone = { loginViewModel.onLoginButtonClick() },
            )
            StyledTextButton(
                onClick = { loginViewModel.onLoginButtonClick() },
                modifier = Modifier
                    .padding(
                        bottom = 40.dp,
                        start = 30.dp,
                        end = 30.dp
                    )
                    .fillMaxWidth(),
                text = "Prihlásiť sa"
            )
        }
        if (downloadingData) {
            InformationNonCloseableModalWindow(
                header = "Načítavanie",
                body = "Sťahujú sa dáta, prosím počkajte",
            )
        }
    }
    DisposableEffect(Unit) {
        loginViewModel.requestLoginNameFocus()
        onDispose { }
    }

}


/**
 * Error alert styled for LoginView
 * @param lastError message shown in error
 */
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
        textStyle = TextStyle(
            textAlign = TextAlign.Center,
        ),
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

/**
 * Input TextField styled for login view
 * @param value actual value in text field
 * @param onChange function executed when values is changed
 * @param placeholder placeholder for text field
 * @param focusRequester focus requester for text field
 * @param keyboardType type of keyboard used for input
 * @param onDone function executed, when done is pressed on keyboard
 * @param visualTransformation visual transformation for text
 */
@Composable
private fun InputField(
    value: String,
    onChange: (String) -> Unit,
    placeholder: String,
    focusRequester: FocusRequester,
    keyboardType: KeyboardType,
    onDone: () -> Unit,
    modifier: Modifier = Modifier.padding(bottom = 10.dp, start = 30.dp, end = 30.dp),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = { onChange(it) },
        colors = TextFieldDefaults
            .textFieldColors(
                backgroundColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.primary
            ),
        placeholder = { Text(text = placeholder) },
        visualTransformation = visualTransformation,
        modifier = modifier
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
        singleLine = true,

        )
}