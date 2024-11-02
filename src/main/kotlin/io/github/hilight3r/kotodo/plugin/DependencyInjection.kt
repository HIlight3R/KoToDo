package io.github.hilight3r.kotodo.plugin

import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(
            defaultModule,
            module {
                factory { databaseConnectionPool }
                single { scheduler }
            }
        )
    }
}