plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.project"
version = "1.0-SNAPSHOT"

val kotestVersion = "5.8.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}