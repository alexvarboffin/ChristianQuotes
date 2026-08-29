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
    alias(libs.plugins.ksp)
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties().apply {
    load(FileInputStream(keystorePropertiesFile))
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()
    namespace = "com.christianquotestoinspire.bibleverses.motivation"

    val versionPropsFile = file("version.properties")

    if (versionPropsFile.canRead()) {
        val code = versionCodeDate()

        defaultConfig {
            resConfigs("ru", "uk", "en")
            multiDexEnabled = true
            applicationId = "com.christianquotestoinspire.bibleverses.motivation"
            minSdk = libs.versions.android.minSdk.get().toInt()
            targetSdk = libs.versions.android.targetSdk.get().toInt()
            versionCode = code
            versionName = "1.2.$code"
            setProperty("archivesBaseName", "ChristianQuotesEn")
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    } else {
        throw GradleException("Could not read version.properties!")
    }


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
            firebaseCrashlytics {
                mappingFileUploadEnabled = false
            }
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
            firebaseCrashlytics {
                mappingFileUploadEnabled = false
            }
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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.play.services.ads)
    implementation(libs.firebase.ads)

    // Firebase Crashlytics
    implementation(libs.google.firebase.crashlytics)

    implementation(libs.google.firebase.analytics)
    implementation(libs.androidx.navigation.fragment)
    //implementation(libs.androidx.test.rules)
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":ui"))
    implementation(project(":threader"))
    implementation(project(":wads"))
    implementation(project(":library"))
    implementation(project(":features:ui_multiple_author"))
    implementation(libs.androidx.preference.ktx)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)


    implementation(libs.androidx.multidex)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(project(":photomovie_ui"))
    implementation(project(":customView"))
}

fun versionCodeDate(): Int {
    return SimpleDateFormat("yyMMdd").format(Date()).toInt() * 10 + 1
}