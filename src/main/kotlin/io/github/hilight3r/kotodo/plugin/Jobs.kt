package io.github.hilight3r.kotodo.plugin

import com.github.kagkarlsson.scheduler.Scheduler
import com.github.kagkarlsson.scheduler.task.helper.Tasks
import com.github.kagkarlsson.scheduler.task.schedule.FixedDelay
import io.ktor.server.application.*
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject
import javax.sql.DataSource

val Application.scheduler: Scheduler
    get() = Scheduler
        .create(get<DataSource>())
        .startTasks(
            Tasks.recurring("printer", FixedDelay.ofSeconds(5))
                .execute { _, _ -> run { log.info("Hello, world!") } }
        )
        .registerShutdownHook()
        .build()

fun Application.configureJobs() {
    val scheduler by inject<Scheduler>()
    scheduler.start()
}
