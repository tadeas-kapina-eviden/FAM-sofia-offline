package sk.msvvas.sofia.fam.offline.ui.components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StyledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.primary,
            disabledBackgroundColor = MaterialTheme.colors.secondaryVariant,
            disabledContentColor = MaterialTheme.colors.primaryVariant
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
        Text(text = text)
    }
}