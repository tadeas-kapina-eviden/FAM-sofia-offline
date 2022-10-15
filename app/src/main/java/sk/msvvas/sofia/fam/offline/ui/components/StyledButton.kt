package sk.msvvas.sofia.fam.offline.ui.components

import androidx.compose.foundation.border
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

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
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier
) {
    StyledButton(onClick = onClick, modifier = modifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center),
            modifier = textModifier
        )
    }
}

@Composable
fun StyledBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.primary,
            disabledBackgroundColor = MaterialTheme.colors.secondary,
            disabledContentColor = MaterialTheme.colors.primary
        ),
        modifier = modifier.border(
            width = 1.dp,
            color = MaterialTheme.colors.primary
        )
    ) {
        content.invoke()
    }
}

@Composable
fun StyledTextBackButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier
) {
    StyledBackButton(onClick = onClick, modifier = modifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center),
            modifier = textModifier
        )
    }
}
