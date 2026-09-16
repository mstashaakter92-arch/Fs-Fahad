package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DiamondVector
import com.example.ui.theme.*
import com.example.viewmodel.UiState

data class PaymentOption(
    val id: String,
    val name: String,
    val banglaName: String,
    val brandColor: Color,
    val iconEmoji: String
)

@Composable
fun CheckoutScreen(
    uiState: UiState,
    onBack: () -> Unit,
    onSelectPayment: (String) -> Unit,
    onApplyPromo: (String) -> Unit,
    onConfirmDemoPayment: () -> Unit,
    modifier: Modifier = Modifier
) {
    val game = uiState.selectedGame
    val pkg = uiState.selectedPackage
    val subtotal = pkg?.price ?: 250.0
    val discount = uiState.appliedDiscount
    val total = (subtotal - discount).coerceAtLeast(0.0)

    var promoInput by remember { mutableStateOf(uiState.promoCodeInput) }

    val paymentMethods = listOf(
        PaymentOption("bKash", "bKash", "বিকাশ", BkashPink, "🕊️"),
        PaymentOption("Nagad", "Nagad", "নগদ", NagadOrange, "🔥"),
        PaymentOption("Rocket", "Rocket", "রকেট", RocketPurple, "🚀"),
        PaymentOption("Card", "Card", "কার্ড", CardNavy, "💳")
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
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
                    text = "Checkout",
                    color = TextWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                // Game Summary Card
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SurfaceDark)
                            .border(0.8.dp, Color(0x2B00D2FF), RoundedCornerShape(14.dp))
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF0C1628)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = game?.iconEmoji ?: "🎮", fontSize = 28.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = game?.name ?: "Free Fire",
                                color = TextWhite,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Player ID: ${if (uiState.playerUid.isNotBlank()) uiState.playerUid else "1234567890"}",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                            if (game?.hasServerId == true && uiState.serverId.isNotBlank()) {
                                Text(
                                    text = "Server ID: ${uiState.serverId}",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                }

                // Package Card in Checkout
                item {
                    Text(
                        text = "Package",
                        color = TextSubtle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SurfaceDark)
                            .border(1.dp, Color(0x3300D2FF), RoundedCornerShape(14.dp))
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0x2000D2FF)),
                                contentAlignment = Alignment.Center
                            ) {
                                DiamondVector(modifier = Modifier.size(28.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = pkg?.amount ?: "310 Diamonds",
                                    color = TextWhite,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "+ ${pkg?.bonus ?: "30 Bonus"}",
                                    color = GamingYellow,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Text(
                            text = "৳ ${subtotal.toInt()}",
                            color = TextWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Price Breakdown Card
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SurfaceDark)
                            .border(0.8.dp, Color(0x2400D2FF), RoundedCornerShape(14.dp))
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Subtotal", color = TextMuted, fontSize = 12.sp)
                            Text(text = "৳ ${subtotal.toInt()}", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Discount", color = StatusSuccess, fontSize = 12.sp)
                            Text(text = "- ৳ ${discount.toInt()}", color = StatusSuccess, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        HorizontalDivider(
                            color = Color(0x1F00D2FF),
                            modifier = Modifier.padding(vertical = 10.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Total", color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = "৳ ${total.toInt()}",
                                color = GamingYellow,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Payment Method Selector
                item {
                    Text(
                        text = "Payment Method",
                        color = TextSubtle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        paymentMethods.forEach { method ->
                            val isSelected = uiState.selectedPaymentMethod == method.id

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceDark)
                                    .border(
                                        width = if (isSelected) 1.2.dp else 0.8.dp,
                                        color = if (isSelected) GamingYellow else Color(0x1A00D2FF),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { onSelectPayment(method.id) }
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Brand Icon Pill
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(method.brandColor),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = method.iconEmoji, fontSize = 18.sp)
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column {
                                        Text(
                                            text = method.name,
                                            color = TextWhite,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = method.banglaName,
                                            color = TextSubtle,
                                            fontSize = 10.sp
                                        )
                                    }
                                }

                                if (isSelected) {
                                    // Yellow radio indicator
                                    Box(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clip(CircleShape)
                                            .border(2.dp, GamingYellow, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(9.dp)
                                                .clip(CircleShape)
                                                .background(GamingYellow)
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                        contentDescription = null,
                                        tint = TextSubtle,
                                        modifier = Modifier.size(13.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Safe checkout disclaimer
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = StatusSuccess,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "100% Encrypted & Safe • Powered by FS FAHAD Direct Gateway",
                            color = TextSubtle,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }

        // Sticky Bottom "Pay Now (Demo)" Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(BgDark.copy(alpha = 0.95f))
                .padding(16.dp)
        ) {
            Button(
                onClick = onConfirmDemoPayment,
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
                    text = "Pay Now (Demo)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
