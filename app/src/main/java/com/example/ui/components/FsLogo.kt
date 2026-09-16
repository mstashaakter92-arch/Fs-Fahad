package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun FsLogo(
    modifier: Modifier = Modifier,
    compact: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Monogram Badge
        Box(
            modifier = Modifier
                .size(if (compact) 36.dp else 42.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF0F172A),
                            Color(0xFF0A1E3F),
                            Color(0xFF06101E)
                        )
                    )
                )
                .border(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(ElectricBlue, RoyalGold, ElectricBlue)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "F",
                    color = ElectricBlue,
                    fontSize = if (compact) 16.sp else 18.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = "S",
                    color = RoyalGold,
                    fontSize = if (compact) 16.sp else 18.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Brand Name and Subtitle
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "FS ",
                    color = ElectricBlue,
                    fontSize = if (compact) 16.sp else 19.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "FAHAD",
                    color = TextWhite,
                    fontSize = if (compact) 16.sp else 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
            Text(
                text = "GAME TOP-UP",
                color = RoyalGold,
                fontSize = if (compact) 9.sp else 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
        }
    }
}

/**
 * Large Esports Crest Emblem matching Screen 1 & Screen 14 of the reference mockup
 */
@Composable
fun FsBrandEmblem(
    modifier: Modifier = Modifier,
    sizeDp: Int = 200
) {
    Box(
        modifier = modifier
            .size(sizeDp.dp),
        contentAlignment = Alignment.Center
    ) {
        // Glowing circular neon ring
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            ElectricBlue.copy(alpha = 0.25f),
                            Color(0x1A00D2FF),
                            Color.Transparent
                        )
                    )
                )
                .border(
                    width = 2.dp,
                    brush = Brush.sweepGradient(
                        colors = listOf(ElectricBlue, RoyalGold, ElectricBlue, Color(0x3300D2FF), ElectricBlue)
                    ),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            // Golden Crown
            Text(text = "👑", fontSize = (sizeDp * 0.16).sp)

            Spacer(modifier = Modifier.height(2.dp))

            // Large FS Monogram with metallic styling
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "F",
                    color = ElectricBlue,
                    fontSize = (sizeDp * 0.24).sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-2).sp
                )
                Text(
                    text = "S",
                    color = RoyalGold,
                    fontSize = (sizeDp * 0.24).sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-2).sp
                )
            }

            // Ribbon: FS FAHAD
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF0F1B30))
                    .border(1.dp, ElectricBlue, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "FS FAHAD",
                    color = TextWhite,
                    fontSize = (sizeDp * 0.07).sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "GAME TOP-UP",
                color = RoyalGold,
                fontSize = (sizeDp * 0.055).sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "FAST • SAFE • EASY",
                color = ElectricBlue,
                fontSize = (sizeDp * 0.045).sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(text = "🎮", fontSize = (sizeDp * 0.08).sp)
        }
    }
}
