import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.dokka") version "1.8.20"
}

group = "com.github.diegoberaldin.metaphrase"
version = libs.versions.appVersion

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
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
                implementation(compose.foundation)
                implementation(libs.koin)
                implementation(libs.decompose)
                implementation(libs.decompose.extensions)

                implementation(projects.coreCommon)
                implementation(projects.coreLocalization)
                implementation(projects.corePersistence)

                implementation(projects.domainLanguage)
                implementation(projects.domainProject)
                implementation(projects.domainLanguage.data)
                implementation(projects.domainProject.data)

                implementation(projects.featureMain)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MetaPhrase"
            packageVersion = libs.versions.appVersion.get().substring(0, 5)
            version = libs.versions.buildNumber.get()
            includeAllModules = true
            macOS {
                iconFile.set(project.file("res/icon.icns"))
                setDockNameSameAsPackageName = true
            }
            windows {
                iconFile.set(project.file("res/icon.ico"))
            }
            linux {
                iconFile.set(project.file("res/icon.png"))
            }
        }
    }
}
