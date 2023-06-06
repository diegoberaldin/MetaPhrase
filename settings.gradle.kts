pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
    }
}

rootProject.name = "MetaPhrase"

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":core-common",
    ":core-localization",
    ":core-data",
    ":core-persistence",
    ":core-repository",

    ":domain-tm",
    ":domain-spellcheck",

    ":feature-main",
    ":feature-main:settings",
    ":feature-intro",

    ":feature-projects",
    ":feature-projects:list",
    ":feature-projects:create",
    ":feature-projects:statistics",

    ":feature-translate",
    ":feature-translate:toolbar",
    ":feature-translate:messages",
    ":feature-translate:newsegment",
    ":feature-translate:panel-validate",
    ":feature-translate:panel-matches",
    ":feature-translate:panel-memory",
    ":feature-translate:panel-glossary",
)
