plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.github.diegoberaldin.metaphrase.feature.translate"
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

                implementation(projects.domainFormats)

                implementation(projects.domainLanguage.data)
                implementation(projects.domainLanguage.repository)

                implementation(projects.domainProject.data)
                implementation(projects.domainProject.usecase)
                implementation(projects.domainProject.repository)
                implementation(projects.domainSpellcheck)
                implementation(projects.domainTm.usecase)
                implementation(projects.domainGlossary.data)
                implementation(projects.domainGlossary.repository)
                implementation(projects.domainMt.repository)

                implementation(projects.featureTranslate.toolbar)
                implementation(projects.featureTranslate.messages)

                implementation(projects.featureTranslate.dialog.newsegment)
                implementation(projects.featureTranslate.dialog.newterm)

                implementation(projects.featureTranslate.panel)
                implementation(projects.featureTranslate.panel.validate)
                implementation(projects.featureTranslate.panel.matches)
                implementation(projects.featureTranslate.panel.memory)
                implementation(projects.featureTranslate.panel.glossary)
                implementation(projects.featureTranslate.panel.machinetranslation)
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
