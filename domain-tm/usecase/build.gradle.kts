plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.github.diegoberaldin.metaphrase.domain.tm.usecase"
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
                implementation(libs.redundent)

                implementation(projects.coreCommon)
                implementation(projects.coreLocalization)

                implementation(projects.domainLanguage.data)
                implementation(projects.domainLanguage.repository)
                implementation(projects.domainProject.data)
                implementation(projects.domainProject.repository)
                implementation(projects.domainTm.data)
                implementation(projects.domainTm.repository)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(kotlin("test-junit5"))
                implementation(libs.mockk)
                implementation(projects.coreCommon.testutils)
            }
        }
    }
}

tasks.withType<Test>() {
    useJUnitPlatform()
}
