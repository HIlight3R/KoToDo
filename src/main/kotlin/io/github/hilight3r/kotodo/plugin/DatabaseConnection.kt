package io.github.hilight3r.kotodo.plugin

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jobrunr.storage.sql.common.db.dialect.*
import org.koin.core.annotation.Single

val HikariDataSource.dialect: Dialect
    get() = when (this.jdbcUrl.split(':')[1]) {
        "h2" -> H2Dialect()
        "oracle" -> OracleDialect()
        "sqlserver" -> SQLServerDialect()
        else -> AnsiDialect()
    }

@Single
// TODO: Fix KSP error!
fun Application.getDataSource(): HikariDataSource = with(environment.config.config("database")) {
    HikariDataSource(HikariConfig().apply {
        jdbcUrl = property("url").getString()
        username = property("user").getString()
        password = property("password").getString()
    })
}

fun Application.configureDatabaseConnection() {
    Database.connect(getDataSource())

    transaction {
        SchemaUtils.createMissingTablesAndColumns()
    }
}