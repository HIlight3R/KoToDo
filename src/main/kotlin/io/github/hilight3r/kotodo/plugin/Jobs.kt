package io.github.hilight3r.kotodo.plugin

import io.github.flaxoos.ktor.server.plugins.taskscheduling.TaskScheduling
import io.github.flaxoos.ktor.server.plugins.taskscheduling.managers.lock.database.DefaultTaskLockTable
import io.github.flaxoos.ktor.server.plugins.taskscheduling.managers.lock.database.jdbc
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject
import javax.sql.DataSource

fun Application.configureJobs() {
    install(TaskScheduling) {
        jdbc {
            val dataSource by inject<DataSource>()
            database = Database.connect(dataSource)
        }.also {
            transaction {
                SchemaUtils.create(DefaultTaskLockTable)
            }
        }
        task {
            name = "test"
            kronSchedule = {
                seconds {
                    from(0) every 5
                }
            }
            task = {
                log.info("My task is running: $it")
            }
        }
    }
}