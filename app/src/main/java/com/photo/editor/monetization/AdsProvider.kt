package com.photo.editor.monetization

import com.ads.control.admob.AppOpenManager
import com.photo.editor.BuildConfig
import com.photo.editor.data.remoteconfig.AdsConfig
import com.photo.editor.data.remoteconfig.RemoteConfig
import com.photo.editor.platform.app.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


/**
 * Ads Script - https://docs.google.com/spreadsheets/d/1QmqZosS-1dD0Hyqzmj913vbaSgJnE8y6hwTsW4nQudQ/edit?gid=2038617560#gid=2038617560
 */
object AdsProvider {
    private const val TAG = "AdsProvider"

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var enableAppOpenResume: Boolean = true

    // Flow containing if remote config and app billing initialized
    private val _adsConfigSet = MutableStateFlow(false)
    val adsConfigSet = _adsConfigSet.asStateFlow()

    var config: AdsConfig = AdsConfig()
        set(value) {
            field = value

            // Config open resume
            //enableAppOpenResume = config.open_resume
            //if (!config.open_resume) AppOpenManager.getInstance().disableAppResume()
        }

//    val bannerSplash by lazy { createBannerSplash() }
//    val interSplash by lazy { createInterSplash() }
//    val nativeLanguage by lazy { createNativeLanguage() }
//    val nativeLanguageDup by lazy { createNativeLanguageDup() }
//    val nativeLanguageHindi by lazy { createNativeLanguageHindi() }
//    val nativeLanguageHindiDup by lazy { createNativeLanguageHindiDup() }
//    val nativeWelcome by lazy { createNativeWelcome() }
//    val nativeWelcomeDup by lazy { createNativeWelcomeDup() }
//    val nativeOnboard by lazy { createNativeOnboard() }
//    val nativeFullScr by lazy { createNativeFullScr() }
//    val nativeFullScr2 by lazy { createNativeFullScr2() }
//    val nativeHome by lazy { createNativeHome() }
//    val interItem by lazy { createInterItem() }
//    val bannerLib by lazy { createBannerLib() }
//    val bannerAll by lazy { createBannerAll() }
//    val bannerOnboard1 by lazy { createBannerOnboard() }
//    val bannerOnboard2 by lazy { createBannerOnboard() }
//
//    val nativeBottomSheet by lazy { createNativeBottomSheet() }
//    val interSave by lazy { createInterSave() }
//    val nativeSaved by lazy { createNativeSaved() }
//    val nativePreview by lazy { createNativePreview() }
//    val nativeLoading by lazy { createNativeLoading() }
//    val rewardGen by lazy { createRewardGen() }

    var experimentalFeatureEnabled: Boolean = false
    var interSplashShown: Boolean = false
    var interSplashShowing: Boolean = false
    var impressionNativeLanguageDupName: String = ""

    init {
//        MonetizationConfig.preventConsecutiveInter = true

        // Listen to remote config fetch status and billing initialization and update ads config
        coroutineScope.launch {
            // Wait for remote config to be fetched
            RemoteConfig.remoteConfigSet.filter { it }.first()
            // Wait for app billing for init
//            AppBilling.initFinishFlow.filter { it }.first()

            combine(
                RemoteConfig.adsConfigFlow,
                RemoteConfig.commonConfigFlow
            ) { remoteConfig, commonConfig ->
                remoteConfig to commonConfig
            }.collectLatest { (remoteConfig, commonConfig) ->
                if (commonConfig.disable_ads) {
                    config = AdsConfig.disableAllAdsConfig
                } else {
                    config = remoteConfig
                }

                if (BuildConfig.DEBUG) {
//                    config = AdsConfig.disableAllAdsConfig
                }

                _adsConfigSet.value = true
            }
        }
    }

    fun enableAppResume() {
        if (enableAppOpenResume) AppOpenManager.getInstance().enableAppResume()
    }

