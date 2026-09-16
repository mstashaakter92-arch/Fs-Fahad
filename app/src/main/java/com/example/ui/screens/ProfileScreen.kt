package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.UserRole
import com.example.ui.theme.*
import com.example.viewmodel.Screen
import com.example.viewmodel.UiState

data class ProfileMenuItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val action: () -> Unit,
    val badge: String? = null
)

@Composable
fun ProfileScreen(
    uiState: UiState,
    onNavigate: (Screen) -> Unit,
    onSwitchRole: (UserRole) -> Unit,
    onOpenAuth: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user = uiState.currentUser

    val menuItems = listOf(
        ProfileMenuItem("My Orders", "View order status & receipts", Icons.Default.ReceiptLong, { onNavigate(Screen.Orders) }),
        ProfileMenuItem("FS Wallet", "Balance: ৳${user.walletBalance.toInt()}", Icons.Default.AccountBalanceWallet, { onNavigate(Screen.Wallet) }),
        ProfileMenuItem("Promotions & Offers", "Active vouchers & cashbacks", Icons.Default.LocalOffer, { onNavigate(Screen.Offers) }, "HOT"),
        ProfileMenuItem("Support Center", "24/7 helpdesk & tickets", Icons.Default.SupportAgent, { onNavigate(Screen.Support) }),
        ProfileMenuItem("Admin Dashboard", "Overview & game controls", Icons.Default.Dashboard, { onNavigate(Screen.AdminDashboard) }, "STAFF"),
        ProfileMenuItem("Developer & API Panel", "Provider sync & logs", Icons.Default.Terminal, { onNavigate(Screen.DeveloperApiPanel) }, "DEV")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp)
    ) {
        // User Profile Hero Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF0F1B36),
                                Color(0xFF132247),
                                Color(0xFF0C1425)
                            )
                        )
                    )
                    .border(1.2.dp, Color(0x3300D2FF), RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(ElectricBlue, RoyalGold)))
                                .border(2.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.name.take(1),
                                color = Color(0xFF040A14),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = user.name,
                                    color = TextWhite,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (user.role == UserRole.USER) ElectricBlue.copy(alpha = 0.2f) else RoyalGold.copy(alpha = 0.2f))
                                        .border(0.8.dp, if (user.role == UserRole.USER) ElectricBlue else RoyalGold, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = user.role.name,
                                        color = if (user.role == UserRole.USER) ElectricBlue else RoyalGold,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }

                            Text(
                                text = user.email,
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                            Text(
                                text = user.phone,
                                color = TextSubtle,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Quick Wallet Preview inside profile
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceDark)
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Wallet Balance", color = TextSubtle, fontSize = 10.sp)
                            Text(text = "৳${user.walletBalance.toInt()} BDT", color = RoyalGold, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }

                        TextButton(onClick = { onNavigate(Screen.Wallet) }) {
                            Text(text = "Manage Wallet", color = ElectricBlue, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Role Switcher for live testing / demo review
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceDark)
                    .border(0.8.dp, Color(0x2600D2FF), RoundedCornerShape(14.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "DEMO ROLE SIMULATOR",
                    color = TextSubtle,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    UserRole.values().forEach { r ->
                        val isCurrent = user.role == r
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isCurrent) ElectricBlue else Color(0xFF162238))
                                .clickable { onSwitchRole(r) }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = r.name,
                                color = if (isCurrent) Color(0xFF040A14) else TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Section: Menu Items
        item {
            Text(
                text = "ACCOUNT & PLATFORM",
                color = TextSubtle,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(menuItems) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceDark)
                    .border(0.8.dp, Color(0x1F00D2FF), RoundedCornerShape(12.dp))
                    .clickable { item.action() }
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF131C31)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = ElectricBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = item.title,
                            color = TextWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = item.subtitle,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (item.badge != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (item.badge == "STAFF" || item.badge == "DEV") RoyalGold else ElectricBlue)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = item.badge,
                                color = Color.Black,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = TextSubtle,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Logout / Switch Account
        item {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = onOpenAuth,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusFailed),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.linearGradient(listOf(Color(0x66EF4444), Color(0x33EF4444)))
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout",
                    tint = StatusFailed,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SWITCH ACCOUNT / LOGOUT",
                    color = StatusFailed,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
