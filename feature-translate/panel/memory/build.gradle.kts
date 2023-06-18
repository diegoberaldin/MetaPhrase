plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.github.diegoberaldin.metaphrase.feature.translate.panel.memory"
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

                implementation(projects.domainLanguage.data)
                implementation(projects.domainLanguage.repository)
                implementation(projects.domainLanguage.usecase)
                implementation(projects.domainProject.data)
                implementation(projects.domainProject.repository)
                implementation(projects.domainProject.usecase)
                implementation(projects.domainTm.data)
                implementation(projects.domainTm.repository)
            }
        }
    }
}
