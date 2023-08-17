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
                    val newVal = (Products innerJoin Types innerJoin OrderItems innerJoin OrderRows).slice(Types.description, Products.description, OrderRows.quantity, Products.id)
                        .selectAll()
                        .groupBy { it[Products.id] }
                        .map { entry ->
                            val mapValue = entry.value
                            mapValue
                                .map { Triple(it[Types.description], it[Products.description], it.getOrNull(OrderRows.quantity)?: 0) }
                                .reduce {
                                    acc,
                                    trip ->
                                    Triple(acc.first, acc.second, acc.third + trip.third)
                                }
                        }.map { triple -> listOf(triple.first, triple.second, triple.third).map { it.toString() } }

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
