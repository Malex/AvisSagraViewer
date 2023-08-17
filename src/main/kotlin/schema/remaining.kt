package schema

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Remaining : Table("giacenze") {
    val id: Column<Int> = integer("id")
    val starting: Column<Int> = integer("scorta_iniziale")

    override val primaryKey = PrimaryKey(id)
}