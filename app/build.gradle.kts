@file:Suppress("UnstableApiUsage", "UNUSED_VARIABLE")

import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.googleservice)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.distribution)
    alias(libs.plugins.perf)
    alias(libs.plugins.ksp)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.photo.editor"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.retica.aiphotoeditor.camerapro"
        minSdk = 27
        targetSdk = 35
        versionCode = 6
        versionName = "0.4.1-dev"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val formattedDate = SimpleDateFormat("MMM.dd.yyyy").format(Date())
        setProperty("archivesBaseName", "Retica_v${versionName}(${versionCode})_${formattedDate}")

        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a"))
        }

        // Refer https://developer.android.com/guide/topics/resources/app-languages#gradle-config
        resourceConfigurations.addAll(
            listOf(
                "en", "de", "es", "fr", "ja", "ko", "pt",
                "bn", "gu", "hi", "kn", "mr", "or", "ta", "te", "ur"
            )
        )

        buildConfigField(
            "String",
            "ADS_API_KEY",
            "\"tkE4guUbBbMkKckDEt2fwn7i1/IyQEUaXwwAwXUTjE5JKa1AEe6+pm0uDTkVqs7cvZcvskYWUgR/fRQYXPzCspse16bUjtaOaaDcJUwv8JaZtABhnzWQBTuYUl7KzCfHMSoOvo74a8f86a9J5xIvDwsEOFVDr75ay0Vnxp2rzjU=\""
        )
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("key/retica.jks")
            storePassword = "retica"
            keyAlias = "retica"
            keyPassword = "retica"
        }

        create("release") {
            storeFile = file("key/retica.jks")
            storePassword = "retica"
            keyAlias = "retica"
            keyPassword = "retica"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro",
                "retrofit2.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["enableCrashReporting"] = "true"
        }
        debug {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            manifestPlaceholders["enableCrashReporting"] = "false"
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    kotlinOptions {
        jvmTarget = "22"
        freeCompilerArgs += "-opt-in=androidx.constraintlayout.compose.ExperimentalMotionApi"
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        freeCompilerArgs += "-opt-in=kotlinx.coroutines.DelicateCoroutinesApi"
        freeCompilerArgs += "-opt-in=kotlinx.coroutines.FlowPreview"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    flavorDimensions += "default"
    productFlavors {
        val testInterId = "\"ca-app-pub-3940256099942544/1033173712\""
        val testAppOpenId = "\"ca-app-pub-3940256099942544/9257395921\""
        val testNativeId = "\"ca-app-pub-3940256099942544/2247696110\""
        val testBannerId = "\"ca-app-pub-3940256099942544/6300978111\""
        val testRewardId = "\"ca-app-pub-3940256099942544/5224354917\""
        val testRewardInterId = "\"ca-app-pub-3940256099942544/5354046379\""
        val testCollapsibleBannerId = "\"ca-app-pub-3940256099942544/2014213617\""

        create("appDev") {
            manifestPlaceholders["ad_app_id"] = "ca-app-pub-3940256099942544~3347511713"
            buildConfigField("Boolean", "dev", "true")

            buildConfigField("String", "banner_splash", testBannerId)
            buildConfigField("String", "inter_splash_high", testInterId)
            buildConfigField("String", "inter_splash_high_2", testInterId)
            buildConfigField("String", "inter_splash", testInterId)
            buildConfigField("String", "native_full_splash_2", testNativeId)

            buildConfigField("String", "native_language_high", testNativeId)
            buildConfigField("String", "native_language", testNativeId)

            buildConfigField("String", "native_language_dup_high", testNativeId)
            buildConfigField("String", "native_language_dup_high_2", testNativeId)
            buildConfigField("String", "native_language_dup_high_3", testNativeId)
            buildConfigField("String", "native_language_dup", testNativeId)

            buildConfigField("String", "native_language_hindi", testNativeId)
            buildConfigField("String", "native_language_hindi_high", testNativeId)
            buildConfigField("String", "native_language_hindi_dup", testNativeId)
            buildConfigField("String", "native_language_hindi_dup_high", testNativeId)

            buildConfigField("String", "native_welcome", testNativeId)
            buildConfigField("String", "native_welcome_high", testNativeId)
            buildConfigField("String", "native_welcome_dup", testNativeId)
            buildConfigField("String", "native_welcome_dup_high", testNativeId)
            buildConfigField("String", "native_onboard_high", testNativeId)
            buildConfigField("String", "native_onboard", testNativeId)
            buildConfigField("String", "native_fullscr", testNativeId)
            buildConfigField("String", "native_fullscr_high", testNativeId)
            buildConfigField("String", "native_fullscr_2", testNativeId)
            buildConfigField("String", "native_fullscr_high_2", testNativeId)
            buildConfigField("String", "native_home", testNativeId)
            buildConfigField("String", "inter_item_high", testInterId)
            buildConfigField("String", "inter_item", testInterId)
            buildConfigField("String", "banner_lib", testBannerId)
            buildConfigField("String", "banner_all", testBannerId)
            buildConfigField("String", "native_bottom_sheet", testNativeId)
            buildConfigField("String", "inter_save", testInterId)
            buildConfigField("String", "native_saved", testNativeId)
            buildConfigField("String", "native_preview", testNativeId)
            buildConfigField("String", "native_loading", testNativeId)
            buildConfigField("String", "reward_gen_high", testRewardId)
            buildConfigField("String", "reward_gen", testRewardId)
        }

        create("appProduct") {
            manifestPlaceholders["ad_app_id"] = "ca-app-pub-4584260126367940~6136165918"
            buildConfigField("Boolean", "dev", "false")

            buildConfigField("String", "banner_splash", "\"ca-app-pub-4584260126367940/5058887963\"")
            buildConfigField("String", "inter_splash_high", "\"ca-app-pub-4584260126367940/4106551330\"")
            buildConfigField("String", "inter_splash_high_2", "\"ca-app-pub-4584260126367940/2793469660\"")
            buildConfigField("String", "inter_splash", "\"ca-app-pub-4584260126367940/9816149372\"")
            buildConfigField("String", "native_full_splash_2", "\"ca-app-pub-4584260126367940/2626485107\"")

            buildConfigField("String", "native_language_high", "\"ca-app-pub-4584260126367940/1119642957\"")
            buildConfigField("String", "native_language", "\"ca-app-pub-4584260126367940/7526921590\"")

            buildConfigField("String", "native_language_dup_high", "\"ca-app-pub-4584260126367940/8311496016\"")
            buildConfigField("String", "native_language_dup_high_2", "\"ca-app-pub-4584260126367940/9433005992\"")
            buildConfigField("String", "native_language_dup_high_3", "\"ca-app-pub-4584260126367940/9167306322\"")
            buildConfigField("String", "native_language_dup", "\"ca-app-pub-4584260126367940/3068801835\"")

            buildConfigField("String", "native_language_hindi", "\"ca-app-pub-4584260126367940/2537300864\"")
            buildConfigField("String", "native_language_hindi_high", "\"ca-app-pub-4584260126367940/6093418408\"")
            buildConfigField("String", "native_language_hindi_dup", "\"ca-app-pub-4584260126367940/2154173393\"")
            buildConfigField("String", "native_language_hindi_dup_high", "\"ca-app-pub-4584260126367940/6877976912\"")

            buildConfigField("String", "native_welcome", "\"ca-app-pub-4584260126367940/7854224654\"")
            buildConfigField("String", "native_welcome_high", "\"ca-app-pub-4584260126367940/8774771691\"")
            buildConfigField("String", "native_welcome_dup", "\"ca-app-pub-4584260126367940/3039229288\"")
            buildConfigField("String", "native_welcome_dup_high", "\"ca-app-pub-4584260126367940/6148608356\"")
            buildConfigField("String", "native_onboard_high", "\"ca-app-pub-4584260126367940/8129556826\"")
            buildConfigField("String", "native_onboard", "\"ca-app-pub-4584260126367940/9241434306\"")
            buildConfigField("String", "native_fullscr", "\"ca-app-pub-4584260126367940/7928352630\"")
            buildConfigField("String", "native_fullscr_high", "\"ca-app-pub-4584260126367940/6615270968\"")
            buildConfigField("String", "native_fullscr_2", "\"ca-app-pub-4584260126367940/2209363349\"")
            buildConfigField("String", "native_fullscr_high_2", "\"ca-app-pub-4584260126367940/4675744588\"")
            buildConfigField("String", "native_home", "\"ca-app-pub-4584260126367940/2049581241\"")
            buildConfigField("String", "inter_item_high", "\"ca-app-pub-4584260126367940/4683251937\"")
            buildConfigField("String", "inter_item", "\"ca-app-pub-4584260126367940/6617240588\"")
            buildConfigField("String", "banner_lib", "\"ca-app-pub-4584260126367940/5304158912\"")
            buildConfigField("String", "banner_all", "\"ca-app-pub-4584260126367940/2979288022\"")
            buildConfigField("String", "native_bottom_sheet", "\"ca-app-pub-4584260126367940/6297676387\"")
            buildConfigField("String", "inter_save", "\"ca-app-pub-4584260126367940/4984594717\"")
            buildConfigField("String", "native_saved", "\"ca-app-pub-4584260126367940/8577189518\"")

            buildConfigField("String", "native_preview", "\"ca-app-pub-4584260126367940/1492862255\"")
            buildConfigField("String", "native_loading", "\"ca-app-pub-4584260126367940/5376508199\"")
            buildConfigField("String", "reward_gen_high", "\"ca-app-pub-4584260126367940/6076206141\"")
            buildConfigField("String", "reward_gen", "\"ca-app-pub-4584260126367940/4559094431\"")
        }
    }
}

dependencies {
    implementation(platform(libs.kotlin.bom))
    implementation(libs.core.ktx)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.exifinterface)
    implementation(libs.material)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Lifecycle
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.common)

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.animation)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.viewbinding)
    implementation(libs.compose.shimmer)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.graphics.shapes)
    debugImplementation(libs.compose.ui.tooling)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)

    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.camera2)

    // Utilities
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.lottie.compose)
    implementation(libs.colorpicker.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.config)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.perf)

    // Network
