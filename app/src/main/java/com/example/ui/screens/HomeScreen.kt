package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Game
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.Screen
import com.example.viewmodel.UiState

@Composable
fun HomeScreen(
    uiState: UiState,
    onNavigate: (Screen) -> Unit,
    onSelectGame: (Game) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Section
        item {
            HeroSection(
                onTopUpNow = {
                    val ff = uiState.games.firstOrNull { it.id == "free-fire" } ?: uiState.games.first()
                    onSelectGame(ff)
                },
                onExploreGames = { onNavigate(Screen.Games) }
            )
        }

        // Trust Bar
        item {
            TrustBar()
        }

        // Popular Games Section Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Popular Games",
                            color = TextWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Choose your game and start your top-up.",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }

                    TextButton(onClick = { onNavigate(Screen.Games) }) {
                        Text(
                            text = "View All",
                            color = ElectricBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = ElectricBlue,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }

        // Popular Games Grid (2 items per row)
        val games = uiState.games.filter { it.active }
        val chunkedGames = games.chunked(2)
        items(chunkedGames.size) { index ->
            val rowGames = chunkedGames[index]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowGames.forEach { game ->
                    GameCard(
                        game = game,
                        onTopUpClick = { onSelectGame(game) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowGames.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // Promotional Flash Offer Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF1E1435),
                                Color(0xFF2C1B4D),
                                Color(0xFF131124)
                            )
                        )
                    )
                    .border(1.dp, Color(0x668B5CF6), RoundedCornerShape(16.dp))
                    .clickable { onNavigate(Screen.Offers) }
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocalOffer,
                                contentDescription = null,
                                tint = RoyalGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "SPECIAL OFFER • 10% EXTRA",
                                color = RoyalGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Free Fire Mega Flash Deal",
                            color = TextWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Use voucher code WELCOMEFS for ৳50 off on first order.",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(ElectricBlue)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "CLAIM",
                            color = Color(0xFF070B13),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }

        // Why Choose FS FAHAD Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Why Choose FS FAHAD?",
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FeatureMiniCard(
                        icon = "⚡",
                        title = "1-5 Min Delivery",
                        desc = "Automated server fulfillment",
                        modifier = Modifier.weight(1f)
                    )
                    FeatureMiniCard(
                        icon = "🛡️",
                        title = "Zero Ban Risk",
                        desc = "100% official ID recharge",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Footer
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Footer(onNavigate = onNavigate)
        }
    }
}

@Composable
private fun FeatureMiniCard(icon: String, title: String, desc: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(0.8.dp, Color(0x1F00D2FF), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(text = icon, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = title, color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text(text = desc, color = TextMuted, fontSize = 10.sp)
    }
}
