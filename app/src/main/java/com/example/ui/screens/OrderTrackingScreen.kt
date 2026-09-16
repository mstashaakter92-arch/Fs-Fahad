package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Order
import com.example.model.TopUpStatus
import com.example.ui.components.TopUpStatusBadge
import com.example.ui.theme.*

@Composable
fun OrderTrackingScreen(
    order: Order?,
    onBack: () -> Unit,
    onAdvanceSimulation: (String) -> Unit,
    onContactSupport: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (order == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(BgDark),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Order not found", color = TextMuted)
        }
        return
    }

    val steps = listOf(
        1 to "Order Created",
        2 to "Payment Verified",
        3 to "Processing",
        4 to "Top-Up Completed"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Header
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceDark)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = ElectricBlue
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Order Tracking",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Tracking ID: #${order.id}",
                        color = ElectricBlue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
        }

        // Live Status Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF0F1B36),
                                Color(0xFF132247)
                            )
                        )
                    )
                    .border(1.dp, Color(0x3300D2FF), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "CURRENT STATUS",
                            color = TextSubtle,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = order.topupStatus.name,
                            color = if (order.topupStatus == TopUpStatus.COMPLETED) StatusSuccess else ElectricBlue,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = if (order.topupStatus == TopUpStatus.COMPLETED)
                                "Diamonds/UC credited directly to Player ID"
                            else "Estimated fulfillment: 1 to 3 minutes",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }

                    TopUpStatusBadge(status = order.topupStatus)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Visual Vertical Timeline
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceDark)
                    .border(1.dp, Color(0x2B00D2FF), RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Text(
                    text = "FULFILLMENT TIMELINE",
                    color = ElectricBlue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                steps.forEachIndexed { index, (stepNum, title) ->
                    val isCompleted = order.timelineStep >= stepNum
                    val isCurrent = order.timelineStep == stepNum

                    TimelineStepRow(
                        stepNumber = stepNum,
                        title = title,
                        subtitle = when (stepNum) {
                            1 -> "Order request initialized in system"
                            2 -> "Payment verified via ${order.paymentMethod}"
                            3 -> "Connecting to game distributor API"
                            4 -> "Voucher successfully redeemed to UID"
                            else -> ""
                        },
                        isCompleted = isCompleted,
                        isCurrent = isCurrent,
                        isLast = index == steps.size - 1
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Action: Advance Simulation (Demo testing tool)
        item {
            Button(
                onClick = { onAdvanceSimulation(order.id) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF132247),
                    contentColor = ElectricBlue
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (order.timelineStep >= 4) "Status: Fully Completed" else "Simulate Next Timeline Step",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Need Help with this order?
        item {
            OutlinedButton(
                onClick = onContactSupport,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.linearGradient(listOf(Color(0x4000D2FF), Color(0x2000D2FF)))
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.SupportAgent,
                    contentDescription = null,
                    tint = RoyalGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Need Help with Order #${order.id}?",
                    color = TextWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun TimelineStepRow(
    stepNumber: Int,
    title: String,
    subtitle: String,
    isCompleted: Boolean,
    isCurrent: Boolean,
    isLast: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Timeline indicator column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(
                        if (isCompleted) StatusSuccess
                        else if (isCurrent) ElectricBlue
                        else SurfaceVariantDark
                    )
                    .border(
                        1.dp,
                        if (isCompleted) StatusSuccess else Color(0x3300D2FF),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Text(
                        text = "$stepNumber",
                        color = if (isCurrent) Color(0xFF040A14) else TextSubtle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(34.dp)
                        .background(if (isCompleted) StatusSuccess else Color(0x2600D2FF))
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Step text
        Column(modifier = Modifier.padding(bottom = if (isLast) 0.dp else 16.dp)) {
            Text(
                text = title,
                color = if (isCompleted || isCurrent) TextWhite else TextSubtle,
                fontSize = 14.sp,
                fontWeight = if (isCurrent) FontWeight.Black else FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = TextMuted,
                fontSize = 11.sp
            )
        }
    }
}
