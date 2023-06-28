plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.github.diegoberaldin.metaphrase.domain.project.usecase"
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

                implementation(projects.domainProject.data)
                implementation(projects.domainProject.repository)
                implementation(projects.domainLanguage.data)
                implementation(projects.domainLanguage.repository)
            }
        }
        val jvmTest by getting {
            val jvmTest by getting {
                dependencies {
                    implementation(libs.kotlinx.coroutines.test)
                    implementation(kotlin("test-junit5"))
                    implementation(libs.mockk)
                    implementation(projects.coreCommon.testutils)
                    implementation(projects.domainProject.data)
                }
            }
        }
    }
}

tasks.withType<Test>() {
    useJUnitPlatform()
}
