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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Order
import com.example.model.TopUpStatus
import com.example.ui.components.TopUpStatusBadge
import com.example.ui.theme.*
import com.example.viewmodel.UiState

@Composable
fun OrdersScreen(
    uiState: UiState,
    onViewOrder: (String) -> Unit,
    onFilterChange: (String) -> Unit,
    onSearchChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filters = listOf("All", "Pending", "Processing", "Completed", "Failed")

    val filteredOrders = uiState.orders.filter { ord ->
        val matchesFilter = when (uiState.orderStatusFilter) {
            "All" -> true
            "Pending" -> ord.topupStatus == TopUpStatus.PENDING
            "Processing" -> ord.topupStatus == TopUpStatus.PROCESSING
            "Completed" -> ord.topupStatus == TopUpStatus.COMPLETED
            "Failed" -> ord.topupStatus == TopUpStatus.FAILED
            else -> true
        }

        val matchesSearch = if (uiState.orderSearchQuery.isBlank()) true
        else ord.id.contains(uiState.orderSearchQuery, ignoreCase = true) ||
             ord.gameName.contains(uiState.orderSearchQuery, ignoreCase = true) ||
             ord.uid.contains(uiState.orderSearchQuery, ignoreCase = true)

        matchesFilter && matchesSearch
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
                text = "My Top-Up Orders",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Track your recent game currency purchases and live fulfillment",
                color = TextMuted,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Search Bar by Order ID or Game
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
                    value = uiState.orderSearchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Search by Order ID (e.g. FS-92810)...", color = TextSubtle, fontSize = 12.sp) },
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

                if (uiState.orderSearchQuery.isNotEmpty()) {
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

        // Filter chips: All, Pending, Processing, Completed, Failed
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filters) { filter ->
                    val isSelected = uiState.orderStatusFilter == filter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) ElectricBlue else SurfaceDark)
                            .border(
                                width = 0.8.dp,
                                color = if (isSelected) ElectricBlue else Color(0x2B00D2FF),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { onFilterChange(filter) }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) Color(0xFF040A14) else TextWhite,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Orders list
        items(filteredOrders) { order ->
            OrderCardItem(order = order, onViewClick = { onViewOrder(order.id) })
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (filteredOrders.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "📦", fontSize = 40.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No orders found",
                            color = TextWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Try clearing search or place a new game top-up!",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderCardItem(
    order: Order,
    onViewClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceDark)
            .border(1.dp, Color(0x2600D2FF), RoundedCornerShape(14.dp))
            .clickable { onViewClick() }
            .padding(14.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Top row: Game icon + Name + Order ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF0F172A))
                            .border(0.8.dp, ElectricBlueGlow, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = order.gameEmoji, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = order.gameName,
                            color = TextWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "#${order.id} • ${order.createdAt}",
                            color = TextSubtle,
                            fontSize = 11.sp
                        )
                    }
                }

                TopUpStatusBadge(status = order.topupStatus)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Package & UID details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Package: ${order.packageName}", color = TextMuted, fontSize = 11.sp)
                    Text(text = "Player UID: ${order.uid}", color = TextMuted, fontSize = 11.sp)
                }

                Text(
                    text = "৳${order.total.toInt()}",
                    color = RoyalGold,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Bottom action row: Payment tag + View Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Paid via ${order.paymentMethod} • ${order.paymentStatus.name}",
                    color = TextSubtle,
                    fontSize = 10.sp
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x1F00D2FF))
                        .clickable { onViewClick() }
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "View",
                        tint = ElectricBlue,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "VIEW TIMELINE",
                        color = ElectricBlue,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
