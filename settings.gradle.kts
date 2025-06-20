@file:Suppress("UnstableApiUsage")



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
        maven { url = uri("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven { url = uri("${rootDir}/libs/") }
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        maven { url = uri("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea") }
        maven { url = uri("https://android-sdk.is.com/") }
        maven { url = uri("https://artifact.bytedance.com/repository/pangle") }
        maven {
            url = uri("https://artifactory.apero.vn/artifactory/gradle-release/")
            credentials {
                username = "hoang-amazing"
                password = "apero@123"
            }
        }
        flatDir {
            dirs = setOf(File("libs"))
        }
    }
}

rootProject.name = "Retica-Puppet"
include(":app")
include(":opencv")
include(":baselineprofile")

include(":core:common")
include(":core:overlaybrush")
include(":core:imageprocessor")
include(":core:editimage")
include(":core:localml")
include(":core:imagecrop")
include(":core:gallery")
include(":core:colorpicker")
