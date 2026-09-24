package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback

object AdMobManager {
    private const val TAG = "AdMobManager"

    // Production AdMob Ad Unit IDs provided by user
    const val PROD_APP_ID = "ca-app-pub-6670494922007372~4901366017"
    const val PROD_BANNER_ID = "ca-app-pub-6670494922007372/4753192209"
    const val PROD_INTERSTITIAL_ID = "ca-app-pub-6670494922007372/1121903103"
    const val PROD_REWARDED_INTERSTITIAL_ID = "ca-app-pub-6670494922007372/6803717034"
    const val PROD_ADDITIONAL_UNIT_ID = "ca-app-pub-6670494922007372/2568872833"

    // Google Official Test Ad Unit IDs (to prevent account flags during testing)
    const val TEST_BANNER_ID = "ca-app-pub-3940256099942544/6300978111"
    const val TEST_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712"
    const val TEST_REWARDED_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/5354046379"

    // Test mode flag: Defaults to true for safe local emulation/development; easily toggled in Settings
    var isTestMode: Boolean = true

    fun getBannerAdUnitId(): String = if (isTestMode) TEST_BANNER_ID else PROD_BANNER_ID
    fun getInterstitialAdUnitId(): String = if (isTestMode) TEST_INTERSTITIAL_ID else PROD_INTERSTITIAL_ID
    fun getRewardedInterstitialAdUnitId(): String = if (isTestMode) TEST_REWARDED_INTERSTITIAL_ID else PROD_REWARDED_INTERSTITIAL_ID

    // -------------------------------------------------------------
    // STRICT AD RULES ENFORCEMENT
    // -------------------------------------------------------------
    // Rule: Show after every 5 quote views/actions
    private const val ACTIONS_BETWEEN_INTERSTITIALS = 5
    // Rule: Minimum 2-minute cooldown between interstitial ads
    private const val INTERSTITIAL_COOLDOWN_MS = 120_000L // 2 minutes
    // Rule: Maximum 3 interstitial ads per user session
    private const val MAX_INTERSTITIALS_PER_SESSION = 3

    private var actionCounter = 0
    private var lastAdShowTimestamp: Long = 0
    private var sessionInterstitialCount = 0

    private var interstitialAd: InterstitialAd? = null
    private var isInterstitialLoading = false

    private var rewardedInterstitialAd: RewardedInterstitialAd? = null
    private var isRewardedLoading = false

    // -------------------------------------------------------------
    // INTERSTITIAL ADS
    // -------------------------------------------------------------
    fun loadInterstitialAd(context: Context) {
        if (interstitialAd != null || isInterstitialLoading) return
        if (sessionInterstitialCount >= MAX_INTERSTITIALS_PER_SESSION) return

        isInterstitialLoading = true
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            getInterstitialAdUnitId(),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isInterstitialLoading = false
                    Log.d(TAG, "Interstitial Ad loaded successfully (testMode=$isTestMode)")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                    isInterstitialLoading = false
                    Log.w(TAG, "Interstitial Ad failed to load: ${loadAdError.message}")
                }
            }
        )
    }

    /**
     * Called on natural quote actions (e.g., viewing next quote).
     * Strictly complies with:
     * - Every 5 actions
     * - Minimum 2-min cooldown
     * - Max 3 per session
     * - Never blocks user if ad isn't ready
     */
    fun onUserQuoteAction(activity: Activity, onContinue: () -> Unit = {}) {
        actionCounter++

        val now = System.currentTimeMillis()
        val cooldownPassed = (now - lastAdShowTimestamp) >= INTERSTITIAL_COOLDOWN_MS
        val withinSessionLimit = sessionInterstitialCount < MAX_INTERSTITIALS_PER_SESSION

        if (actionCounter >= ACTIONS_BETWEEN_INTERSTITIALS && cooldownPassed && withinSessionLimit) {
            actionCounter = 0
            showInterstitialAd(activity, onContinue)
        } else {
            onContinue()
        }
    }

    fun showInterstitialAd(activity: Activity, onAdFinished: () -> Unit = {}) {
        val now = System.currentTimeMillis()
        val cooldownPassed = (now - lastAdShowTimestamp) >= INTERSTITIAL_COOLDOWN_MS
        val withinSessionLimit = sessionInterstitialCount < MAX_INTERSTITIALS_PER_SESSION

        val ad = interstitialAd
        if (ad != null && cooldownPassed && withinSessionLimit) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    lastAdShowTimestamp = System.currentTimeMillis()
                    sessionInterstitialCount++
                    loadInterstitialAd(activity)
                    onAdFinished()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    interstitialAd = null
                    loadInterstitialAd(activity)
                    onAdFinished()
                }
            }
            ad.show(activity)
        } else {
            // Never delay the user if not ready or cooldown not met
            loadInterstitialAd(activity)
            onAdFinished()
        }
    }

    // -------------------------------------------------------------
    // REWARDED INTERSTITIAL ADS
    // -------------------------------------------------------------
    fun loadRewardedInterstitialAd(context: Context) {
        if (rewardedInterstitialAd != null || isRewardedLoading) return

        isRewardedLoading = true
        val adRequest = AdRequest.Builder().build()

        RewardedInterstitialAd.load(
            context,
            getRewardedInterstitialAdUnitId(),
            adRequest,
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    rewardedInterstitialAd = ad
                    isRewardedLoading = false
                    Log.d(TAG, "Rewarded Interstitial Ad loaded successfully")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    rewardedInterstitialAd = null
                    isRewardedLoading = false
                    Log.w(TAG, "Rewarded Interstitial Ad failed to load: ${loadAdError.message}")
                }
            }
        )
    }

    /**
     * Shows Rewarded Interstitial ONLY on explicit voluntary user action.
     * Reward is granted ONLY if the user completes the ad.
     */
    fun showRewardedInterstitialAd(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onAdClosedWithoutReward: () -> Unit = {}
    ) {
        val ad = rewardedInterstitialAd
        if (ad != null) {
            var rewardGiven = false
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    rewardedInterstitialAd = null
                    lastAdShowTimestamp = System.currentTimeMillis()
                    loadRewardedInterstitialAd(activity)
                    if (!rewardGiven) {
                        onAdClosedWithoutReward()
                    }
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    rewardedInterstitialAd = null
                    loadRewardedInterstitialAd(activity)
                    onAdClosedWithoutReward()
                }
            }

            ad.show(activity) { _ ->
                rewardGiven = true
                onRewardEarned()
            }
        } else {
            // If ad is not ready, initiate load and inform user politely
            loadRewardedInterstitialAd(activity)
            onAdClosedWithoutReward()
        }
    }

    fun getSessionStats(): String {
        val remaining = (MAX_INTERSTITIALS_PER_SESSION - sessionInterstitialCount).coerceAtLeast(0)
        return "Session Interstitials: $sessionInterstitialCount/$MAX_INTERSTITIALS_PER_SESSION ($remaining remaining)"
    }
}
