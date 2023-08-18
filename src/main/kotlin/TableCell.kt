import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    onClick: ((Int) -> Unit)? = null
) {
    val modifier = Modifier
        .border(1.dp, Color.Black)
        .weight(weight)
        .padding(8.dp)
    onClick?.let {
        ClickableText(text = AnnotatedString(text), modifier = modifier, onClick=it)
    } ?: Text(text = text, modifier)
}