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
                implementation(projects.coreData)

                implementation(projects.coreRepository)
                implementation(projects.domainLanguage)
                implementation(projects.domainAndroid)
                implementation(projects.domainIos)

                implementation(projects.featureTranslate.toolbar)
                implementation(projects.featureTranslate.messages)
                implementation(projects.featureTranslate.newsegment)
                implementation(projects.featureTranslate.panelValidate)
                implementation(projects.featureTranslate.panelMatches)
                implementation(projects.featureTranslate.panelMemory)
                implementation(projects.featureTranslate.panelGlossary)
            }
        }
    }
}
