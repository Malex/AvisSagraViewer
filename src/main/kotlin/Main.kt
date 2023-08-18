import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


@Composable
@Preview
fun App() {
    val text = "Aggiorna"

    var results by remember { mutableStateOf(listOf<List<String>>()) }
    val filters = mutableStateListOf<String>()
    val queryExecutor = DbQueryExecutor()
    val productOptions = queryExecutor.getAllProducts()

    MaterialTheme {
        Column {
            Button(onClick = {
                results = if (filters.isEmpty()) queryExecutor.getBasic() else queryExecutor.getSpecificProductsbyName(filters)
            }, colors = ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier.padding(10.dp)) {
                Text(text)
            }
            TableScreen(
                listOf("Categoria", "Piatto", "Quantità"),
                results,
                productOptions,
                filters
            )

        }
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
