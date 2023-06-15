plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "feature.translate"
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

                implementation(projects.domainFormats.android)
                implementation(projects.domainFormats.ios)
                implementation(projects.domainFormats.resx)
                implementation(projects.domainFormats.po)

                implementation(projects.domainProject.data)
                implementation(projects.domainProject.usecase)
                implementation(projects.domainProject.repository)
                implementation(projects.domainSpellcheck)
                implementation(projects.domainTm.usecase)
                implementation(projects.domainGlossary.data)
                implementation(projects.domainGlossary.repository)

                implementation(projects.featureTranslate.toolbar)
                implementation(projects.featureTranslate.messages)

                implementation(projects.featureTranslate.dialog.newsegment)
                implementation(projects.featureTranslate.dialog.newterm)

                implementation(projects.featureTranslate.panel.validate)
                implementation(projects.featureTranslate.panel.matches)
                implementation(projects.featureTranslate.panel.memory)
                implementation(projects.featureTranslate.panel.glossary)
            }
        }
    }
}
