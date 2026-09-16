package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import com.example.model.Order
import com.example.model.PaymentStatus
import com.example.model.TopUpStatus
import com.example.ui.theme.*

@Composable
fun OrderSuccessScreen(
    order: Order,
    onTrackOrder: (String) -> Unit,
    onBackHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackHome,
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceDark)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = ElectricBlue
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = "Order Success",
                color = TextWhite,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Large Glowing Green Checkmark Circle
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(StatusSuccess.copy(alpha = 0.35f), Color.Transparent)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(2.5.dp, StatusSuccess, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Success",
                    tint = StatusSuccess,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ORDER CREATED!",
            color = StatusSuccess,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Your order has been successfully placed.\nWe are processing your top-up.",
            color = TextMuted,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Receipt Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceDark)
                .border(1.dp, Color(0x2B00D2FF), RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ReceiptRow(label = "Order ID", value = order.id)
            ReceiptRow(label = "Game", value = order.gameId.replace("-", " ").uppercase())
            ReceiptRow(label = "Player ID", value = order.uid)
            ReceiptRow(label = "Package", value = "310 Diamonds (+30)")
            ReceiptRow(label = "Amount", value = "৳ ${order.amount.toInt()}")

            // Payment Status with Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Payment Status", color = TextMuted, fontSize = 12.sp)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x33FFBE18))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Pending",
                        color = GamingYellow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Top-Up Status with Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Top-Up Status", color = TextMuted, fontSize = 12.sp)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x3300D2FF))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Processing",
                        color = ElectricBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Buttons
        Button(
            onClick = { onTrackOrder(order.id) },
            colors = ButtonDefaults.buttonColors(
                containerColor = GamingYellow,
                contentColor = Color(0xFF070B13)
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Track Order",
                fontSize = 14.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onBackHome,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = Brush.linearGradient(listOf(Color(0x3300D2FF), Color(0x3300D2FF)))
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Back to Home",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = TextMuted, fontSize = 12.sp)
        Text(text = value, color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}
