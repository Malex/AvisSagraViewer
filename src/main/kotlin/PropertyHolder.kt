import androidx.compose.ui.res.useResource
import java.io.File
import java.util.Properties

private const val CONFIG_FILE = "config.properties"

//val ConfigFolder = File(System.getProperty("compose.application.resources.dir"))

object PropertiesHolder {
    private val properties = Properties()

    init {
        useResource(CONFIG_FILE) {
            val file = it.reader()
            properties.load(file)
        }

    }

    fun get(key: String): String? = properties.getProperty(key)
}