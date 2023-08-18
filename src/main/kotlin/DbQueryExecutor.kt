import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
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

    private fun queryWithAggregator(fn: (FieldSet) -> Query): List<List<String>> {
        return basicQueryExec(joinedTables, fn, ::queryAggregator)
    }

    private fun <T> basicQueryExec(fieldSet: FieldSet, fn: (FieldSet) -> Query, then: Query.() -> T): T {
        return transaction(DbSettings.db) {
            addLogger(StdOutSqlLogger)
            fieldSet.let(fn).let(then)
        }
    }

    fun getBasic(): List<List<String>> {
        return queryWithAggregator { it.selectAll() }
    }

    fun getSpecificProducts(products: List<Int>): List<List<String>> {
        return queryWithAggregator {
            it.select(Products.id.inList(products))
        }
    }

    fun getSpecificProductsbyName(products: List<String>): List<List<String>> {
        return getSpecificProducts(basicQueryExec(Products.slice(Products.id), { it.select(Products.description.inList(products)) }, {this.map { it[Products.id] }}))
    }

    fun getAllProducts(): List<String> {
        return basicQueryExec(Products.slice(Products.description), { it.selectAll() }, { this.map { it[Products.description] } } )

    }
}