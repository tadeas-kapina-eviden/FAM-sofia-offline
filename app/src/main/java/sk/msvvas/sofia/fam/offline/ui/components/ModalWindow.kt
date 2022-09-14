package sk.msvvas.sofia.fam.offline.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.ModalWindow(
    header: @Composable ColumnScope.() -> Unit = {},
    body: @Composable ColumnScope.() -> Unit = {},
    footer: @Composable ColumnScope.() -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xBB222222),
        content = {}
    )
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .align(Alignment.Center)
            .background(color = MaterialTheme.colors.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary
            ),
    ) {
        header.invoke(this)
        body.invoke(this)
        footer.invoke(this)
    }
}

@Composable
fun BoxScope.ConfirmModalWindow(
    header: String,
    body: String,
    confirmButtonText: String,
    confirmButtonAction: () -> Unit,
    declineButtonText: String,
    declineButtonAction: () -> Unit
) {
    ModalWindow(
        header = {
            TextField(
                value = header,
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                ),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    textAlign = TextAlign.Center
                ),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            )
        },
        body = {
            TextField(
                value = body,
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                ),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            )
        },
        footer = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = confirmButtonAction
                ) {
                    Text(text = confirmButtonText)
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = declineButtonAction
                ) {
                    Text(text = declineButtonText)
                }
            }
        })
}

@Composable
fun BoxScope.InformationModalWindow(
    header: String,
    body: String,
    buttonText: String,
    buttonAction: () -> Unit
) {
    ModalWindow(
        header = {
            TextField(
                value = header,
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                ),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    textAlign = TextAlign.Center
                ),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            )
        },
        body = {
            TextField(
                value = body,
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                ),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            )
        },
        footer = {
            Row {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = buttonAction
                ) {
                    Text(text = buttonText)
                }
            }
        })
}

@Composable
fun BoxScope.InformationNonClosableModalWindow(
    header: String,
    body: String
) {
    ModalWindow(
        header = {
            TextField(
                value = header,
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                ),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    textAlign = TextAlign.Center
                ),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            )
        },
        body = {
            TextField(
                value = body,
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                ),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun ModalWindowPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        InformationModalWindow(
            header = "Modálne okno",
            body = "Správa v modálnom okne",
            buttonText = "OK",
            buttonAction = {}
        )
    }
}