import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun ProductsDropDownMenu(menuItems: List<String>, outList: MutableList<String>, expanded:  MutableState<Boolean>) {
    DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
        menuItems.map {str ->
            DropdownMenuItem(
                onClick = { outList.add(str) },
                content = {
                    Checkbox(checked=str in outList, onCheckedChange = {
                        if (it) outList.add(str) else outList.remove(str)
                    })
                    Text(str)
                }
            )
        }
    }
}