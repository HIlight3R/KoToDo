package io.github.hilight3r.kotodo.plugin

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.hilight3r.kotodo.models.tables.ScheduledTasks
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject
import javax.sql.DataSource

val Application.databaseConnectionPool: DataSource
    get() = with(environment.config.config("database")) {
        return HikariDataSource(HikariConfig().apply {
            jdbcUrl = property("url").getString()
            username = property("user").getString()
            password = property("password").getString()
        })
    }

fun Application.configureDatabaseConnection() {
    val dataSource by inject<DataSource>()
    val db = Database.connect(dataSource)
    TransactionManager.defaultDatabase = db
    transaction {
        SchemaUtils.createMissingTablesAndColumns(ScheduledTasks)
    }
}