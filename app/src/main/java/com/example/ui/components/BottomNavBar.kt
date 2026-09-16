package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.Screen

data class NavItem(
    val title: String,
    val screen: Screen,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun BottomNavBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem("Home", Screen.Home, Icons.Filled.Home, Icons.Outlined.Home),
        NavItem("Games", Screen.Games, Icons.Filled.SportsEsports, Icons.Outlined.SportsEsports),
        NavItem("Offers", Screen.Offers, Icons.Filled.LocalOffer, Icons.Outlined.LocalOffer),
        NavItem("Orders", Screen.Orders, Icons.Filled.ReceiptLong, Icons.Outlined.ReceiptLong),
        NavItem("Profile", Screen.Profile, Icons.Filled.Person, Icons.Outlined.Person)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xF0070B13),
                        Color(0xFF070B13)
                    )
                )
            )
            .border(
                width = 0.8.dp,
                color = Color(0x3300D2FF),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = when (item.screen) {
                    Screen.Home -> currentScreen is Screen.Home
                    Screen.Games -> currentScreen is Screen.Games || currentScreen is Screen.GameTopUp || currentScreen is Screen.Checkout
                    Screen.Offers -> currentScreen is Screen.Offers
                    Screen.Orders -> currentScreen is Screen.Orders || currentScreen is Screen.OrderSuccess || currentScreen is Screen.OrderTracking
                    Screen.Profile -> currentScreen is Screen.Profile || currentScreen is Screen.Wallet || currentScreen is Screen.AdminDashboard || currentScreen is Screen.DeveloperApiPanel
                    else -> false
                }

                val iconColor by animateColorAsState(
                    targetValue = if (isSelected) ElectricBlue else TextSubtle,
                    label = "iconColor"
                )
                val pillWidth by animateDpAsState(
                    targetValue = if (isSelected) 42.dp else 0.dp,
                    label = "pillWidth"
                )

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onNavigate(item.screen) }
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Active pill highlight on top
                    Box(
                        modifier = Modifier
                            .width(pillWidth)
                            .height(3.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) ElectricBlue else Color.Transparent)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = item.title,
                        color = if (isSelected) TextWhite else TextSubtle,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}
