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
    header: String,
    body: String,
    buttonText: String,
    confirm: () -> Unit
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
        TextField(
            value = header,
            readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            textStyle = TextStyle(fontSize = MaterialTheme.typography.h6.fontSize, textAlign = TextAlign.Center),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
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
        Row {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = confirm
            ) {
                Text(text = buttonText)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModalWindowPreview(){
    Box(modifier = Modifier.fillMaxSize()){
        ModalWindow(header = "Modálne okno", body = "Správa v modálnom okne", buttonText = "OK", confirm = {})
    }
}