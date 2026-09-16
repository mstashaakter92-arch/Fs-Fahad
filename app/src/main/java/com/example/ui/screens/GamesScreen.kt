package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Game
import com.example.ui.components.GameCard
import com.example.ui.theme.*
import com.example.viewmodel.UiState

@Composable
fun GamesScreen(
    uiState: UiState,
    onSelectGame: (Game) -> Unit,
    onSearchChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Battle Royale", "MOBA", "Sports", "FPS / Action", "Strategy", "Sandbox")

    val filteredGames = uiState.games.filter { game ->
        val matchesCategory = if (uiState.selectedCategory == "All") true
        else game.category.contains(uiState.selectedCategory, ignoreCase = true)

        val matchesSearch = if (uiState.searchQuery.isBlank()) true
        else game.name.contains(uiState.searchQuery, ignoreCase = true) ||
             game.subtitle.contains(uiState.searchQuery, ignoreCase = true)

        matchesCategory && matchesSearch && game.active
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp)
    ) {
        // Title
        item {
            Text(
                text = "All Games Catalog",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Instant digital top-up for 10+ popular titles in Bangladesh",
                color = TextMuted,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Search Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceDark)
                    .border(1.dp, Color(0x3300D2FF), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = ElectricBlue,
                    modifier = Modifier.size(20.dp)
                )

                TextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Search Free Fire, PUBG, Robux, etc...", color = TextSubtle, fontSize = 13.sp) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    modifier = Modifier.weight(1f)
                )

                if (uiState.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Category Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { category ->
                    val isSelected = uiState.selectedCategory == category
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) ElectricBlue else SurfaceDark)
                            .border(
                                width = 0.8.dp,
                                color = if (isSelected) ElectricBlue else Color(0x2B00D2FF),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { onCategoryChange(category) }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) Color(0xFF040A14) else TextWhite,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Games Count Info
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredGames.size} Games Available",
                    color = TextSubtle,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Automated Instant Delivery",
                    color = StatusSuccess,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Games Grid (2 items per row)
        val chunked = filteredGames.chunked(2)
        items(chunked.size) { index ->
            val rowGames = chunked[index]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
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

        if (filteredGames.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🎮", fontSize = 40.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No games matched '${uiState.searchQuery}'",
                            color = TextWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Try searching for Free Fire, PUBG, or MLBB",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
