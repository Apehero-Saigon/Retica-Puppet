package com.photo.editor.monetization.terasoft

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import com.ads.control.ads.AperoAd
import com.ads.control.helper.adnative.NativeAdConfig
import com.apero.firstopen.FirstOpenSDK
import com.apero.firstopen.core.ads.AdUnitId
import com.apero.firstopen.core.ads.TestAd
import com.apero.firstopen.core.ads.resume.FOResumeAdConfig
import com.apero.firstopen.core.ads.resume.FOResumeAdType
import com.apero.firstopen.core.config.SplashConfiguration
import com.apero.firstopen.core.data.model.FOLanguageItem
import com.apero.firstopen.core.data.prefs.FOPrefsManager
import com.apero.firstopen.core.onboarding.component.FOOnboardingHost
import com.apero.firstopen.template1.FOLanguage
import com.apero.firstopen.template1.FOOnboarding
import com.apero.firstopen.template1.FOTemplateAdConfig
import com.apero.firstopen.template1.FOTemplateUiConfig
import com.apero.firstopen.template1.FlowStatusCallback
import com.apero.firstopen.template1.LanguageAdConfig
import com.apero.firstopen.template1.LanguageResult
import com.apero.firstopen.template1.LanguageUiConfig
import com.apero.firstopen.template1.OnboardingAdConfig
import com.apero.firstopen.template1.OnboardingIdentifier
import com.apero.firstopen.template1.OnboardingSingleAdConfig
import com.apero.firstopen.template1.OnboardingSingleUiConfig
import com.apero.firstopen.template1.OnboardingUiConfig
import com.apero.firstopen.template1.SplashAdConfig
import com.apero.firstopen.template1.data.FlowStatus
import com.apero.firstopen.template1.model.FOLanguageModel
import com.apero.firstopen.template1.onboarding.FOOnboardingActivity
import com.apero.firstopen.template1.splash.FOSplashActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.photo.editor.BuildConfig
import com.photo.editor.R
import com.photo.editor.common.util.toast
import com.photo.editor.domain.model.Language
import com.photo.editor.ui.activity.MainActivity2
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

/**
 * 1. Nut next ở màn onboard 3 có độ trễ đang kể khi qua application
 * 2. Banner Splash lâu lâu bị nháy hoặc không xuất hiện banner splash
 * 3. Ở vị trí native_language, có key remote fo_native_language để cho phép xuất hiện/ ẩn quảng cáo nhưng không có
 * key remote config để quyết định ad unit high floor hay all price được load hay không?
 *
 * đây là pần code luồng First open theo Terasoft SDK nha - https://github.com/Apero-Partner/Apero-Module-First-Open-Sample
 * Mifnh dang apply code theo huong dan nay nha ok a
 */
@SuppressLint("CustomSplashScreen")
class TerasoftActivity : FOSplashActivity() {

    private val viewModel: TerasoftViewModel by viewModel()
    private val TAG = this.javaClass.simpleName

    override fun getLayoutRes() = R.layout.terasoft_activity_splash

    // tat bat ads thif lib support nhe. bat / tat qua firebase. // em hieu roi, chac den day la du de em thuc hien yeu cau cua Terasoft roi, em cam on nha
    // voi viec bat/ tat quang cao thi chi can cai dat tren firebase config thoi phai khong anh
    // em muon bat/ tat nativelanguge, native onboard thi chi can khai bao tren firebase thoi hay can phai thao tac them gi o handle remote config khong anh.
    override fun handleRemoteConfig(remoteConfig: FirebaseRemoteConfig) {
    }

    override fun initSplashAdConfig(): SplashAdConfig {
        return SplashAdConfig(
            bannerAd = AdUnitId.AdUnitIdDouble(BuildConfig.banner_splash, BuildConfig.banner_splash),
            interstitialAd = AdUnitId.AdUnitIdDouble(BuildConfig.inter_splash_high, BuildConfig.inter_splash),
        )
    }


    override fun nextScreen(activity: ComponentActivity, data: Intent) {
        val intent = Intent(activity, MainActivity2::class.java)
        intent.putExtras(data)
        activity.startActivity(intent)
    }


