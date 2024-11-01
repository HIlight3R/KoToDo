package io.github.hilight3r.kotodo.plugin

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject
import javax.sql.DataSource

fun Application.configureDatabaseConnection() {
    val dataSource by inject<DataSource>()
    Database.connect(dataSource)

    transaction {
        SchemaUtils.createMissingTablesAndColumns()
    }
}