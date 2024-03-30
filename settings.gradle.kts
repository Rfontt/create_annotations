rootProject.name = "e-commerce"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    "ecommerce-core",
    "ecommerce-core:domain",
    "ecommerce-core:application",
    "ecommerce-core:adapters"
)

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}