//    implementation(libs.ktor.client.core)
//    implementation(libs.ktor.client.okhttp)
//    implementation(libs.ktor.client.android)
//    implementation(libs.ktor.client.logging)
//    implementation(libs.ktor.client.content.negotiation)
//    implementation(libs.ktor.serialization)
//    implementation(libs.logging.interceptor)
//    implementation(libs.converter.gson)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.navigation)
    implementation(libs.koin.startup)
    implementation(libs.koin.ktor)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    // Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // Accompanist
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.permissions)



    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

//    implementation(project(":opencv"))

    implementation(project(":core:common"))
//    implementation(project(":core:overlaybrush"))
//    implementation(project(":core:imageprocessor"))
//    implementation(project(":core:editimage"))
//    implementation(project(":core:localml"))
//    implementation(project(":core:imagecrop"))
//    implementation(project(":core:gallery"))
//    implementation(project(":core:colorpicker"))

    implementation(libs.inappupdate)
    implementation(libs.review)

    // Terasoft Monetization
    implementation("apero-inhouse:first-open:2.5.0-beta06")
    implementation("apero-inhouse:apero-ads:7.20.0")
}

composeCompiler {
    featureFlags = setOf(
        ComposeFeatureFlag.OptimizeNonSkippingGroups
    )

    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}