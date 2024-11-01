package io.github.hilight3r.kotodo.plugin

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import javax.sql.DataSource

fun Application.getDatabaseConnectionPool(): DataSource = with(environment.config.config("database")) {
    return HikariDataSource(HikariConfig().apply {
        jdbcUrl = property("url").getString()
        username = property("user").getString()
        password = property("password").getString()
    })
}

fun Application.configureDependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(
            defaultModule,
            module {
                factory { getDatabaseConnectionPool() }
            }
        )
    }
}