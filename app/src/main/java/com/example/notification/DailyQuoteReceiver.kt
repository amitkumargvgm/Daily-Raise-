package com.example.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.MotivationApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Calendar

class DailyQuoteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = MotivationApp.instance.repository
                val quotes = repository.allQuotes.firstOrNull() ?: emptyList()
                if (quotes.isNotEmpty()) {
                    val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
                    val quote = quotes[dayOfYear % quotes.size]
                    NotificationHelper.showQuoteNotification(
                        context = context,
                        quoteText = quote.text,
                        author = quote.author
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
