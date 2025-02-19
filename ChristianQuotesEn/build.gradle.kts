import com.android.build.gradle.internal.dsl.SigningConfig
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    alias(libs.plugins.firebase.crashlytics)
    id("com.google.devtools.ksp")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties().apply {
    load(FileInputStream(keystorePropertiesFile))
}

android {
    compileSdk = 35
    buildToolsVersion = rootProject.extra["buildToolsVersion0"].toString()

    val versionPropsFile = file("version.properties")

    if (versionPropsFile.canRead()) {
        val code = versionCodeDate()

        defaultConfig {
            resConfigs("ru", "uk", "en")
            multiDexEnabled = true
            applicationId = "com.christianquotestoinspire.bibleverses.motivation"
            minSdk = rootProject.extra["minSdkVersion0"].toString().toInt()
            targetSdk = rootProject.extra["targetSdkVersion0"].toString().toInt()
            versionCode = code
            versionName = "1.2.$code"
            setProperty("archivesBaseName", "ChristianQuotesEn")
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    } else {
        throw GradleException("Could not read version.properties!")
    }

    namespace = "com.christianquotestoinspire.bibleverses.motivation"

    signingConfigs {
        create("x") {
            storeFile = file(keystoreProperties["storeFile"].toString())
            storePassword = keystoreProperties["storePassword"].toString()
            keyAlias = keystoreProperties["keyAliasRU"].toString()
            keyPassword = keystoreProperties["keyPassword"].toString()
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("x")
            versionNameSuffix = "-DEMO"
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("x")
            versionNameSuffix = ".release"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:${rootProject.extra["compatVersion"]}")
    implementation("com.google.android.material:material:${rootProject.extra["materialVersion"]}")

    implementation("com.google.android.gms:play-services-ads:${rootProject.extra["gmsAds"]}")
    implementation("com.google.firebase:firebase-ads:${rootProject.extra["gmsAds"]}")

    // Firebase Crashlytics
    implementation("com.google.firebase:firebase-crashlytics:${rootProject.extra["crashlyticsVersion"]}")
    implementation("com.google.firebase:firebase-analytics:${rootProject.extra["analyticsVersion"]}")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.test:rules:1.6.0")
    implementation("androidx.core:core-ktx:1.13.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation(project(":features:ui"))
    implementation(project(":threader"))
    implementation(project(":features:wads"))
    implementation(project(":library"))
    implementation(project(":features:ui_multiple_author"))
    implementation("androidx.preference:preference:1.2.1")

    implementation("androidx.room:room-runtime:${rootProject.extra["roomVersion"]}")
    annotationProcessor("androidx.room:room-compiler:${rootProject.extra["roomVersion"]}")


    implementation("androidx.multidex:multidex:2.0.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra["lifecycle_version"]}")

    implementation(project(":photomovie_ui"))
    implementation(project(":customView"))
}

fun versionCodeDate(): Int {
    return SimpleDateFormat("yyMMdd").format(Date()).toInt()
}