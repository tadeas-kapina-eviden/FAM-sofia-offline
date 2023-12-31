package sk.msvvas.sofia.fam.offline.ui.views.login

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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.R
import sk.msvvas.sofia.fam.offline.ui.components.LoadingAnimationModalWindow
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
    val isLoaded by loginViewModel.isLoaded.observeAsState(false)

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
            InputField(
                value = TextFieldValue(
                    text = loginName,
                    selection = TextRange(loginName.length)
                ),
                onChange = { loginViewModel.onLoginNameChanged(it) },
                label = "Užívateľ",
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
                value = TextFieldValue(
                    text = password,
                    selection = TextRange(password.length)
                ),
                onChange = { loginViewModel.onPasswordChange(it) },
                label = "Heslo",
                focusRequester = passwordFocusRequester!!,
                keyboardType = KeyboardType.Password,
                onDone = { loginViewModel.requestClientFocus() },
                visualTransformation = PasswordVisualTransformation(),
            )
            InputField(
                value = TextFieldValue(
                    text = client,
                    selection = TextRange(client.length)
                ),
                onChange = { loginViewModel.onClientChange(it) },
                label = "Klient",
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
        if (!isLoaded) {
            LoadingAnimationModalWindow(header = "Načítavanie", text = "")
        } else {
            loginViewModel.setSavedUserData();
        }
        if (downloadingData) {
            LoadingAnimationModalWindow(header = "Načítavanie", text = "Prihlasuje sa na server...")
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

/**
 * Input TextField styled for login view
 * @param value actual value in text field
 * @param onChange function executed when values is changed
 * @param label placeholder for text field
 * @param focusRequester focus requester for text field
 * @param keyboardType type of keyboard used for input
 * @param onDone function executed, when done is pressed on keyboard
 * @param visualTransformation visual transformation for text
 */
@Composable
private fun InputField(
    value: TextFieldValue,
    onChange: (String) -> Unit,
    label: String,
    focusRequester: FocusRequester,
    keyboardType: KeyboardType,
    onDone: () -> Unit,
    modifier: Modifier = Modifier.padding(bottom = 10.dp, start = 30.dp, end = 30.dp),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChange(it.text) },
        colors = TextFieldDefaults
            .textFieldColors(
                backgroundColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.primary,
                placeholderColor = MaterialTheme.colors.primaryVariant,
            ),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.body1
            )
        },
        visualTransformation = visualTransformation,
        modifier = modifier
            /*.border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary
                ),
            )*/
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
        textStyle = MaterialTheme.typography.body1
    )
}