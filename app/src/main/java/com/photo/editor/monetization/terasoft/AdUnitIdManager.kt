package com.photo.editor.monetization.terasoft


import com.apero.firstopen.core.ads.AdUnitId
import com.apero.firstopen.core.ads.TestAd
import com.photo.editor.BuildConfig
/**
 * TEST_NATIVE_VIDEO represents for high floor ad
 * TEST_NATIVE represents for all price ad
 */
object AdUnitIdManager {
    val NativeLanguage = AdUnitId.AdUnitIdDouble(
        adUnitId1 = BuildConfig.native_language_high,
        adUnitId2 = BuildConfig.native_language,
    )
    val NativeLanguageDup = AdUnitId.AdUnitIdDouble(
        adUnitId1 = BuildConfig.native_language_dup_high,
        adUnitId2 = BuildConfig.native_language_dup,
    )

    val NativeOnboard1 = AdUnitId.AdUnitIdDouble(
        adUnitId1 = BuildConfig.native_onboard_high,
        adUnitId2 = BuildConfig.native_onboard,
    )
    val NativeOnboard2 = AdUnitId.AdUnitIdDouble(
        adUnitId1 = BuildConfig.native_onboard_high,
        adUnitId2 = BuildConfig.native_onboard,
    )
    val NativeOnboard3 = AdUnitId.AdUnitIdDouble(
        adUnitId1 = BuildConfig.native_onboard_high,
        adUnitId2 = BuildConfig.native_onboard,
    )
    val NativeOnboardFullscreen = AdUnitId.AdUnitIdDouble(
        adUnitId1 = BuildConfig.native_fullscr_high,
        adUnitId2 = BuildConfig.native_fullscr,
    )
}