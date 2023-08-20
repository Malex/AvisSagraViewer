import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogProperties
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.util.*

val TODAY = java.time.LocalDate.now().toKotlinLocalDate()

@Composable
@Preview
fun App() {

    var results by remember { mutableStateOf(listOf<List<String>>()) }
    val filters = mutableStateListOf<String>()
    val queryExecutor = DbQueryExecutor()
    val productOptions = queryExecutor.getAllProducts()
    val dateFrom = mutableStateOf<LocalDate?>(TODAY)
    val dateTo = mutableStateOf<LocalDate?>(TODAY)

    val dialogStateFrom = rememberMaterialDialogState()
    val dialogStateTo = rememberMaterialDialogState()

    createDatePicker(dialogStateFrom, dateFrom)
    createDatePicker(dialogStateTo, dateTo)

    MaterialTheme {
        Column {
            Row {
                Button(onClick = {
                    results = if (isEmptyFilters(filters, dateFrom.value, dateTo.value)) queryExecutor.getBasic() else queryExecutor.getSpecificProductsbyName(filters, (dateFrom.value ?: LocalDate.fromEpochDays(1))..(dateTo.value ?: TODAY))
                }, colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier.padding(10.dp)) {
                    Text("Aggiorna")
                }
                Button(
                    onClick = {
                        filters.removeAll { true }
                        results = queryExecutor.getBasic()
                    }, colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier.padding(10.dp)) {
                    Text("Reset")
                }
                Button(
                    onClick = {
                              dialogStateFrom.show()
                    }, colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier.padding(10.dp)) {
                    Text("Da: ${dateFrom.value ?: "Sempre"}")
                }
                Button(
                    onClick = {
                        dialogStateTo.show()
                    }, colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier.padding(10.dp)) {
                    Text("A: ${dateTo.value ?: "Sempre"}")
                }
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

@Composable
fun createDatePicker(
    dialogState: MaterialDialogState,
    dateToChange: MutableState<LocalDate?>
) {
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        },
        properties = MaterialDialogProperties(size = DpSize(500.dp, 500.dp))
    ) {
        datepicker(initialDate = dateToChange.value ?: TODAY,
            title = "Scegli data") { date ->
            dateToChange.value = date
        }
    }
}

fun isEmptyFilters(filters: List<String>, dateFrom: LocalDate?, dateTo: LocalDate?) =
    filters.isEmpty() and Objects.isNull(dateFrom) and Objects.isNull(dateTo)


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
