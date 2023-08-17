import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

object DbSettings {
    val db by lazy {
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        val tmp = Database.connect("jdbc:sqlite:C:\\Users\\a86t\\My Private Documents\\Desktop\\database.db", "org.sqlite.JDBC")
        // TODO Check read only
        tmp
    }
}
