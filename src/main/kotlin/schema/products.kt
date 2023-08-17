package schema

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Products : Table("articoli") {
    val id: Column<Int> = integer("id")
    val description: Column<String> = varchar("descrizione", 255)
    val position: Column<Int> = integer("posizione")
    val type = reference("id_tipologia", Types.id)
    val remaining = reference("id_giacenza", Remaining.id).nullable()

    override val primaryKey = PrimaryKey(id)
}