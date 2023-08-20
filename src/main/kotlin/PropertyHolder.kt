import java.io.File
import java.util.Properties

private const val CONFIG_FILE = "config.properties"

val ConfigFolder = File(System.getProperty("compose.application.resources.dir"))

object PropertiesHolder {
    private val properties = Properties()

    init {
        ConfigFolder.resolve(CONFIG_FILE).inputStream().use { properties.load(it) }


    }

    fun get(key: String): String? = properties.getProperty(key)
}