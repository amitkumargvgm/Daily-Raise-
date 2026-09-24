package com.example.ui.components

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Quote
import com.example.model.QuoteCategory

@Composable
fun QuoteCard(
    quote: Quote,
    onFavoriteClick: () -> Unit,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit,
    onUnlockClick: () -> Unit,
    modifier: Modifier = Modifier,
    customBrush: Brush? = null
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val heartScale by animateFloatAsState(
        targetValue = if (quote.isFavorite) 1.15f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "heartScale"
    )

    val heartColor by animateColorAsState(
        targetValue = if (quote.isFavorite) Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "heartColor"
    )

    val categoryMeta = QuoteCategory.fromId(quote.category)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("quote_card_${quote.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (customBrush == null) MaterialTheme.colorScheme.surface else Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (customBrush != null) {
                        Modifier.background(customBrush)
                    } else {
                        Modifier.background(MaterialTheme.colorScheme.surface)
                    }
                )
                .border(
                    width = 1.dp,
                    color = if (customBrush != null) Color.White.copy(alpha = 0.15f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {
            val contentColor = if (customBrush != null) Color.White else MaterialTheme.colorScheme.onSurface
            val subtextColor = if (customBrush != null) Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.onSurfaceVariant

            Column(modifier = Modifier.fillMaxWidth()) {
                // Top row: Category badge and VIP tag
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = if (customBrush != null) Color.White.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "${categoryMeta.titleEn} • ${categoryMeta.titleHi}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (customBrush != null) Color.White else MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    if (quote.isVip && !quote.isUnlocked) {
                        Surface(
                            color = Color(0xFFF59E0B),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "VIP Quote",
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "VIP",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Quotation Mark Accent
                Text(
                    text = "“",
                    fontSize = 44.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = if (customBrush != null) Color.White.copy(alpha = 0.4f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                )

                if (quote.isVip && !quote.isUnlocked) {
                    // Blurred or Locked placeholder state
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (customBrush != null) Color.Black.copy(alpha = 0.3f)
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                            )
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked Quote",
                                tint = if (customBrush != null) Color.White else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Premium Motivational Quote",
                                fontWeight = FontWeight.Bold,
                                color = contentColor,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "Watch a short ad to unlock this exclusive quote",
                                color = subtextColor,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = onUnlockClick,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.testTag("unlock_quote_button_${quote.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayCircle,
                                    contentDescription = "Watch Ad",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Watch Ad to Unlock", color = Color.White, fontSize = 13.sp)
                            }
                        }
                    }
                } else {
                    // English Quote Text
                    Text(
                        text = quote.text,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 27.sp,
                        color = contentColor
                    )

                    // Hindi Translation
                    if (!quote.hindiText.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = quote.hindiText,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 22.sp,
                            color = subtextColor
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Author Name
                    Text(
                        text = "— ${quote.author}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (customBrush != null) Color.White else MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Actions Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Copy Button
                        IconButton(
                            onClick = onCopyClick,
                            modifier = Modifier
                                .size(44.dp)
                                .testTag("copy_quote_button_${quote.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy Quote",
                                tint = if (customBrush != null) Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        // Share Button
                        IconButton(
                            onClick = onShareClick,
                            modifier = Modifier
                                .size(44.dp)
                                .testTag("share_quote_button_${quote.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share Quote",
                                tint = if (customBrush != null) Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        // Favorite Button
                        IconButton(
                            onClick = onFavoriteClick,
                            modifier = Modifier
                                .size(44.dp)
                                .scale(heartScale)
                                .testTag("favorite_quote_button_${quote.id}")
                        ) {
                            Icon(
                                imageVector = if (quote.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (quote.isFavorite) "Favorited" else "Add to Favorites",
                                tint = if (quote.isFavorite) Color(0xFFEF4444) else if (customBrush != null) Color.White else heartColor,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
