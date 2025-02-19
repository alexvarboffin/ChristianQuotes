plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
}

android {
    buildFeatures {
        viewBinding = true
    }

    compileSdk = 35
    buildToolsVersion = rootProject.extra["buildToolsVersion0"].toString()

    defaultConfig {
        multiDexEnabled = true
        minSdk = rootProject.extra["minSdkVersion0"].toString().toInt()
        targetSdk = rootProject.extra["targetSdkVersion0"].toString().toInt()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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

    namespace = "com.walhalla.core"
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:${rootProject.extra["compatVersion"]}")
    implementation("com.google.android.material:material:${rootProject.extra["materialVersion"]}")
    implementation("com.google.firebase:firebase-ads:${rootProject.extra["gmsAds"]}")
    implementation(libs.androidx.core.ktx)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(project(":features:ui"))
    implementation(project(":threader"))
    implementation(project(":library"))
    implementation(project(":features:db_oraritreni"))
    implementation(project(":type:type_single"))

    implementation("androidx.room:room-runtime:${rootProject.extra["roomVersion"]}")
    annotationProcessor("androidx.room:room-compiler:${rootProject.extra["roomVersion"]}")
    api("com.github.jd-alexander:likebutton:0.2.3")

    implementation("androidx.lifecycle:lifecycle-process:${rootProject.extra["lifecycle_version"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${rootProject.extra["kotlin_version"]}")
}