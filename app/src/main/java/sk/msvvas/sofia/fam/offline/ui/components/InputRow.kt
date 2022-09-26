package sk.msvvas.sofia.fam.offline.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.ui.theme.FAMInventuraOfflineClientTheme

/**
 * Component with label and input field in same row
 * Used for selection value via external function
 * @param label text value of label
 * @param value text value of input
 * @param modifier custom modifier for row container
 * @param ratio ratio of label/input text field
 * @param labelTextAlign align for label text
 * @param textFieldTextAlign align for text in input text field
 * @param labelTextHorizontalPadding horizontal padding of label
 * @param onClick function executed when user click on input text field (change textField value or open another view for selection value)
 */
@Composable
fun InputRow(
    label: String,
    value: String,
    modifier: Modifier,
    ratio: Float,
    labelTextAlign: TextAlign = TextAlign.Start,
    labelTextColor: Color = MaterialTheme.colors.primary,
    textFieldTextAlign: TextAlign = TextAlign.Start,
    textFieldColors: TextFieldColors = TextFieldDefaults.textFieldColors(
        disabledTextColor = MaterialTheme.colors.primary,
        textColor = MaterialTheme.colors.primary,
        backgroundColor = MaterialTheme.colors.secondary
    ),
    labelTextHorizontalPadding: Dp = 0.dp,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            modifier = Modifier
                .weight(ratio)
                .padding(horizontal = labelTextHorizontalPadding)
                .align(Alignment.CenterVertically),
            textAlign = labelTextAlign,
            style = MaterialTheme.typography.body1,
            color = labelTextColor
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
            colors = textFieldColors,
            textStyle = LocalTextStyle.current
                .copy(textAlign = textFieldTextAlign),
        )
    }
}


/**
 * Default preview for InputRow
 */
@Preview(showBackground = true)
@Composable
fun InputRowPreview() {
    FAMInventuraOfflineClientTheme {
        InputRow(
            label = "Lokalita: ",
            value = "AB-52",
            modifier = Modifier
                .drawWithBottomLine(
                    width = 1f,
                    color = MaterialTheme.colors.primary
                ),
            ratio = 2f / 5f,
            labelTextAlign = TextAlign.End,
            labelTextHorizontalPadding = 15.dp,
            onClick = {}
        )
    }
}