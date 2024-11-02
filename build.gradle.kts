plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktor)
}

group = "io.github.hilight3r"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven(uri("https://jitpack.io"))
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.exposed.core)
    implementation(libs.exposed.crypt)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.exposed.json)
    implementation(libs.exposed.money)
    implementation(libs.h2)
    implementation(libs.hikari.cp)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)
    implementation(libs.telegram.bot.core)
    implementation(libs.telegram.bot.ktor)
    implementation(libs.telegram.bot.source.exposed)
    implementation(libs.db.scheduler)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}

application {
    mainClass = "io.github.hilight3r.kotodo.ApplicationKt"
}

tasks.withType<JavaExec> {
    file(".env").readLines().forEach {
        if (it.isNotEmpty() && !it.startsWith("#")) {
            val (key, value) = it.split("=", limit = 2)
            if (System.getenv(key) == null) environment(key, value)
        }
    }
}