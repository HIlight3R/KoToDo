package io.github.hilight3r.kotodo.models.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

@Suppress("unused")
object ScheduledTasks : Table("scheduled_tasks") {
    private val taskName = varchar("task_name", 100)
    private val taskInstance = varchar("task_instance", 100)
    private val taskData = blob("task_data").nullable()
    private val executionTime = timestamp("execution_time").index("execution_time_idx")
    private val picked = bool("picked")
    private val pickedBy = varchar("picked_by", 50).nullable()
    private val lastSuccess = timestamp("last_success").nullable()
    private val lastFailure = timestamp("last_failure").nullable()
    private val consecutiveFailures = integer("consecutive_failures").nullable()
    private val lastHeartbeat = timestamp("last_heartbeat").nullable().index("last_heartbeat_idx")
    private val version = long("version")

    override val primaryKey = PrimaryKey(taskName, taskInstance)
}