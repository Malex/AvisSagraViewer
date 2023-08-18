import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import schema.OrderItems
import schema.OrderRows
import schema.Products
import schema.Types

class DbQueryExecutor {

    private val joinedTables = (Products innerJoin Types innerJoin OrderItems innerJoin OrderRows).slice(
        Types.description,
        Products.description,
        OrderRows.quantity,
        Products.id
    )

    private fun queryAggregator(input: Query): List<List<String>> {
        return input.groupBy { it[Products.id] }
            .map { entry ->
                val mapValue = entry.value
                mapValue
                    .map {
                        Triple(
                            it[Types.description],
                            it[Products.description],
                            it.getOrNull(OrderRows.quantity) ?: 0
                        )
                    }
                    .reduce { acc,
                              trip ->
                        Triple(acc.first, acc.second, acc.third + trip.third)
                    }
            }.map { triple -> listOf(triple.first, triple.second, triple.third).map { it.toString() } }
    }

    private fun queryUser(fn: (FieldSet) -> Query): List<List<String>> {
        return transaction(DbSettings.db) {
            addLogger(StdOutSqlLogger)
            joinedTables.let(fn).let {
                queryAggregator(it)
            }
        }
    }

    fun getBasic(): List<List<String>> {
        return queryUser { it.selectAll() }
    }
}