    override fun initAdResumeAppConfig(): FOResumeAdConfig {
        val nativeAd = createNativeAdFullScreenConfig()
        return FOResumeAdConfig(
            appOpenAd = FOResumeAdType.AppOpenAdResume(TestAd.TEST_APP_OPEN),
            interstitialNativeAd = FOResumeAdType.InterstitialNativeAdResume(nativeAd),
        )
    }

    private fun createNativeAdFullScreenConfig(): NativeAdConfig {
        return NativeAdConfig(
            idAds = TestAd.TEST_NATIVE,
            canReloadAds = true,
            canShowAds = true,
            layoutId = R.layout.native_ads_fullscr,
        )
    }


    override fun initTemplateUiConfig(): FOTemplateUiConfig {
        val languageUiConfig = LanguageUiConfig(
            layoutId = R.layout.terasoft_activity_language,
            itemLayoutId = R.layout.terasoft_language_element,
            listLanguage = supportedLanguages,
            languageSelected = null,
            languageToolTip = FOLanguageModel(
                Language.Japanese.drawableId,
                Locale.forLanguageTag(Language.Japanese.code).displayName,
                Language.Japanese.code
            ),
        )
//        val onboardingUiConfig = getOnboardingSingleUiConfig()
        val onboardingUiConfig = getOnboardingUiConfigWithNativeFullScreen()
        return FOTemplateUiConfig(languageUiConfig, onboardingUiConfig)
    }

    // getOnboardingUiConfigWithNativeFullScreen - config nay se chuyen man hinh onboard
    fun getOnboardingUiConfigWithNativeFullScreen(): OnboardingUiConfig {
        return OnboardingUiConfig(
            layoutId = R.layout.terasoft_activity_onboard_fullscreen,
            canShowFullScreen = true,
            onboarding1 = FOOnboarding.Ui.Content.Onboarding1(
                R.layout.terasoft_layout_onboard_1
            ),
            onboarding2 = FOOnboarding.Ui.Content.Onboarding2(
                R.layout.terasoft_layout_onboard_2
            ),
            onboarding3 = FOOnboarding.Ui.Content.Onboarding3(
                R.layout.terasoft_layout_onboard_3
            ),
            onboardingFullscreen1 = FOOnboarding.Ui.FullScreen.OnboardingFullScreen1(
                R.layout.terasoft_fragment_onboard_fullscreen
            ),
            onboardingFullscreen2 = FOOnboarding.Ui.FullScreen.OnboardingFullScreen2(
                R.layout.terasoft_fragment_onboard_fullscreen
            ),
        )
    }

    // nguyen nhan la gi vay anh oi, anh chi em voi. k
    // laf congig cua OB bij sai nen no tu dong qua main luon
    override fun initTemplateAdConfig(): FOTemplateAdConfig {
        Log.d(TAG, "initTemplateAdConfig")

        val languageAdConfig = LanguageAdConfig(
            language1 = FOLanguage.Native.NativeLanguage1(AdConfigManager.LFO1),
            language2 = FOLanguage.Native.NativeLanguage2(AdConfigManager.LFO2),
            null
        )

        // em hieu la o day minh cai dat 5 vi tri quang cao thi minh se dung getOnboardingUiConfigWithNativeFullScreen(multiple) dung voi OnboardingAdConfigdung nhu
        // con getOnboardingSingleUiConfig(single) dung voi getOnboardingSingleUiConfig. ban muon dung loai nao nhi . single obvboarding hay multiple. multiple thì

        // nhu cau cua em la co onboard native fullscreen & onboard native . the thi dung multiple như hien tại nhe. -> cho nay em hieu roi anh nha
        val onboardingAdConfig = OnboardingAdConfig(
            onboarding1 = FOOnboarding.Native.Onboarding1(AdConfigManager.Onboarding1),
            onboarding2 = FOOnboarding.Native.Onboarding2(AdConfigManager.Onboarding2),
            onboarding3 = FOOnboarding.Native.Onboarding3(AdConfigManager.Onboarding3),
            onboardingFullscreen1 = FOOnboarding.Native.OnboardingFullScreen1(AdConfigManager.OnboardingFullScreen1),
            onboardingFullscreen2 = FOOnboarding.Native.OnboardingFullScreen2(AdConfigManager.OnboardingFullScreen2)
        )
        return FOTemplateAdConfig(languageAdConfig, onboardingAdConfig)
    }

