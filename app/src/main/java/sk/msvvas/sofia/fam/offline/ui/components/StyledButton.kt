package sk.msvvas.sofia.fam.offline.ui.components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun StyledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.secondary,
            disabledBackgroundColor = MaterialTheme.colors.primary,
            disabledContentColor = MaterialTheme.colors.secondary
        ),
        modifier = modifier
    ) {
        content.invoke()
    }

}

@Composable
fun StyledTextButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    StyledButton(onClick = onClick, modifier = modifier) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}