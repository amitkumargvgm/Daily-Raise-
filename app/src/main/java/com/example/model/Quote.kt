package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val author: String,
    val category: String, // SUCCESS, STUDY, LIFE, CAREER, HAPPINESS, MORNING
    val hindiText: String? = null,
    val isFavorite: Boolean = false,
    val isUnlocked: Boolean = true,
    val isVip: Boolean = false,
    val isCustom: Boolean = false,
    val language: String = "BOTH", // "BOTH", "EN", "HI"
    val tags: String = "",
    val dateAdded: Long = System.currentTimeMillis()
)
