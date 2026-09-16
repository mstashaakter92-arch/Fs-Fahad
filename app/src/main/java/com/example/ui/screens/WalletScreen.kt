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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.window.Dialog
import com.example.model.TransactionType
import com.example.model.WalletTransaction
import com.example.ui.theme.*
import com.example.viewmodel.UiState

@Composable
fun WalletScreen(
    uiState: UiState,
    onOpenAddMoney: () -> Unit,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Deposit", "Top-up", "Refund", "Bonus")

    val filteredTransactions = remember(selectedFilter, uiState.transactions) {
        when (selectedFilter) {
            "Deposit" -> uiState.transactions.filter { it.type == TransactionType.DEPOSIT }
            "Top-up" -> uiState.transactions.filter { it.type == TransactionType.TOPUP }
            "Refund" -> uiState.transactions.filter { it.type == TransactionType.REFUND }
            "Bonus" -> uiState.transactions.filter { it.type == TransactionType.BONUS }
            else -> uiState.transactions
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp)
    ) {
        // Screen Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (onBack != null) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceDark)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDownward,
                            contentDescription = "Back",
                            tint = ElectricBlue
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Text(
                    text = "Wallet",
                    color = TextWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Wallet Balance Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF0D1B33),
                                Color(0xFF0A162B),
                                Color(0xFF081020)
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(listOf(Color(0x6600D2FF), Color(0x33FFBE18))),
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(18.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Available Balance",
                                color = TextMuted,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "৳ 1,250.00",
                                color = TextWhite,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        // Glowing Wallet Icon
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0x22FFBE18))
                                .border(1.2.dp, GamingYellow, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🪙", fontSize = 24.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons: ADD MONEY & TRANSACTION HISTORY
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onOpenAddMoney,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GamingYellow,
                                contentColor = Color(0xFF070B13)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Add Money",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp
                            )
                        }

                        OutlinedButton(
                            onClick = { /* already on wallet */ },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = Brush.linearGradient(listOf(Color(0x3300D2FF), Color(0x3300D2FF)))
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1.2f)
                        ) {
                            Text(
                                text = "Transaction History",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Filter Pills: [All] [Deposit] [Top-up] [Refund] [Bonus]
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) GamingYellow else SurfaceDark)
                            .border(
                                0.8.dp,
                                if (isSelected) GamingYellow else Color(0x2400D2FF),
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) Color(0xFF070B13) else TextMuted,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Transactions List
        items(filteredTransactions) { tx ->
            TransactionCard(tx = tx)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun TransactionCard(tx: WalletTransaction) {
    val isPositive = tx.type == TransactionType.DEPOSIT || tx.type == TransactionType.BONUS || tx.type == TransactionType.REFUND
    val iconColor = when (tx.type) {
        TransactionType.DEPOSIT -> StatusSuccess
        TransactionType.TOPUP -> ElectricBlue
        TransactionType.REFUND -> RoyalGold
        TransactionType.BONUS -> NeonCyan
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(0.8.dp, Color(0x2000D2FF), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPositive) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = tx.description,
                        color = TextWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${tx.id} • ${tx.timestamp}",
                        color = TextSubtle,
                        fontSize = 10.sp
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (isPositive) "+" else ""}৳${tx.amount.toInt()}",
                    color = if (isPositive) StatusSuccess else TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = tx.type.name,
                    color = iconColor,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AddMoneyDialog(
    onDismiss: () -> Unit,
    onAddMoney: (amount: Double, method: String) -> Unit
) {
    var amountText by remember { mutableStateOf("500") }
    var selectedMethod by remember { mutableStateOf("bKash") }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(BgDarkElevated)
                .border(1.dp, Color(0x3300D2FF), RoundedCornerShape(18.dp))
                .padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add Money to Wallet",
                        color = TextWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Deposit Amount (BDT)", color = TextMuted, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    prefix = { Text("৳ ", color = RoyalGold, fontWeight = FontWeight.Bold) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricBlue,
                        unfocusedBorderColor = Color(0x3300D2FF),
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Quick amount chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("200", "500", "1000", "2000").forEach { preset ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (amountText == preset) ElectricBlue else SurfaceDark)
                                .clickable { amountText = preset }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "৳$preset",
                                color = if (amountText == preset) Color.Black else TextWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(text = "Payment Method", color = TextMuted, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("bKash", "Nagad", "Rocket").forEach { m ->
                        val isSelected = selectedMethod == m
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFF1E293B) else SurfaceDark)
                                .border(
                                    width = if (isSelected) 1.2.dp else 0.5.dp,
                                    color = if (isSelected) ElectricBlue else Color(0x2400D2FF),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedMethod = m }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = m,
                                color = if (isSelected) ElectricBlue else TextMuted,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        val amt = amountText.toDoubleOrNull() ?: 500.0
                        onAddMoney(amt, selectedMethod)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "CONFIRM DEMO DEPOSIT",
                        color = Color(0xFF040A14),
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