    fun disableAppResume() {
        AppOpenManager.getInstance().disableAppResume()
    }

//    private fun createBannerSplash(): BannerAdGroup {
//        return BannerAdGroup(
//            BuildConfig.banner_splash to "banner_splash",
//            name = "banner_splash",
//        ).apply { config(config.banner_splash) }
//    }
//
//    private fun createInterSplash(): InterstitialAdGroup {
//        return InterstitialAdGroup(
//            BuildConfig.inter_splash_high to "inter_splash_high",
//            BuildConfig.inter_splash_high_2 to "inter_splash_high_2",
//            BuildConfig.inter_splash to "inter_splash",
//            name = "inter_splash",
//            delayBetweenRequests = 500,
//        ).apply {
//            config(config.inter_splash_high, config.inter_splash_high_2, config.inter_splash)
//        }
//    }
//
//    private fun createNativeLanguage(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_language_high to "native_language_high",
//            BuildConfig.native_language to "native_language",
//            name = "native_language",
//            delayBetweenRequests = 500,
//            onImpression = { _, adName ->
//                if (adName != "native_language") {
//                    experimentalFeatureEnabled = true
//                }
//            },
//        ).apply {
//            config(config.native_language_high, config.native_language)
//        }
//    }
//
//    private fun createNativeLanguageDup(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_language_dup_high to "native_language_dup_high",
//            BuildConfig.native_language_dup_high_2 to "native_language_dup_high_2",
//            BuildConfig.native_language_dup_high_3 to "native_language_dup_high_3",
//            BuildConfig.native_language_dup to "native_language_dup",
//            name = "native_language_dup",
//            delayBetweenRequests = 500,
//            onImpression = { _, adName ->
//                impressionNativeLanguageDupName = adName
//                if (adName != "native_language_dup") {
//                    experimentalFeatureEnabled = true
//                }
//            },
//        ).apply {
//            config(
//                config.native_language_dup_high,
//                config.native_language_dup_high_2,
//                config.native_language_dup_high_3,
//                config.native_language_dup
//            )
//        }
//    }
//
//    private fun createNativeLanguageHindi(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_language_hindi_high to "native_language_hindi_high",
//            BuildConfig.native_language_hindi to "native_language_hindi",
//            name = "native_language_hindi",
//            delayBetweenRequests = 500,
//            onImpression = { _, adName ->
//                if (adName != "native_language_hindi") {
//                    experimentalFeatureEnabled = true
//                }
//            }
//        ).apply {
//            config(config.native_language_hindi_high, config.native_language_hindi)
//        }
//    }
//
//    private fun createNativeLanguageHindiDup(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_language_hindi_dup_high to "native_language_hindi_dup_high",
//            BuildConfig.native_language_hindi_dup to "native_language_hindi_dup",
//            name = "native_language_hindi_dup",
//            delayBetweenRequests = 500,
//            onImpression = { _, adName ->
//                if (adName != "native_language_hindi_dup") {
//                    experimentalFeatureEnabled = true
//                }
//            }
//        ).apply {
//            config(config.native_language_hindi_dup_high, config.native_language_hindi_dup)
//        }
//    }
//
//    private fun createNativeWelcome(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_welcome_high to "native_welcome_high",
//            BuildConfig.native_welcome to "native_welcome",
//            name = "native_welcome",
//            delayBetweenRequests = 500,
//            onImpression = { _, adName ->
//                if (adName != "native_welcome") {
//                    experimentalFeatureEnabled = true
//                }
//            },
//        ).apply {
//            config(config.native_welcome_high, config.native_welcome)
//        }
//    }
//
//    private fun createNativeWelcomeDup(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_welcome_dup_high to "native_welcome_dup_high",
//            BuildConfig.native_welcome_dup to "native_welcome_dup",
//            name = "native_welcome_dup",
//            delayBetweenRequests = 500,
//            onImpression = { _, adName ->
//                if (adName != "native_welcome_dup") {
//                    experimentalFeatureEnabled = true
//                }
//            },
//        ).apply {
//            config(config.native_welcome_dup_high, config.native_welcome_dup)
//        }
//    }
//
//    private fun createNativeOnboard(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_onboard_high to "native_onboard_high",
//            BuildConfig.native_onboard to "native_onboard",
//            name = "native_onboard",
//            delayBetweenRequests = 500,
//            onImpression = { _, adName ->
//                if (adName != "native_onboard") {
//                    experimentalFeatureEnabled = true
//                }
//            },
//        ).apply {
//            config(config.native_onboard_high, config.native_onboard)
//        }
//    }
//
//    private fun createNativeFullScr(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_fullscr_high to "native_fullscr_high",
//            BuildConfig.native_fullscr to "native_fullscr",
//            name = "native_fullscr",
//            delayBetweenRequests = 500,
//            isFullScreen = true,
//        ).apply {
//            config(config.native_fullscr_high, config.native_fullscr)
//        }
//    }
//
//    private fun createNativeFullScr2(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_fullscr_high_2 to "native_fullscr_high_2",
//            BuildConfig.native_fullscr_2 to "native_fullscr_2",
//            name = "native_fullscr_2",
//            delayBetweenRequests = 500,
//            isFullScreen = true,
//        ).apply {
//            config(config.native_fullscr_high_2, config.native_fullscr_2)
//        }
//    }
//
//    private fun createNativeHome(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_home to "native_home",
//            name = "native_home",
//        ).apply {
//            config(config.native_home)
//        }
//    }
//
//    private fun createInterItem(): InterstitialAdGroup {
//        return InterstitialAdGroup(
//            BuildConfig.inter_item_high to "inter_item_high",
//            BuildConfig.inter_item to "inter_item",
//            name = "inter_item",
//            delayBetweenRequests = 500,
//            steps = RemoteConfig.commonConfig.inter_item_steps,
//            showAtFirstStep = false,
//        ).apply {
//            config(config.inter_item_high, config.inter_item)
//        }
//    }
//
//    private fun createBannerLib(): BannerAdGroup {
//        return BannerAdGroup(
//            BuildConfig.banner_lib to "banner_lib",
//            name = "banner_lib",
//            adSize = BannerAdUnit.BannerAdSize.LargeBanner,
//        ).apply {
//            config(config.banner_lib)
//        }
//    }
//
//    private fun createBannerAll(): BannerAdGroup {
//        return BannerAdGroup(
//            BuildConfig.banner_all to "banner_all",
//            name = "banner_all",
//        ).apply {
//            config(config.banner_all)
//        }
//    }
//
//    private fun createNativeBottomSheet(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_bottom_sheet to "native_bottom_sheet",
//            name = "native_bottom_sheet",
//        ).apply {
//            config(config.native_bottom_sheet)
//        }
//    }
//
//    private fun createInterSave(): InterstitialAdGroup {
//        return InterstitialAdGroup(
//            BuildConfig.inter_save to "inter_save",
//            name = "inter_save",
//        ).apply {
//            config(config.inter_save)
//        }
//    }
//
//    private fun createNativeSaved(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_saved to "native_saved",
//            name = "native_saved",
//        ).apply {
//            config(config.native_saved)
//        }
//    }
//
//    private fun createNativePreview(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_preview to "native_preview",
//            name = "native_preview",
//        ).apply {
//            config(config.native_preview)
//        }
//    }
//
//    private fun createNativeLoading(): NativeAdGroup {
//        return NativeAdGroup(
//            BuildConfig.native_loading to "native_loading",
//            name = "native_loading",
//        ).apply {
//            config(config.native_loading)
//        }
//    }
//
//    private fun createRewardGen(): RewardAdGroup {
//        return RewardAdGroup(
//            BuildConfig.reward_gen_high to "reward_gen_high",
//            BuildConfig.reward_gen to "reward_gen",
//            name = "reward_gen",
//            rewardType = RewardAdUnit.RewardAdType.RewardVideo,
//        ).apply { config(config.reward_gen_high, config.reward_gen) }
//    }
//
//
//    private fun createBannerOnboard(): BannerAdGroup {
//        return BannerAdGroup(
//            BuildConfig.banner_all to "banner_onboard",
//            name = "banner_onboard",
//        ).apply { config(config.banner_onboard) }
//    }

}