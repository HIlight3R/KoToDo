package io.github.hilight3r.kotodo

import io.github.hilight3r.kotodo.plugin.configureDatabaseConnection
import io.github.hilight3r.kotodo.plugin.configureDependencyInjection
import io.github.hilight3r.kotodo.plugin.configureJobs
import io.github.hilight3r.kotodo.plugin.configureTelegramBot
import io.ktor.server.application.Application

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureDependencyInjection()
    configureDatabaseConnection()
    configureTelegramBot()
//    configureJobs()
}