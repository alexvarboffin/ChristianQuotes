plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.android.library) apply false
}

// Non-version project keys only (SDK / Kotlin / deps → gradle/libs.versions.toml)
extra.apply {
    set("ONESIGNAL_APP_ID", "")
    set("APPSFLYER_DEV_KEY", "")
    set("javaVersion", 17)
}

subprojects {
    configurations.configureEach {
        exclude(group = "androidx.collection", module = "collection-ktx")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-android-extensions-runtime")
        exclude(group = "com.github.jd-alexander", module = "likebutton")
        exclude(group = "com.github.jd-alexander", module = "LikeButton")
    }
    // Tor/Proxifier often blocks firebasecrashlyticssymbols.googleapis.com
    tasks.configureEach {
        if (name.startsWith("uploadCrashlyticsMappingFile")) {
            enabled = false
        }
    }
}
