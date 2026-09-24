package com.example.model

enum class QuoteCategory(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val iconName: String
) {
    ALL("ALL", "All Quotes", "सभी विचार", "auto_awesome"),
    SUCCESS("SUCCESS", "Success", "सफलता", "emoji_events"),
    STUDY("STUDY", "Study & Focus", "पढ़ाई और एकाग्रता", "school"),
    LIFE("LIFE", "Life Lessons", "जीवन सूत्र", "spa"),
    CAREER("CAREER", "Career & Growth", "करियर और लक्ष्य", "trending_up"),
    HAPPINESS("HAPPINESS", "Happiness & Peace", "खुशी और शांति", "sentiment_very_satisfied"),
    MORNING("MORNING", "Morning Inspiration", "दैनिक जागरण", "wb_sunny");

    companion object {
        fun fromId(id: String): QuoteCategory {
            return entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: SUCCESS
        }
    }
}
