plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.github.diegoberaldin.metaphrase.feature.projects"
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

                implementation(projects.domainProject.data)
                implementation(projects.domainProject.repository)
                implementation(projects.domainProject.usecase)

                implementation(projects.featureProjects.list)
                implementation(projects.featureTranslate)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(kotlin("test-junit5"))
                implementation(libs.mockk)
                implementation(libs.turbine)
                implementation(projects.coreCommon.testutils)
                implementation(projects.corePersistence)
                implementation(projects.domainLanguage)
                implementation(projects.domainProject)
                implementation(projects.featureProjects.list)
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
