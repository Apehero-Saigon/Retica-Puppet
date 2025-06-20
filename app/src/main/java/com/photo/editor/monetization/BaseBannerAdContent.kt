package com.photo.editor.monetization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import apero.aperosg.monetization.adgroup.BannerAdGroup
import apero.aperosg.monetization.ui.BannerAdContent
import com.photo.editor.data.remoteconfig.RemoteConfig

@Composable
fun BaseBannerAdContent(
    bannerAdGroup: BannerAdGroup,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            bannerAdGroup.loadAds(context)
        }
    }

    BannerAdContent(
        bannerAdGroup = bannerAdGroup,
        fastReload = RemoteConfig.commonConfig.fast_reload_banner,
        fastReloadBannerPeriod = RemoteConfig.commonConfig.fast_reload_banner_period,
    )
}