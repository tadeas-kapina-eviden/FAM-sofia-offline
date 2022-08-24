package sk.msvvas.sofia.fam.offline.ui.views.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginView(
    loginViewModel: LoginViewModel
) {
    val loginName: String by loginViewModel.loginName.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val client: String by loginViewModel.client.observeAsState("")
    val lastError: String by loginViewModel.lastError.observeAsState("")

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(lastError.isNotEmpty()){
            TextField(
                value = lastError,
                onValueChange = {  },
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
        TextField(
            value = loginName,
            onValueChange = { loginViewModel.onLoginNameChanged(it) },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            placeholder = { Text(text = "Užívateľ") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 5.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary
                    )
                )
        )
        TextField(
            value = password,
            onValueChange = { loginViewModel.onPasswordChange(it) },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            placeholder = { Text(text = "Heslo") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 5.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary
                    )
                ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1,
        )
        TextField(
            value = client,
            onValueChange = { loginViewModel.onClientChange(it) },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            placeholder = { Text(text = "Klient") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 5.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary
                    )
                ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
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
}


@Composable
@Preview(showBackground = true)
fun LoginViewPreview() {
    LoginView(
        loginViewModel = LoginViewModel(
            changeView = {}
        )
    )
}
