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
    ":core-common:testutils",
    ":core-localization",
    ":core-persistence",

    ":domain-language",
    ":domain-language:data",
    ":domain-language:persistence",
    ":domain-language:repository",
    ":domain-language:usecase",

    ":domain-project",
    ":domain-project:data",
    ":domain-project:persistence",
    ":domain-project:repository",
    ":domain-project:usecase",

    ":domain-formats",

    ":domain-tm",
    ":domain-tm:data",
    ":domain-tm:persistence",
    ":domain-tm:repository",
    ":domain-tm:usecase",

    ":domain-spellcheck",

    ":domain-glossary",
    ":domain-glossary:persistence",
    ":domain-glossary:data",
    ":domain-glossary:repository",
    ":domain-glossary:usecase",

    ":domain-mt",
    ":domain-mt:repository",

    ":feature-main",
    ":feature-main:dialog:settings",
    ":feature-main:dialog:login",
    ":feature-intro",

    ":feature-projects",
    ":feature-projects:list",
    ":feature-projects:dialog:newproject",
    ":feature-projects:dialog:statistics",
    ":feature-projects:dialog:export",
    ":feature-projects:dialog:import",

    ":feature-translate",
    ":feature-translate:dialog:newsegment",
    ":feature-translate:dialog:newterm",
    ":feature-translate:toolbar",
    ":feature-translate:messages",

    ":feature-translate:panel:validate",
    ":feature-translate:panel:matches",
    ":feature-translate:panel:memory",
    ":feature-translate:panel:glossary",
    ":feature-translate:panel:machinetranslation",
)
