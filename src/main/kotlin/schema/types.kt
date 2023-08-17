package schema

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Types : Table("tipologie") {
    val id: Column<Int> = integer("id")
    val description: Column<String> = varchar("descrizione", 255)
    val position: Column<Int> = integer("posizione")
    val visible: Column<Boolean> = bool("visibile")

    override val primaryKey = PrimaryKey(id)
}