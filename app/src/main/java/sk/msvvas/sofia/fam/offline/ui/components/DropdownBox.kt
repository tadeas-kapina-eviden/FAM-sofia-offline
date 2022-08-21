package sk.msvvas.sofia.fam.offline.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DropdownBox(
    default: String,
    content: List<String> = ArrayList()
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selected by remember {
        mutableStateOf(default)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isExpanded = !isExpanded
                }
                .border(width = 1.dp, color = MaterialTheme.colors.primary)
                .padding(15.dp)
            ,
            color = MaterialTheme.colors.background,

            ) {
            Row {
                Text(
                    text = selected,
                    modifier = Modifier.weight(7f)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "dropdown icon",
                    modifier = Modifier
                        .rotate(if (isExpanded) 180f else 0f)
                        .weight(1f)
                )
            }
        }
        if (isExpanded) {
            for (c in content) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isExpanded = !isExpanded
                            selected = c
                        }
                        .border(width = 1.dp, color = MaterialTheme.colors.primary)
                        .padding(15.dp),
                    color = MaterialTheme.colors.background
                ) {
                    Text(text = c)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownBoxPreview() {
    DropdownBox(default = "selected text", content = listOf("selected text", "text1", "text 2"))
}