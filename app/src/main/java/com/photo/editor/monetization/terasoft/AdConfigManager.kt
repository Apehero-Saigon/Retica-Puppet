package com.photo.editor.monetization.terasoft

import com.apero.firstopen.core.ads.config.NativeConfig
import com.photo.editor.R

/**
 * Configuration manager for native ad configurations used throughout the app.
 * Contains predefined native ad configurations for language selection and onboarding screens.
 * 
 * @author Phong-Kaster
 */
object AdConfigManager {
    object LFO1 : NativeConfig(
        adUnitId = AdUnitIdManager.NativeLanguage,
        layoutId = R.layout.native_ads_large,
        layoutIdForMeta = R.layout.native_ads_large_meta,
        preloadKey = "native_language_1",
    )
    object LFO2 : NativeConfig(
        adUnitId = AdUnitIdManager.NativeLanguageDup,
        layoutId = R.layout.native_ads_large,
        layoutIdForMeta = R.layout.native_ads_large_meta,
        preloadKey = "native_language_2",
    )
    object Onboarding1 : NativeConfig(
        adUnitId = AdUnitIdManager.NativeOnboard1,
        layoutId = R.layout.native_ads_large,
        layoutIdForMeta = R.layout.native_ads_large_meta,
        preloadKey = "native_onboard_1",
    )
    object Onboarding2 : NativeConfig(
        adUnitId = AdUnitIdManager.NativeOnboard2,
        layoutId = R.layout.native_ads_large,
        layoutIdForMeta = R.layout.native_ads_large_meta,
        preloadKey = "native_onboard_2",
    )
    object Onboarding3 : NativeConfig(
        adUnitId = AdUnitIdManager.NativeOnboard3,
        layoutId = R.layout.native_ads_large,
        layoutIdForMeta = R.layout.native_ads_large_meta,
        preloadKey = "native_onboard_3",
    )
    object OnboardingFullScreen1 : NativeConfig(
        adUnitId = AdUnitIdManager.NativeOnboardFullscreen,
        layoutId = R.layout.native_ads_fullscr,
        preloadKey = "native_onboard_fullscreen_1",
    )
    object OnboardingFullScreen2 : NativeConfig(
        adUnitId = AdUnitIdManager.NativeOnboardFullscreen,
        layoutId = R.layout.native_ads_fullscr,
        preloadKey = "native_onboard_fullscreen_2",
    )
}