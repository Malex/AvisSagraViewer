import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import schema.*


@Composable
@Preview
fun App() {
    val text = "Aggiorna"

    var results by remember { mutableStateOf(listOf<List<String>>()) }

    MaterialTheme {
        Column {
            Button(onClick = {
                transaction(DbSettings.db) {
                    addLogger(StdOutSqlLogger)
                    val newVal = (Products innerJoin Types innerJoin Remaining).slice(Types.description, Products.description, Remaining.starting).selectAll().map {
                        listOf(it[Types.description].toString(), it[Products.description].toString(), it.getOrNull(Remaining.starting)?.toString() ?: "")

                    }.toList()
                    results = newVal
                }
            }) {
                Text(text)
            }
            TableScreen(
                listOf("Categoria", "Piatto", "Quantità"),
                results
            )

        }
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
