package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary Brand Colors - Inspiring Amber & Golden Dawn
val Gold50 = Color(0xFFFFFBEB)
val Gold100 = Color(0xFFFEF3C7)
val Gold400 = Color(0xFFFBBF24)
val Gold500 = Color(0xFFF59E0B)
val Gold600 = Color(0xFFD97706)
val Gold700 = Color(0xFFB45309)

// Secondary - Deep Indigo / Celestial Night
val Indigo900 = Color(0xFF1E1B4B)
val Indigo800 = Color(0xFF312E81)
val Indigo600 = Color(0xFF4F46E5)
val Indigo400 = Color(0xFF818CF8)
val Indigo200 = Color(0xFFC7D2FE)

// Accent - Vibrant Coral / Rose
val Rose500 = Color(0xFFF43F5E)
val Rose600 = Color(0xFFE11D48)

// Neutral Palette
val Slate950 = Color(0xFF030712)
val Slate900 = Color(0xFF0F172A)
val Slate800 = Color(0xFF1E293B)
val Slate700 = Color(0xFF334155)
val Slate400 = Color(0xFF94A3B8)
val Slate200 = Color(0xFFE2E8F0)
val Slate100 = Color(0xFFF1F5F9)
val Slate50 = Color(0xFFF8FAFC)

// Card Gradients for Quotes
object QuoteGradients {
    val SunsetGold = Brush.linearGradient(
        colors = listOf(Color(0xFFF59E0B), Color(0xFFEA580C))
    )
    val MidnightIndigo = Brush.linearGradient(
        colors = listOf(Color(0xFF1E1B4B), Color(0xFF3B0764))
    )
    val OceanEmerald = Brush.linearGradient(
        colors = listOf(Color(0xFF0F766E), Color(0xFF065F46))
    )
    val RoyalPurple = Brush.linearGradient(
        colors = listOf(Color(0xFF4C1D95), Color(0xFF701A75))
    )
    val RadiantRose = Brush.linearGradient(
        colors = listOf(Color(0xFFBE123C), Color(0xFF831843))
    )
    val DeepCosmos = Brush.linearGradient(
        colors = listOf(Color(0xFF0F172A), Color(0xFF1E293B))
    )

    val gradientList = listOf(
        SunsetGold,
        MidnightIndigo,
        OceanEmerald,
        RoyalPurple,
        RadiantRose,
        DeepCosmos
    )

    val gradientNames = listOf(
        "Sunset Gold",
        "Midnight Celestial",
        "Emerald Forest",
        "Royal Violet",
        "Ruby Rose",
        "Deep Slate"
    )
}
