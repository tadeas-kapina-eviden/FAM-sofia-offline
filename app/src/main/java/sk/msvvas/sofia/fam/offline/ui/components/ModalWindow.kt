package sk.msvvas.sofia.fam.offline.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.msvvas.sofia.fam.offline.ui.theme.FAMInventuraOfflineClientTheme

/**
 * Pop up window with shadowing background
 * @param header content of header of footer
 * @param body content of body of footer
 * @param footer content of footer of footer
 */
@Composable
fun BoxScope.ModalWindow(
    header: @Composable ColumnScope.() -> Unit = {},
    body: @Composable ColumnScope.() -> Unit = {},
    footer: @Composable ColumnScope.() -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.onSurface,
        content = {},
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .align(Alignment.Center)
            .background(color = MaterialTheme.colors.secondary, shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(12.dp)
            ),
    ) {
        header.invoke(this)
        body.invoke(this)
        footer.invoke(this)
    }
}

/**
 * Modal window for actions that need to be confirmed
 * @param header text in header of window
 * @param body text in body of window
 * @param confirmButtonText text for confirm button in footer
 * @param confirmButtonAction function executed when confirm button is clicked
 * @param declineButtonText text for confirm button in footer
 * @param declineButtonAction function executed when decline button is clicked
 */
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
            Text(
                text = header,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h4.copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .drawWithBottomLine(color = MaterialTheme.colors.primary, width = 1f)
                    .padding(10.dp),
            )
        },
        body = {
            Text(
                text = body,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .drawWithBottomLine(color = MaterialTheme.colors.primary, width = 1f)
                    .padding(10.dp),
            )
        },
        footer = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                StyledTextButton(
                    modifier = Modifier.weight(1f),
                    onClick = confirmButtonAction,
                    text = confirmButtonText,
                    textModifier = Modifier.padding(vertical = 15.dp)
                )
                Spacer(modifier = Modifier.weight(0.1f))
                StyledTextButton(
                    modifier = Modifier.weight(1f),
                    onClick = declineButtonAction,
                    text = declineButtonText,
                    textModifier = Modifier.padding(vertical = 15.dp)
                )
            }
        })
}

/**
 * Modal window used for showing information
 * Can be dismissed by button
 * @param header text in header of window
 * @param body text in body of window
 * @param buttonText text in button of window
 * @param buttonAction function that is executed when button is clicked (used for dismissing window)
 */
@Composable
fun BoxScope.InformationModalWindow(
    header: String,
    body: String,
    buttonText: String,
    buttonAction: () -> Unit
) {
    ModalWindow(
        header = {
            Text(
                text = header,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h4.copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .drawWithBottomLine(color = MaterialTheme.colors.primary, width = 1f)
                    .padding(10.dp),
            )
        },
        body = {
            Text(
                text = body,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .drawWithBottomLine(color = MaterialTheme.colors.primary, width = 1f)
                    .padding(10.dp),
            )
        },
        footer = {
            Row {
                StyledTextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    onClick = buttonAction,
                    text = buttonText,
                    textModifier = Modifier.padding(vertical = 15.dp)
                )
            }
        })
}

/**
 * Modal window used for showing information
 * Cannot be dismissed by user - used primary when application loading data
 * @param header text in header of window
 * @param body text in body of window
 */
@Composable
fun BoxScope.InformationNonCloseableModalWindow(
    header: String,
    body: String
) {
    ModalWindow(
        header = {
            Text(
                text = header,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h4.copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .drawWithBottomLine(color = MaterialTheme.colors.primary, width = 1f)
                    .padding(10.dp),
            )
        },
        body = {
            Text(
                text = body,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
            )
        },
    )
}

/**
 * Default preview for Modal window
 */
@Preview(showBackground = true)
@Composable
fun ModalWindowPreview() {
    FAMInventuraOfflineClientTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            InformationModalWindow(
                header = "Modálne okno",
                body = "Správa v modálnom okne",
                buttonText = "OK",
                buttonAction = {}
            )
        }
    }
}