plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.github.diegoberaldin.metaphrase.feature.main.dialog.settings"
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

                implementation(libs.compose.material3)
                implementation(libs.koin)
                implementation(libs.decompose)
                implementation(libs.decompose.extensions)

                implementation(projects.coreCommon)
                implementation(projects.coreLocalization)

                implementation(projects.featureMain.dialog.login)

                implementation(projects.domainLanguage.data)
                implementation(projects.domainLanguage.usecase)
                implementation(projects.domainProject.data)
                implementation(projects.domainProject.usecase)
                implementation(projects.domainMt.repository)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(kotlin("test-junit5"))
                implementation(libs.mockk)
                implementation(libs.turbine)
                implementation(projects.coreCommon.testutils)
                implementation(projects.domainLanguage)
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
