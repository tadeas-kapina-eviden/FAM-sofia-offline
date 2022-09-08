package sk.msvvas.sofia.fam.offline.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun InputRow(
    label: String,
    value: String,
    modifier: Modifier,
    ratio: Float,
    labelTextAlign: TextAlign = TextAlign.Start,
    textFieldTextAlign: TextAlign = TextAlign.Start,
    labelTextHorizontalPadding: Dp = 0.dp,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = label,
            modifier = Modifier
                .weight(ratio)
                .padding(horizontal = labelTextHorizontalPadding)
                .align(Alignment.CenterVertically),
            textAlign = labelTextAlign,
        )
        TextField(
            value = value,
            enabled = false,
            onValueChange = {},
            modifier = Modifier
                .weight(1f)
                .clickable(enabled = true) {
                    onClick()
                },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = LocalTextStyle.current
                .copy(textAlign = textFieldTextAlign)
        )
    }
}