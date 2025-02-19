pluginManagement {
    repositories {
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
    //repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        jcenter()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")



include (":ChInspirefaith")
include (":ChristianQuotesEn")
include (":lib")

//include (":photomovie_ui")

include (":photomovie_ui")
project(":photomovie_ui").projectDir = File("C:\\src\\Android\\vidmaker-102\\photomovie_ui")

include (":photomovie")
project(":photomovie").projectDir = File("C:\\src\\Android\\vidmaker-102\\photomovie")


rootProject.name = "ChristianQuotes"

include(":features:ui")
project(":features:ui").projectDir = file("D:\\walhalla\\sdk\\android\\UI\\features\\ui\\")

include(":library")
project(":library").projectDir = file("D:\\walhalla\\QUOTES\\01_QuotesPhrases\\library\\")

include(":features:wads")
project(":features:wads").projectDir = file("D:\\walhalla\\sdk\\android\\ui\\features\\wads\\")

include(":threader")
project(":threader").projectDir = file("D:\\walhalla\\sdk\\android\\multithreader\\threader\\")

include(":customView")
project(":customView").projectDir = file("D:\\walhalla\\QUOTES\\03_DreamBook\\customView")

include(":type:type_single")
include(":type:type_multiple")
include(":type:type_multiple_author")

include(":features:db_oraritreni")
project(":features:db_oraritreni").projectDir = file("D:\\walhalla\\QUOTES\\02_ChristianQuotes\\features\\db_oraritreni\\")

include(":features:ui_single")
include(":features:ui_multiple")
include(":features:ui_multiple_author")

apply(from = "D:\\walhalla\\sdk\\android\\ui\\kwk\\corelib\\submodules.gradle")

include(":features:webview")
project(":features:webview").projectDir = file("D:\\walhalla\\sdk\\android\\ui\\features\\webview\\")

//include (":kwk:c")
//project(':kwk:c').projectDir = new File('D:\\walhalla\\sdk\\android\\ui\\kwk\\corelib\\')

//include (":kwk:StelthCore")
//project(':kwk:StelthCore').projectDir = new File('D:\\walhalla\\sdk\\android\\ui\\kwk\\StelthCore\\')
//include (":promo")
//project(':promo').projectDir = new File('D:\\walhalla\\QUOTES\\01_QuotesPhrases\\promo\\')

include(":mylibrary")
