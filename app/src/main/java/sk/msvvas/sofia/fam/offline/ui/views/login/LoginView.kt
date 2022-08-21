package sk.msvvas.sofia.fam.offline.ui.views.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.ui.components.DropdownBox

@Composable
fun LoginView() {
    var loginName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedLanguage by remember {
        mutableStateOf("SK - Slovenčina")
    }

    var klient by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = loginName,
            onValueChange = { newText: String -> loginName = newText },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            placeholder = { Text(text = "Užívateľ") },
            modifier = Modifier.border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary
                )
            )
        )
        TextField(
            value = password,
            onValueChange = { newPass: String -> password = newPass },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            placeholder = { Text(text = "Heslo") },
            modifier = Modifier.border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary
                )
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Text(text = "Jazyk", textAlign = TextAlign.Left)
        DropdownBox(
            default = selectedLanguage,
            content = listOf("DE - Deutsch", "EN - English", "SK - Slovenčina", "CS - Čeština")
        )
        TextField(
            value = klient,
            onValueChange = { newKlient: String -> klient = newKlient },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            placeholder = { Text(text = "Klient") },
            modifier = Modifier.border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary
                )
            ),
        )
        Button(
            onClick = { login() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Prihlásenie")
        }
    }
}


@Composable
@Preview(showBackground = true)
fun LoginViewPreview() {
    LoginView()
}

fun login() {

}

