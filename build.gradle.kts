plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "com.ecommerce"
version = "1.0-SNAPSHOT"

val kotestVersion = "5.8.0"

repositories {
    mavenCentral()
}

val Project.fullName: String get() = (parent?.fullName?.plus("-") ?: "") + name

subprojects {
    if (file("src/main/kotlin").isDirectory || file("src/main/resources").isDirectory) {
        apply {
            plugin("org.jetbrains.kotlin.jvm")
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
    }
}