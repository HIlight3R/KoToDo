package io.github.hilight3r.kotodo.plugin

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import io.ktor.server.application.install
import net.kiberion.ktor_scheduler.Scheduler
import org.jobrunr.storage.StorageProviderUtils.DatabaseOptions
import org.jobrunr.storage.sql.common.DefaultSqlStorageProvider
import org.koin.ktor.ext.inject

fun Application.configureJobs() {
    install(Scheduler) {
        val dataSource by inject<HikariDataSource>()
        storageProvider = DefaultSqlStorageProvider(dataSource, dataSource.dialect, DatabaseOptions.CREATE)
        threads = 5
    }
}