import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


infix fun Int.fdiv(i: Int): Float = this / i.toFloat()

@Composable
fun TableScreen(columnText: List<String>, values: List<List<String?>>) {
    // Each cell of a column must have the same weight.
    val totalWeight = columnText.map { it.length }.sum()
    val weightMap = linkedMapOf(*columnText.map { Pair(it, it.length fdiv totalWeight) }.toTypedArray())
    val weightList = weightMap.toList()
    // The LazyColumn will be our table. Notice the use of the weights below
    LazyColumn (Modifier.fillMaxSize().padding(16.dp)) {
        // Here is the header
        item {
            Row (Modifier.background(Color.Gray)) {
                columnText.forEach {
                    TableCell(text = it, weight = weightMap[it]?:0f)
                }
            }
        }
        // Here are all the lines of your table.
        items(values) {
            Row(Modifier.fillMaxWidth()) {
                it.forEachIndexed { index, it ->
                    TableCell(text = it?: "", weight = weightList.get(index).second)
                }
            }
        }
    }
}