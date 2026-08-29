rootProject.name = "ChristianQuotes"

pluginManagement {
    repositories {
        flatDir {
            dirs("./libs")
        }
        mavenLocal()
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        jcenter()
    }
}
dependencyResolutionManagement {
    repositories {
        flatDir {
            dirs("./libs")
        }
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        jcenter()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":ChInspirefaith")
include(":ChristianQuotesEn")
include(":lib")

include(":photomovie_ui")
project(":photomovie_ui").projectDir = File("C:\\src\\Synced\\WalhallaUI\\features\\vidmaker-102\\photomovie_ui")

include(":photomovie")
project(":photomovie").projectDir = File("C:\\src\\Synced\\WalhallaUI\\features\\vidmaker-102\\photomovie")

include(":library")
project(":library").projectDir = file("D:\\dev\\android\\QUOTES\\01_QuotesPhrases\\library\\")

include(":likebutton")
project(":likebutton").projectDir = File("D:\\dev\\android\\QUOTES\\03_DreamBook\\LikeButton\\likebutton")

include(":threader")
project(":threader").projectDir = File("D:\\dev\\android\\Compatibility\\threader\\")

include(":customView")
project(":customView").projectDir = file("D:\\dev\\android\\QUOTES\\03_DreamBook\\customView")

include(":type:type_single")
include(":type:type_multiple")
include(":type:type_multiple_author")

include(":db_oraritreni")
project(":db_oraritreni").projectDir =
    file("D:\\dev\\android\\QUOTES\\002_ChristianQuotes\\features\\db_oraritreni\\")

include(":features:ui_single")
include(":features:ui_multiple")
include(":features:ui_multiple_author")

apply(from = "C:\\src\\Synced\\WalhallaUI\\kwk\\corelib\\submodules.gradle")
// Override dead D:\walhalla\… stub paths from submodules.gradle
project(":stub:stub_mtracker").projectDir = File("C:\\src\\Synced\\WalhallaUI\\stub\\stub_mtracker")
project(":stub:stub_appmetrica").projectDir = File("C:\\src\\Synced\\WalhallaUI\\stub\\stub_appmetrica")
project(":stub:stub_facebook").projectDir = File("C:\\src\\Synced\\WalhallaUI\\stub\\stub_facebook")
project(":stub:stub_appsflyer").projectDir = File("C:\\src\\Synced\\WalhallaUI\\stub\\stub_appsflyer")
project(":stub:stub_onesignal").projectDir = File("C:\\src\\Synced\\WalhallaUI\\stub\\stub_onesignal")
project(":stub:stub_admob").projectDir = File("C:\\src\\Synced\\WalhallaUI\\stub\\stub_admob")

include(":features:webview")
project(":features:webview").projectDir = file("C:\\src\\Synced\\WalhallaUI\\features\\webview\\")

// Flat names required by :library / :type_* (QuotesPhrases layout)
include(":ui")
project(":ui").projectDir = File("C:\\src\\Synced\\WalhallaUI\\features\\ui\\")

include(":wads")
project(":wads").projectDir = File("C:\\src\\Synced\\WalhallaUI\\features\\wads\\")

include(":features:permissionResolver")
project(":features:permissionResolver").projectDir = File("C:\\src\\Synced\\WalhallaUI\\features\\permissionResolver")

include(":shared")
project(":shared").projectDir = File("C:\\src\\Synced\\WalhallaUI\\shared\\")

include(":mylibrary")
