package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.ads.AdMobManager
import com.example.model.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Calendar

class QuoteRepository(
    private val quoteDao: QuoteDao,
    context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("daily_rise_prefs", Context.MODE_PRIVATE)

    val allQuotes: Flow<List<Quote>> = quoteDao.getAllQuotes()
    val favoriteQuotes: Flow<List<Quote>> = quoteDao.getFavoriteQuotes()

    init {
        AdMobManager.isTestMode = prefs.getBoolean("admob_test_mode", true)
    }

    fun getQuotesByCategory(category: String): Flow<List<Quote>> {
        return if (category == "ALL") {
            quoteDao.getAllQuotes()
        } else {
            quoteDao.getQuotesByCategory(category)
        }
    }

    suspend fun checkAndSeedInitialQuotes() = withContext(Dispatchers.IO) {
        val count = quoteDao.getQuotesCount()
        if (count == 0) {
            quoteDao.insertAll(QuoteSeedData.initialQuotes)
        }
    }

    suspend fun insertCustomQuote(
        text: String,
        author: String,
        category: String,
        hindiText: String?,
        language: String
    ) = withContext(Dispatchers.IO) {
        val quote = Quote(
            text = text.trim(),
            author = if (author.isBlank()) "Anonymous" else author.trim(),
            category = category,
            hindiText = hindiText?.trim()?.ifBlank { null },
            isCustom = true,
            language = language,
            isUnlocked = true
        )
        quoteDao.insertQuote(quote)
    }

    suspend fun toggleFavorite(quoteId: Int, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        quoteDao.updateFavorite(quoteId, isFavorite)
    }

    suspend fun unlockQuote(quoteId: Int) = withContext(Dispatchers.IO) {
        quoteDao.unlockQuote(quoteId)
    }

    suspend fun unlockCategory(category: String) = withContext(Dispatchers.IO) {
        quoteDao.unlockCategory(category)
    }

    suspend fun unlockAllQuotes() = withContext(Dispatchers.IO) {
        quoteDao.unlockAllQuotes()
    }

    suspend fun deleteQuote(id: Int) = withContext(Dispatchers.IO) {
        quoteDao.deleteQuote(id)
    }

    // --- Language Preference ---
    // Options: "BOTH" (English + Hindi), "EN" (English only), "HI" (Hindi only)
    fun getLanguagePreference(): String = prefs.getString("language_pref", "BOTH") ?: "BOTH"

    fun setLanguagePreference(lang: String) {
        prefs.edit().putString("language_pref", lang).apply()
    }

    // --- AdMob Test Mode Preference ---
    fun isTestAdMode(): Boolean = prefs.getBoolean("admob_test_mode", true)

    fun setTestAdMode(enabled: Boolean) {
        prefs.edit().putBoolean("admob_test_mode", enabled).apply()
        AdMobManager.isTestMode = enabled
    }

    // --- Streak & Daily Progress ---
    fun getStreakCount(): Int {
        val lastDay = prefs.getInt("last_opened_day_of_year", -1)
        val lastYear = prefs.getInt("last_opened_year", -1)
        val currentStreak = prefs.getInt("current_streak", 1)

        val cal = Calendar.getInstance()
        val currentDay = cal.get(Calendar.DAY_OF_YEAR)
        val currentYear = cal.get(Calendar.YEAR)

        if (lastDay == currentDay && lastYear == currentYear) {
            return currentStreak
        }

        cal.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = cal.get(Calendar.DAY_OF_YEAR)
        val yesterdayYear = cal.get(Calendar.YEAR)

        val updatedStreak = if (lastDay == yesterday && lastYear == yesterdayYear) {
            currentStreak + 1
        } else if (lastDay == -1) {
            1
        } else {
            1
        }

        prefs.edit()
            .putInt("last_opened_day_of_year", currentDay)
            .putInt("last_opened_year", currentYear)
            .putInt("current_streak", updatedStreak)
            .apply()

        return updatedStreak
    }

    // --- Theme Preference ("SYSTEM", "LIGHT", "DARK") ---
    fun getThemeMode(): String = prefs.getString("theme_mode", "SYSTEM") ?: "SYSTEM"

    fun setThemeMode(mode: String) {
        prefs.edit().putString("theme_mode", mode).apply()
    }

    // --- Notification Preferences ---
    fun isNotificationEnabled(): Boolean = prefs.getBoolean("daily_notification_enabled", true)

    fun setNotificationEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("daily_notification_enabled", enabled).apply()
    }

    fun getNotificationHour(): Int = prefs.getInt("notification_hour", 8) // 8 AM
    fun getNotificationMinute(): Int = prefs.getInt("notification_minute", 0)

    fun setNotificationTime(hour: Int, minute: Int) {
        prefs.edit()
            .putInt("notification_hour", hour)
            .putInt("notification_minute", minute)
            .apply()
    }

    fun getDailyOffset(): Int = prefs.getInt("daily_quote_offset", 0)

    fun setDailyOffset(offset: Int) {
        prefs.edit().putInt("daily_quote_offset", offset).apply()
    }

    fun getCardStyleIndex(): Int = prefs.getInt("card_style_index", 0)

    fun setCardStyleIndex(index: Int) {
        prefs.edit().putInt("card_style_index", index).apply()
    }
}
