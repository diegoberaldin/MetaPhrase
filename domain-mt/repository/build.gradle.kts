plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.8.21"
}

group = "com.github.diegoberaldin.metaphrase.domain.mt.repository"
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
                implementation(libs.ktor.core)
                implementation(libs.ktor.cio)
                implementation(libs.ktor.negotiation)
                implementation(libs.ktor.serialization.json)

                implementation(projects.coreCommon)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(kotlin("test-junit5"))
                implementation(libs.mockk)
                implementation(libs.ktor.client.mock)
                implementation(projects.coreLocalization)
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
