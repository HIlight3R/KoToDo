plugins {
    application
    kotlin("jvm") version "2.0.21"
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
}

group = "io.github.hilight3r"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven(uri("https://jitpack.io"))
}

val coroutinesVersion: String by project
val koinVersion: String by project
val koinAnnotationsVersion: String by project
val ktorVersion: String by project
val telegramBotVersion: String by project
val exposedVersion: String by project

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.insert-koin:koin-annotations:$koinAnnotationsVersion")
    ksp("io.insert-koin:koin-ksp-compiler:$koinAnnotationsVersion")
    implementation("com.h2database:h2:2.3.232")
    implementation("io.github.dehuckakpyt.telegrambot:telegram-bot-core:$telegramBotVersion")
    implementation("io.github.dehuckakpyt.telegrambot:telegram-bot-ktor:$telegramBotVersion")
    implementation("io.github.dehuckakpyt.telegrambot:telegram-bot-source-exposed:$telegramBotVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-money:$exposedVersion")
    implementation("com.github.kibertoad:ktor-scheduler:2.0.0")
    implementation("org.jobrunr:jobrunr:6.1.1")
    implementation("com.zaxxer:HikariCP:5.0.1")
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}

application {
    mainClass = "io.github.hilight3r.kotodo.ApplicationKt"
}

tasks.withType<JavaExec> {
    file(".env").readLines().forEach {
        if (!it.isEmpty() && !it.startsWith("#")) {
            val (key, value) = it.split("=")
            if (System.getenv(key) == null) environment(key, value)
        }
    }
}