plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.github.diegoberaldin.metaphrase.domain.glossary.persistence"
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

                implementation(projects.domainGlossary.data)
            }
        }
    }
}
