package schema

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import schema.OrderItems.nullable

object Orders : Table("ordini") {
    val id: Column<Int> = integer("id")

    override val primaryKey = PrimaryKey(id)
}

object OrderRows : Table("righe") {
    val id: Column<Int> = integer("id")
    val quantity: Column<Int> = integer("quantita")
    val orderId = reference("id_ordine", Orders.id)

    override val primaryKey = PrimaryKey(id)
}

object OrderItems : Table("righe_articoli") {
    val id: Column<Int> = integer("id")
    val orderId = reference("id_riga", OrderRows.id)
    val productId = reference("posizione", Products.id).nullable()

    override val primaryKey = PrimaryKey(id)
}