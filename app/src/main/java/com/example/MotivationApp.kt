package com.example

import android.app.Application
import android.util.Log
import com.example.ads.AdMobManager
import com.example.data.AppDatabase
import com.example.data.QuoteRepository
import com.example.notification.NotificationHelper
import com.google.android.gms.ads.MobileAds

class MotivationApp : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var repository: QuoteRepository
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Initialize Room Database
        database = AppDatabase.getDatabase(this)
        repository = QuoteRepository(database.quoteDao(), this)

        // Initialize Notification Channels
        NotificationHelper.createNotificationChannel(this)

        // Initialize Google Mobile Ads SDK
        try {
            MobileAds.initialize(this) { initializationStatus ->
                Log.d("AdMob", "AdMob initialized successfully: ${initializationStatus.adapterStatusMap}")
            }
        } catch (e: Exception) {
            Log.e("AdMob", "Failed to initialize MobileAds", e)
        }
    }

    companion object {
        lateinit var instance: MotivationApp
            private set
    }
}
