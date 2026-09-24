package com.example.ui.viewmodel

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ads.AdMobManager
import com.example.data.QuoteRepository
import com.example.model.Quote
import com.example.model.QuoteCategory
import com.example.notification.NotificationHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class MotivationViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _selectedCategory = MutableStateFlow(QuoteCategory.ALL)
    val selectedCategory: StateFlow<QuoteCategory> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _languageFilter = MutableStateFlow(repository.getLanguagePreference())
    val languageFilter: StateFlow<String> = _languageFilter.asStateFlow()

    private val _dailyOffset = MutableStateFlow(repository.getDailyOffset())
    val dailyOffset: StateFlow<Int> = _dailyOffset.asStateFlow()

    private val _cardStyleIndex = MutableStateFlow(repository.getCardStyleIndex())
    val cardStyleIndex: StateFlow<Int> = _cardStyleIndex.asStateFlow()

    private val _themeMode = MutableStateFlow(repository.getThemeMode())
    val themeMode: StateFlow<String> = _themeMode.asStateFlow()

    private val _streakCount = MutableStateFlow(repository.getStreakCount())
    val streakCount: StateFlow<Int> = _streakCount.asStateFlow()

    private val _notificationEnabled = MutableStateFlow(repository.isNotificationEnabled())
    val notificationEnabled: StateFlow<Boolean> = _notificationEnabled.asStateFlow()

    private val _notificationHour = MutableStateFlow(repository.getNotificationHour())
    val notificationHour: StateFlow<Int> = _notificationHour.asStateFlow()

    private val _notificationMinute = MutableStateFlow(repository.getNotificationMinute())
    val notificationMinute: StateFlow<Int> = _notificationMinute.asStateFlow()

    private val _isTestAdMode = MutableStateFlow(repository.isTestAdMode())
    val isTestAdMode: StateFlow<Boolean> = _isTestAdMode.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.checkAndSeedInitialQuotes()
        }
    }

    val allQuotes: StateFlow<List<Quote>> = repository.allQuotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteQuotes: StateFlow<List<Quote>> = repository.favoriteQuotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered quotes based on category, language, and search query
    val filteredQuotes: StateFlow<List<Quote>> = combine(
        allQuotes,
        _selectedCategory,
        _searchQuery,
        _languageFilter
    ) { quotes, category, query, lang ->
        var list = if (category == QuoteCategory.ALL) {
            quotes
        } else {
            quotes.filter { it.category.equals(category.id, ignoreCase = true) }
        }

        // Language Filter
        if (lang == "HI") {
            list = list.filter { !it.hindiText.isNullOrBlank() }
        } else if (lang == "EN") {
            list = list.filter { it.text.isNotBlank() }
        }

        // Search Query
        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.text.lowercase().contains(q) ||
                it.author.lowercase().contains(q) ||
                (it.hindiText?.lowercase()?.contains(q) == true) ||
                it.tags.lowercase().contains(q)
            }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Daily quote computed from day of the year + user offset
    val dailyQuote: StateFlow<Quote?> = combine(
        allQuotes,
        _dailyOffset
    ) { quotes, offset ->
        if (quotes.isEmpty()) return@combine null
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val index = Math.floorMod(dayOfYear + offset, quotes.size)
        quotes.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun selectTab(tabIndex: Int) {
        _selectedTab.value = tabIndex
    }

    fun setCategory(category: QuoteCategory) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setLanguageFilter(lang: String) {
        _languageFilter.value = lang
        repository.setLanguagePreference(lang)
        val label = when (lang) {
            "HI" -> "हिंदी विचार"
            "EN" -> "English Quotes"
            else -> "Bilingual (English + Hindi)"
        }
        _userMessage.value = "Language set to $label"
    }

    fun setTestAdMode(enabled: Boolean) {
        _isTestAdMode.value = enabled
        repository.setTestAdMode(enabled)
        _userMessage.value = if (enabled) "Switched to AdMob Test Ads" else "Switched to Production AdMob Units"
    }

    fun nextDailyQuote(activity: Activity? = null) {
        val newOffset = _dailyOffset.value + 1
        _dailyOffset.value = newOffset
        repository.setDailyOffset(newOffset)
        _userMessage.value = "✨ New quote loaded"

        // Strictly follows: show interstitial after every 5 quote actions with 2-min cooldown and max 3 per session
        activity?.let {
            AdMobManager.onUserQuoteAction(it)
        }
    }

    fun cycleCardStyle() {
        val next = (_cardStyleIndex.value + 1) % 6
        _cardStyleIndex.value = next
        repository.setCardStyleIndex(next)
    }

    fun toggleFavorite(quote: Quote) {
        viewModelScope.launch {
            val newFavState = !quote.isFavorite
            repository.toggleFavorite(quote.id, newFavState)
            _userMessage.value = if (newFavState) "Saved to favorites ❤️" else "Removed from favorites"
        }
    }

    fun copyQuote(context: Context, quote: Quote) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val textToCopy = when (_languageFilter.value) {
            "HI" -> if (!quote.hindiText.isNullOrBlank()) "“${quote.hindiText}”\n— ${quote.author}" else "“${quote.text}”\n— ${quote.author}"
            "EN" -> "“${quote.text}”\n— ${quote.author}"
            else -> "“${quote.text}”\n— ${quote.author}" +
                    if (!quote.hindiText.isNullOrBlank()) "\n\n${quote.hindiText}" else ""
        } + "\n\n🌅 Daily Rise: Start Your Day with Inspiration"

        val clip = ClipData.newPlainText("Daily Rise Quote", textToCopy)
        clipboard.setPrimaryClip(clip)
        _userMessage.value = "Quote copied to clipboard 📋"
    }

    fun shareQuote(context: Context, quote: Quote) {
        val shareText = when (_languageFilter.value) {
            "HI" -> if (!quote.hindiText.isNullOrBlank()) "“${quote.hindiText}”\n\n— ${quote.author}" else "“${quote.text}”\n\n— ${quote.author}"
            "EN" -> "“${quote.text}”\n\n— ${quote.author}"
            else -> "“${quote.text}”\n\n— ${quote.author}" +
                    if (!quote.hindiText.isNullOrBlank()) "\n\n${quote.hindiText}" else ""
        } + "\n\n🌅 Start Your Day with Inspiration • Daily Rise App"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share Daily Rise Quote")
        shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(shareIntent)
    }

    fun addCustomQuote(
        text: String,
        author: String,
        category: String,
        hindiText: String?,
        language: String
    ) {
        if (text.isBlank() && (hindiText == null || hindiText.isBlank())) {
            _userMessage.value = "Please enter quote text!"
            return
        }
        viewModelScope.launch {
            repository.insertCustomQuote(
                text = if (text.isNotBlank()) text else hindiText ?: "",
                author = author,
                category = category,
                hindiText = hindiText,
                language = language
            )
            _userMessage.value = "🎉 Your inspiring quote was added!"
        }
    }

    fun deleteQuote(quote: Quote) {
        viewModelScope.launch {
            repository.deleteQuote(quote.id)
            _userMessage.value = "Quote deleted"
        }
    }

    fun unlockExtraQuoteWithReward(activity: Activity, quote: Quote) {
        AdMobManager.showRewardedInterstitialAd(
            activity = activity,
            onRewardEarned = {
                viewModelScope.launch {
                    repository.unlockQuote(quote.id)
                    _userMessage.value = "🎉 Extra VIP Quote Unlocked!"
                }
            },
            onAdClosedWithoutReward = {
                _userMessage.value = "Ad skipped. Complete ad to unlock!"
            }
        )
    }

    fun unlockAllQuotesWithReward(activity: Activity) {
        AdMobManager.showRewardedInterstitialAd(
            activity = activity,
            onRewardEarned = {
                viewModelScope.launch {
                    repository.unlockAllQuotes()
                    _userMessage.value = "🎉 All VIP Quotes Successfully Unlocked!"
                }
            },
            onAdClosedWithoutReward = {
                _userMessage.value = "Watch full video ad to unlock all quotes"
            }
        )
    }

    fun setThemeMode(mode: String) {
        _themeMode.value = mode
        repository.setThemeMode(mode)
    }

    fun toggleNotification(context: Context, enabled: Boolean) {
        _notificationEnabled.value = enabled
        repository.setNotificationEnabled(enabled)
        if (enabled) {
            NotificationHelper.scheduleDailyAlarm(context, _notificationHour.value, _notificationMinute.value)
            _userMessage.value = "Morning reminder scheduled for %02d:%02d ⏰".format(_notificationHour.value, _notificationMinute.value)
        } else {
            NotificationHelper.cancelDailyAlarm(context)
            _userMessage.value = "Daily notification turned off"
        }
    }

    fun setNotificationTime(context: Context, hour: Int, minute: Int) {
        _notificationHour.value = hour
        _notificationMinute.value = minute
        repository.setNotificationTime(hour, minute)
        if (_notificationEnabled.value) {
            NotificationHelper.scheduleDailyAlarm(context, hour, minute)
            _userMessage.value = "Reminder updated to %02d:%02d daily 🔔".format(hour, minute)
        }
    }

    fun sendTestNotification(context: Context) {
        val quote = dailyQuote.value ?: allQuotes.value.firstOrNull()
        val text = quote?.text ?: "Rise up and greet the day with purpose."
        val author = quote?.author ?: "Daily Rise"
        NotificationHelper.showQuoteNotification(context, text, author)
        _userMessage.value = "Test notification sent! Check notifications 🔔"
    }

    fun clearMessage() {
        _userMessage.value = null
    }

    class Factory(private val repository: QuoteRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MotivationViewModel(repository) as T
        }
    }
}
