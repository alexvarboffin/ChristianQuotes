plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
}

android {
    buildFeatures {
        viewBinding = true
    }

    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()
    namespace = "com.walhalla.core"

    defaultConfig {
        multiDexEnabled = true
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }

    buildTypes {
        getByName("release") {
            //isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            consumerProguardFiles("consumer-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }


    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.ads)
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":ui"))
    implementation(project(":threader"))
    implementation(project(":library"))
    implementation(project(":db_oraritreni"))
    implementation(project(":type:type_single"))

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    api(project(":likebutton"))

    implementation(libs.androidx.lifecycle.process)
    implementation(libs.kotlin.stdlib.jdk8)
}