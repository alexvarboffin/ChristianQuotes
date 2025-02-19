package com.christianquotes.inspirefaith

import androidx.multidex.MultiDexApplication
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.InitializationStatus
import com.video.maker.advertManager.RewardManager
import com.video.maker.util.KSUtil
import com.walhalla.domain.repository.from_internet.AdvertAdmobRepository
import com.walhalla.domain.repository.from_internet.AdvertConfig
import com.walhalla.ui.BuildConfig
import com.walhalla.utils.AdmobAdsIds
import com.walhalla.utils.AdvertManager
import com.walhalla.wads.AppOpenManager5

class MyApp : MultiDexApplication() {
    private var appOpenManager: AppOpenManager5? = null


    override fun onCreate() {
        super.onCreate()

        val testDevices: MutableList<String> = ArrayList()
        if (BuildConfig.DEBUG) {
            testDevices.add(AdRequest.DEVICE_ID_EMULATOR)
            testDevices.add("90BB024CB7214225E67797AD71A77EA4")
        }
        val conf =
            RequestConfiguration.Builder() //.SetTagForChildDirectedTreatment(RequestConfiguration.TagForChildDirectedTreatmentTrue)
                //.SetMaxAdContentRating(MAX_AD_CONTENT_RATING_T)MAX_AD_CONTENT_RATING_G
                .setTestDeviceIds(testDevices)
                .build()
        MobileAds.setRequestConfiguration(conf)
        MobileAds.initialize(this) { initializationStatus: InitializationStatus? -> }
        val config = AdvertConfig.newBuilder()
            .setAppId(getString(R.string.app_id))
            .setBannerId(getString(R.string.b1))
            .build()

        repository = AdvertAdmobRepository.getInstance(config)
        appOpenManager = AppOpenManager5(this, R.string.appOpen)

        //Start
        val m: KSUtil = KSUtil.getInstance(applicationContext)

        val filters: MutableSet<Int> = HashSet()
        filters.add(4)
        filters.add(5)

        //filters.add(6);
        filters.add(7)

        val transition: MutableSet<Int> = HashSet()
        transition.add(4)
        transition.add(6)
        transition.add(7)
        transition.add(9)


        val musicpos: MutableSet<Int> = HashSet()
        musicpos.add(4)
        musicpos.add(6)
        musicpos.add(7)
        musicpos.add(9)

        m.initialize(filters, transition, musicpos)


        val m0 = AdmobAdsIds(
            getString(R.string.admob_native_id),
            null,  //getString(R.string.admob_inter_id),
            getString(R.string.admob_reward_ad_id)
        )

        val w = AdvertManager.getInstance()
        w.init(m0)

        val z: RewardManager = RewardManager.getInstance()
        z.init(m0)
        //End
    }

    companion object {
        @JvmField
        var repository: AdvertAdmobRepository? = null
    }
}
