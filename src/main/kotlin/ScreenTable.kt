import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.max


infix fun Int.fdiv(i: Int): Float = this / i.toFloat()

@Composable
fun TableScreen(columnText: List<String>, values: List<List<String?>>, optList: List<String>, outVar: MutableList<String>) {
    val expanded = mutableStateOf(false)

    ProductsDropDownMenu(optList, outVar, expanded)

    // Each cell of a column must have the same weight.
    val totalWeightColumn = columnText.map { it.length }.sum()
    val maxValuesRows = values.map { stringList -> stringList.map { it?.length ?: 0 } }
            .reduceOrNull {
                    acc, ints ->  ints.mapIndexed { i, value ->
                max(value, acc[i])
            }
        }
    val totalWeightRows = maxValuesRows?.sum() ?: 1
    val totalWeigth = max(totalWeightColumn, totalWeightRows)

    val weightMapColumn = linkedMapOf(*columnText.map { Pair(it, it.length fdiv totalWeigth) }.toTypedArray()).toList()
    val weightMapRows = linkedMapOf(*maxValuesRows?.map { Pair(it, it fdiv totalWeigth) }?.toTypedArray() ?: emptyArray()).toList()
    val weightList = (0..(weightMapColumn.size-1)).map {
        max(weightMapColumn.getOrNull(it)?.second ?: 0f, weightMapRows.getOrNull(it)?.second ?: 0f)
    }.toList()
    // The LazyColumn will be our table. Notice the use of the weights below
    LazyColumn (Modifier.fillMaxSize().padding(16.dp)) {
        // Here is the header
        item {
            Row (Modifier.background(Color.Gray)) {
                columnText.forEachIndexed { index, col ->
                    TableCell(text = col, weight = weightList[index],
                        onClick = {
                            expanded.value = true
                        })
                }
            }
        }
        // Here are all the lines of your table.
        items(values) {
            Row(Modifier.fillMaxWidth()) {
                it.forEachIndexed { index, it ->
                    TableCell(text = it?: "", weight = weightList[index])
                }
            }
        }
    }
}



