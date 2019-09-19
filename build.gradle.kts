val ktor_version: String by project
val koroutines_version: String by project
val koin_version: String by project
val logback_version: String by project
val kotlintest_version: String by project

apply(from = "gradle/credentials.gradle.kts")

plugins {
    application
    kotlin("jvm") version "1.3.41"
}

group = "spotify-api-explorer"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks.named<JavaExec>("run") {
    doFirst {
        environment(
            "SPOTIFY_CLIENT_ID" to project.extra["spotify_client_id"],
            "SPOTIFY_CLIENT_SECRET" to project.extra["spotify_client_secret"]
        )
    }
}

repositories {
    mavenLocal()
    
    if (project.extra.has("repositoryMirrors")) {
        val repositoryMirrors: String by project
        repositoryMirrors.split(',').forEach { mirrorUrl ->
            maven { url = uri(mirrorUrl) }
        }
    } else {
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/ktor") }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$koroutines_version")

    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-freemarker:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")

    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    implementation("io.ktor:ktor-client-json:$ktor_version")
    implementation("io.ktor:ktor-client-gson:$ktor_version")
    runtimeOnly("io.ktor:ktor-client-auth:$ktor_version")
    implementation("io.ktor:ktor-client-logging-jvm:$ktor_version")

    implementation("org.koin:koin-ktor:$koin_version")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$koroutines_version")
    testImplementation("io.kotlintest:kotlintest-assertions-ktor:$kotlintest_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("io.ktor:ktor-client-mock:$ktor_version")
    testImplementation("io.ktor:ktor-client-mock-jvm:$ktor_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("main")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
