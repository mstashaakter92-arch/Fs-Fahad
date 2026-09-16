package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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

data class TrustItem(
    val icon: String,
    val title: String,
    val subtitle: String
)

@Composable
fun TrustBar(modifier: Modifier = Modifier) {
    val items = listOf(
        TrustItem("⚡", "Fast Processing", "Instant auto-delivery in 1-5 mins"),
        TrustItem("🔐", "Secure Checkout", "100% Safe & Verified"),
        TrustItem("💳", "Easy Payment", "bKash, Nagad, Rocket, Cards"),
        TrustItem("🎧", "24/7 Support", "Bangla & English Live Support")
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.take(2).forEach { item ->
                TrustCard(item = item, modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.drop(2).forEach { item ->
                TrustCard(item = item, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun TrustCard(item: TrustItem, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF0D1424)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = Color(0x1F00D2FF),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF131C31)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = item.icon, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = item.title,
                color = TextWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = item.subtitle,
                color = TextMuted,
                fontSize = 10.sp,
                maxLines = 1
            )
        }
    }
}
