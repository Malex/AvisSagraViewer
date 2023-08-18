import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

object DbSettings {
    val db by lazy {
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        val tmp = PropertiesHolder.get("avis.sagra.database")
            ?.let { PropertiesHolder.get("avis.sagra.database_driver")
                    ?.let { it1 -> Database.connect(it, it1) }
            }
        // TODO Check read only
        tmp
    }
}
