package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PaymentStatus
import com.example.model.TopUpStatus
import com.example.ui.theme.*

@Composable
fun TopUpStatusBadge(
    status: TopUpStatus,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, borderColor) = when (status) {
        TopUpStatus.COMPLETED -> Triple(Color(0x2610B981), StatusSuccess, StatusSuccess.copy(alpha = 0.5f))
        TopUpStatus.PROCESSING -> Triple(Color(0x2600D2FF), ElectricBlue, ElectricBlue.copy(alpha = 0.5f))
        TopUpStatus.PENDING -> Triple(Color(0x26FFBE18), GamingYellow, GamingYellow.copy(alpha = 0.5f))
        TopUpStatus.FAILED -> Triple(Color(0x26EF4444), StatusFailed, StatusFailed.copy(alpha = 0.5f))
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .border(0.8.dp, borderColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = status.name,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PaymentStatusBadge(
    status: PaymentStatus,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, borderColor) = when (status) {
        PaymentStatus.COMPLETED, PaymentStatus.VERIFIED -> Triple(Color(0x2610B981), StatusSuccess, StatusSuccess.copy(alpha = 0.5f))
        PaymentStatus.PENDING -> Triple(Color(0x26FFBE18), GamingYellow, GamingYellow.copy(alpha = 0.5f))
        PaymentStatus.FAILED -> Triple(Color(0x26EF4444), StatusFailed, StatusFailed.copy(alpha = 0.5f))
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .border(0.8.dp, borderColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = status.name,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
