plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "core.persistence"
version = libs.versions.appVersion.get()

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)

                implementation(libs.koin)
                implementation(libs.h2database)
                implementation(libs.bundles.exposed)

                implementation(projects.coreCommon)
                implementation(projects.coreData)

                implementation(projects.domainGlossary.persistence)
                implementation(projects.domainProject.persistence)
                implementation(projects.domainTm.persistence)
            }
        }
    }
}
