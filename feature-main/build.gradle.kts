plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.github.diegoberaldin.metaphrase.feature.main"
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

                implementation(projects.domainLanguage.data)
                implementation(projects.domainProject.data)
                implementation(projects.domainProject.repository)
                implementation(projects.domainProject.usecase)
                implementation(projects.domainTm.usecase)
                implementation(projects.domainGlossary.usecase)
                implementation(projects.domainMt.repository)

                implementation(projects.featureIntro)
                implementation(projects.featureProjects)

                implementation(projects.featureProjects.dialog.newproject)
                implementation(projects.featureProjects.dialog.statistics)
                implementation(projects.featureProjects.dialog.import)
                implementation(projects.featureProjects.dialog.export)
                implementation(projects.featureMain.dialog.settings)
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
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
