package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Game
import com.example.model.Order
import com.example.model.TopUpStatus
import com.example.ui.components.TopUpStatusBadge
import com.example.ui.theme.*
import com.example.viewmodel.Screen
import com.example.viewmodel.UiState

@Composable
fun AdminDashboardScreen(
    uiState: UiState,
    onNavigate: (Screen) -> Unit,
    onToggleGame: (String) -> Unit,
    onUpdateOrderStatus: (String, TopUpStatus) -> Unit,
    onToggleBanUser: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeSubTab by remember { mutableStateOf("Overview") }
    val subTabs = listOf("Overview", "Orders", "Games", "Packages", "Users")

    val totalOrders = uiState.orders.size
    val completedOrders = uiState.orders.count { it.topupStatus == TopUpStatus.COMPLETED }
    val pendingOrders = uiState.orders.count { it.topupStatus == TopUpStatus.PENDING || it.topupStatus == TopUpStatus.PROCESSING }
    val totalRevenue = uiState.orders.sumOf { it.total }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Admin Portal",
                            color = TextWhite,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(RoyalGold)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "MASTER CONTROL",
                                color = Color.Black,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                    Text(
                        text = "Real-time gaming store metrics and fulfillment controls",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }

                Button(
                    onClick = { onNavigate(Screen.DeveloperApiPanel) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF13233E),
                        contentColor = ElectricBlue
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Terminal,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "API Panel", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // Sub-Tabs Selector
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceDark)
                    .padding(3.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                subTabs.forEach { tab ->
                    val isSelected = activeSubTab == tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) ElectricBlue else Color.Transparent)
                            .clickable { activeSubTab = tab }
                            .padding(vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab,
                            color = if (isSelected) Color(0xFF040A14) else TextMuted,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Conditional Tab Rendering
        when (activeSubTab) {
            "Overview" -> {
                // Stat Cards Grid
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AdminStatCard(
                                title = "TOTAL REVENUE",
                                value = "৳${totalRevenue.toInt()}",
                                subtitle = "+18% from last week",
                                valueColor = RoyalGold,
                                modifier = Modifier.weight(1f)
                            )
                            AdminStatCard(
                                title = "TOTAL ORDERS",
                                value = "$totalOrders",
                                subtitle = "Avg ৳280/order",
                                valueColor = TextWhite,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AdminStatCard(
                                title = "COMPLETED",
                                value = "$completedOrders",
                                subtitle = "100% delivered",
                                valueColor = StatusSuccess,
                                modifier = Modifier.weight(1f)
                            )
                            AdminStatCard(
                                title = "PENDING / QUEUED",
                                value = "$pendingOrders",
                                subtitle = "Automated processing",
                                valueColor = ElectricBlue,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }

                // Chart 1: Orders over Time (Canvas Bar Chart)
                item {
                    AdminChartContainer(title = "Orders Over Time (Weekly)") {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height
                            val barData = listOf(14f, 22f, 18f, 35f, 28f, 44f, 38f)
                            val labels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                            val maxVal = 50f
                            val barWidth = (w / barData.size) * 0.45f
                            val stepX = w / barData.size

                            barData.forEachIndexed { i, value ->
                                val barHeight = (value / maxVal) * (h * 0.75f)
                                val x = i * stepX + (stepX - barWidth) / 2
                                val y = h - barHeight - 15f

                                drawRoundRect(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(ElectricBlue, Color(0xFF0369A1))
                                    ),
                                    topLeft = Offset(x, y),
                                    size = Size(barWidth, barHeight),
                                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // Chart 2: Revenue Overview (Canvas Smooth Gradient Curve)
                item {
                    AdminChartContainer(title = "Revenue Overview Trend (৳ BDT)") {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height
                            val points = listOf(
                                Offset(0f, h * 0.7f),
                                Offset(w * 0.2f, h * 0.55f),
                                Offset(w * 0.4f, h * 0.62f),
                                Offset(w * 0.6f, h * 0.35f),
                                Offset(w * 0.8f, h * 0.42f),
                                Offset(w, h * 0.2f)
                            )

                            val fillPath = Path().apply {
                                moveTo(0f, h)
                                points.forEach { lineTo(it.x, it.y) }
                                lineTo(w, h)
                                close()
                            }

                            drawPath(
                                path = fillPath,
                                brush = Brush.verticalGradient(
                                    colors = listOf(RoyalGold.copy(alpha = 0.25f), Color.Transparent)
                                )
                            )

                            val strokePath = Path().apply {
                                moveTo(points.first().x, points.first().y)
                                for (i in 1 until points.size) {
                                    lineTo(points[i].x, points[i].y)
                                }
                            }

                            drawPath(
                                path = strokePath,
                                color = RoyalGold,
                                style = Stroke(width = 3.5f)
                            )

                            points.forEach { pt ->
                                drawCircle(color = RoyalGold, radius = 4.5f, center = pt)
                                drawCircle(color = Color(0xFF040A14), radius = 2f, center = pt)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // Chart 3: Game Sales Distribution
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SurfaceDark)
                            .border(1.dp, Color(0x2400D2FF), RoundedCornerShape(14.dp))
                            .padding(14.dp)
                    ) {
                        Text(
                            text = "Game Sales Distribution",
                            color = TextWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        DistributionBar("Free Fire Diamonds", 0.46f, "46%", BkashPink)
                        DistributionBar("PUBG Mobile UC", 0.28f, "28%", ElectricBlue)
                        DistributionBar("Mobile Legends Diamonds", 0.14f, "14%", RoyalGold)
                        DistributionBar("Roblox Robux & Other", 0.12f, "12%", NeonCyan)
                    }
                }
            }

            "Orders" -> {
                item {
                    Text(
                        text = "Manage Customer Orders",
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tap to transition order status in real time",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(uiState.orders) { order ->
                    AdminOrderManagerCard(
                        order = order,
                        onStatusChange = { newStatus -> onUpdateOrderStatus(order.id, newStatus) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            "Games" -> {
                item {
                    Text(
                        text = "Game Catalog & Visibility",
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Toggle active state to show/hide game on store front",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(uiState.games) { game ->
                    AdminGameRow(
                        game = game,
                        onToggle = { onToggleGame(game.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            "Packages" -> {
                item {
                    Text(
                        text = "Active Top-Up Packages",
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Configurable package tiers across all supported game titles",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(uiState.packages) { pkg ->
                    AdminPackageRow(pkg = pkg)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            "Users" -> {
                item {
                    Text(
                        text = "Platform Registered Users",
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "User moderation, balance review, and access control",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(uiState.users) { u ->
                    AdminUserRow(
                        user = u,
                        onToggleBan = { onToggleBanUser(u.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun AdminStatCard(
    title: String,
    value: String,
    subtitle: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(1.dp, Color(0x2600D2FF), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(text = title, color = TextSubtle, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, color = valueColor, fontSize = 20.sp, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = subtitle, color = TextMuted, fontSize = 10.sp)
    }
}

@Composable
private fun AdminChartContainer(title: String, content: @Composable BoxScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceDark)
            .border(1.dp, Color(0x2600D2FF), RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Text(text = title, color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            content = content
        )
    }
}

@Composable
private fun DistributionBar(title: String, ratio: Float, pctLabel: String, barColor: Color) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, color = TextWhite, fontSize = 11.sp)
            Text(text = pctLabel, color = barColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF0F172A))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(ratio)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(barColor)
            )
        }
    }
}

@Composable
private fun AdminOrderManagerCard(order: Order, onStatusChange: (TopUpStatus) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(0.8.dp, Color(0x2000D2FF), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "#${order.id} • ${order.gameName}", color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                TopUpStatusBadge(status = order.topupStatus)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "UID: ${order.uid} | ${order.packageName} | ৳${order.total.toInt()}", color = TextMuted, fontSize = 11.sp)

            Spacer(modifier = Modifier.height(8.dp))

            // Quick Status Changer Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                TopUpStatus.values().forEach { st ->
                    val isCurrent = order.topupStatus == st
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isCurrent) ElectricBlue else Color(0xFF131D33))
                            .clickable { onStatusChange(st) }
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = st.name.take(4),
                            color = if (isCurrent) Color(0xFF040A14) else TextMuted,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminGameRow(game: Game, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(0.8.dp, Color(0x1F00D2FF), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = game.iconEmoji, fontSize = 22.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = game.name, color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(text = game.category, color = TextMuted, fontSize = 11.sp)
            }
        }

        Switch(
            checked = game.active,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = ElectricBlue,
                checkedTrackColor = Color(0xFF0369A1)
            )
        )
    }
}

@Composable
private fun AdminPackageRow(pkg: com.example.model.GamePackage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(0.8.dp, Color(0x1F00D2FF), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "${pkg.name} (${pkg.amount})", color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Text(text = "Game: ${pkg.gameId} • Bonus: ${pkg.bonus.ifBlank { "None" }}", color = TextMuted, fontSize = 11.sp)
        }

        Text(text = "৳${pkg.price.toInt()}", color = RoyalGold, fontSize = 15.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
private fun AdminUserRow(user: com.example.model.User, onToggleBan: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(0.8.dp, Color(0x1F00D2FF), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = user.name, color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                if (user.isBanned) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(StatusFailed)
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(text = "BANNED", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Text(text = "${user.email} • Bal: ৳${user.walletBalance.toInt()}", color = TextMuted, fontSize = 11.sp)
        }

        Button(
            onClick = onToggleBan,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (user.isBanned) StatusSuccess else Color(0xFF3B1D1D)
            ),
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = if (user.isBanned) "UNBAN" else "BAN",
                color = if (user.isBanned) Color.Black else StatusFailed,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
