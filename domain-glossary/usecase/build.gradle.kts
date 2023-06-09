plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.github.diegoberaldin.metaphrase.domain.glossary.usecase"
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
                implementation(libs.commons.csv)

                implementation(projects.coreCommon)

                implementation(projects.domainSpellcheck)
                implementation(projects.domainGlossary.data)
                implementation(projects.domainGlossary.repository)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(kotlin("test-junit5"))
                implementation(libs.mockk)
                implementation(projects.coreCommon.testutils)
                implementation(projects.domainGlossary.data)
            }
        }
    }
}

tasks.withType<Test>() {
    useJUnitPlatform()
}
