package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun HeroSection(
    onTopUpNow: () -> Unit,
    onExploreGames: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Subtle breathing pulse for neon lighting
    val infiniteTransition = rememberInfiniteTransition(label = "heroGlow")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0C1425),
                        Color(0xFF0F1A33),
                        Color(0xFF080D1A)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
            .border(
                width = 1.2.dp,
                brush = Brush.linearGradient(
                    listOf(
                        ElectricBlue.copy(alpha = pulseAlpha),
                        RoyalGold.copy(alpha = 0.4f),
                        ElectricBlue.copy(alpha = 0.2f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {
        // Decorative Canvas: abstract gaming energy, controller wireframe, digital particles
        Canvas(
            modifier = Modifier
                .matchParentSize()
        ) {
            val w = size.width
            val h = size.height

            // Glowing radial aura
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(ElectricBlue.copy(alpha = 0.18f), Color.Transparent),
                    center = Offset(w * 0.85f, h * 0.3f),
                    radius = w * 0.45f
                ),
                radius = w * 0.45f,
                center = Offset(w * 0.85f, h * 0.3f)
            )

            // Abstract controller geometric silhouette in the background
            val controllerCenter = Offset(w * 0.82f, h * 0.65f)
            val cWidth = 90f
            val cHeight = 55f

            val controllerPath = Path().apply {
                // Controller body
                moveTo(controllerCenter.x - cWidth / 2, controllerCenter.y)
                cubicTo(
                    controllerCenter.x - cWidth / 2, controllerCenter.y - cHeight / 2,
                    controllerCenter.x + cWidth / 2, controllerCenter.y - cHeight / 2,
                    controllerCenter.x + cWidth / 2, controllerCenter.y
                )
                cubicTo(
                    controllerCenter.x + cWidth * 0.6f, controllerCenter.y + cHeight * 0.6f,
                    controllerCenter.x + cWidth * 0.2f, controllerCenter.y + cHeight * 0.5f,
                    controllerCenter.x, controllerCenter.y + cHeight * 0.2f
                )
                cubicTo(
                    controllerCenter.x - cWidth * 0.2f, controllerCenter.y + cHeight * 0.5f,
                    controllerCenter.x - cWidth * 0.6f, controllerCenter.y + cHeight * 0.6f,
                    controllerCenter.x - cWidth / 2, controllerCenter.y
                )
                close()
            }

            drawPath(
                path = controllerPath,
                color = ElectricBlue.copy(alpha = 0.12f),
                style = Stroke(width = 2.5f)
            )

            // Digital particle dots
            val particleOffsets = listOf(
                Offset(w * 0.75f, h * 0.2f),
                Offset(w * 0.90f, h * 0.25f),
                Offset(w * 0.82f, h * 0.45f),
                Offset(w * 0.70f, h * 0.75f),
                Offset(w * 0.92f, h * 0.85f),
                Offset(w * 0.60f, h * 0.15f)
            )

            particleOffsets.forEachIndexed { index, pt ->
                val dotColor = if (index % 2 == 0) ElectricBlue else RoyalGold
                drawCircle(
                    color = dotColor.copy(alpha = 0.4f * pulseAlpha + 0.2f),
                    radius = if (index % 2 == 0) 3.5f else 2.5f,
                    center = pt
                )
            }
        }

        // Hero Foreground Content
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Live Tagline Pill
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0x2B00D2FF))
                    .border(0.8.dp, Color(0x6600D2FF), RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(StatusSuccess)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "FAST • SAFE • EASY  |  BANGLADESH #1",
                    color = ElectricBlue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Headline
            Text(
                text = "Power Up\nYour Game",
                color = TextWhite,
                fontSize = 30.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Secondary text
            Text(
                text = "Fast and secure game top-up, designed for gamers.\nOfficial diamond, UC & point vouchers via bKash & Nagad.",
                color = TextMuted,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onTopUpNow,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricBlue,
                        contentColor = Color(0xFF040A14)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "TOP UP NOW",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }

                OutlinedButton(
                    onClick = onExploreGames,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextWhite
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(listOf(RoyalGold, Color(0x66FFB800)))
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.SportsEsports,
                        contentDescription = null,
                        tint = RoyalGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "EXPLORE GAMES",
                        color = RoyalGoldLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
