plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "feature.translate.panel.glossary"
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
                implementation(compose.materialIconsExtended)

                implementation(libs.koin)
                implementation(libs.decompose)
                implementation(libs.decompose.extensions)

                implementation(projects.coreCommon)
                implementation(projects.coreLocalization)
                implementation(projects.coreData)

                implementation(projects.domainProject.repository)
                implementation(projects.domainProject.repository)
                implementation(projects.domainGlossary)
                implementation(projects.domainGlossary.repository)
                implementation(projects.domainGlossary.usecase)
            }
        }
    }
}
