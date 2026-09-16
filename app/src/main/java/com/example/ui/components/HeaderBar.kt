package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.ui.theme.*

@Composable
fun HeaderBar(
    walletBalance: Double,
    onLogoClick: () -> Unit,
    onWalletClick: () -> Unit,
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFA070B13),
                        Color(0xEE0D1424)
                    )
                )
            )
            .border(
                width = 0.5.dp,
                color = Color(0x2600D2FF),
                shape = RoundedCornerShape(0.dp)
            )
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Brand Logo
            FsLogo(
                compact = true,
                onClick = onLogoClick
            )

            // Right Action Controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Notification Bell
                Box {
                    IconButton(
                        onClick = onNotificationClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SurfaceDark)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = RoyalGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    // Dot indicator
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .align(Alignment.TopEnd)
                            .clip(CircleShape)
                            .background(StatusSuccess)
                    )
                }

                // Wallet Balance Pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(SurfaceDark)
                        .border(0.8.dp, ElectricBlueGlow, RoundedCornerShape(20.dp))
                        .clickable { onWalletClick() }
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(ElectricBlue, RoyalGold))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = null,
                            tint = Color(0xFF040A14),
                            modifier = Modifier.size(11.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "৳${walletBalance.toInt()}",
                        color = RoyalGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Profile Avatar Button
                IconButton(
                    onClick = onProfileClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF131F36))
                        .border(1.dp, ElectricBlue, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = TextWhite,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