    override fun updateUI(savedInstanceState: Bundle?) {
        setupFirstOpenListener()
        setupClickListener()
    }

    private fun setupClickListener() {
        var clickCount = 0
        val clickThreshold = 5

        findViewById<View>(R.id.layoutLogo).setOnClickListener {
            if (++clickCount == clickThreshold) {
                AperoAd.getInstance().isShowMessageTester = true
                toast("Show ad unit id")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        AperoAd.getInstance().isShowMessageTester = true // cai dat nhu vay de tu dong bat duoc khum
        // nhieu khi quen click
    }

    private fun setupFirstOpenListener() {
        FirstOpenSDK.setOnLanguageResultListener(object : LanguageResult {
            override fun onResultLanguageSelected(languageResult: FOLanguageItem) {
                viewModel.setLanguageWithCode(languageResult.languageCode)
            }
        })

        FirstOpenSDK.setOnFlowStatusResultListener(object : FlowStatusCallback {
            override fun onFlowStatusFOListener(status: FlowStatus) {
                Log.d(TAG, "FlowStatus: $status")
            }
        })
    }

    /**
     * Gets the type of fullscreen ad used in the splash screen.
     * Returns a type defined in `SplashConfiguration.SplashAdFullScreenType`
     *
     * Mình support bạn xong Mix logic rồi nhé.
     *  return SplashConfiguration.SplashAdFullScreenType.BothInterstitialAndNativeFullScreen(
     *             adUnitInterstitial = AdUnitId.AdUnitIdDouble(
     *                 adUnitId1 = BuildConfig.inter_splash_high,
     *                 adUnitId2 = BuildConfig.inter_splash
     *             ),
     *             nativeAdConfig = NativeAdConfig(
     *                 idAds = BuildConfig.native_fullscr_high,
     *                 canShowAds = true,
     *                 canReloadAds = true,
     *                 layoutId = R.layout.native_ads_fullscr,
     *             ),
     *             null
     *         )
     *
     * Chỗ này nè: - Dùng chung với logic "fo_logic_ad_launcher"
     */
    override fun getSplashFullScreenType(): SplashConfiguration.SplashAdFullScreenType {
        return SplashConfiguration.SplashAdFullScreenType.BothInterstitialAndNativeFullScreen(
            adUnitInterstitial = AdUnitId.AdUnitIdDouble(
                adUnitId1 = BuildConfig.inter_splash_high,
                adUnitId2 = BuildConfig.inter_splash
            ),
            nativeAdConfig = NativeAdConfig(
                idAds = BuildConfig.native_fullscr_high,
                canShowAds = true,
                canReloadAds = true,
                layoutId = R.layout.native_ads_fullscr,
            ),
            null
        )
    }


    private val supportedLanguages = listOf(
        FOLanguageModel(
            Language.Japanese.drawableId,
            Locale.forLanguageTag(Language.Japanese.code).displayName,
            Language.Japanese.code
        ),
        FOLanguageModel(
            Language.Korean.drawableId,
            Locale.forLanguageTag(Language.Korean.code).displayName,
            Language.Korean.code
        ),
        FOLanguageModel(
            Language.English.drawableId,
            Locale.forLanguageTag(Language.English.code).displayName,
            Language.English.code
        ),
        FOLanguageModel(
            Language.Hindi.drawableId,
            Locale.forLanguageTag(Language.Hindi.code).displayName,
            Language.Hindi.code
        ),
        FOLanguageModel(
            Language.French.drawableId,
            Locale.forLanguageTag(Language.French.code).displayName,
            Language.French.code
        ),
        FOLanguageModel(
            Language.German.drawableId,
            Locale.forLanguageTag(Language.German.code).displayName,
            Language.German.code
        ),
        FOLanguageModel(
            Language.Portuguese.drawableId,
            Locale.forLanguageTag(Language.Portuguese.code).displayName,
            Language.Portuguese.code
        ),
        FOLanguageModel(
            Language.Spanish.drawableId,
            Locale.forLanguageTag(Language.Spanish.code).displayName,
            Language.Spanish.code
        ),
    )
}