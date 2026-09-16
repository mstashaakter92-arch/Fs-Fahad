package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FaqItem
import com.example.ui.theme.*
import com.example.viewmodel.UiState

@Composable
fun SupportScreen(
    uiState: UiState,
    onSubmitTicket: (orderId: String, problemType: String, message: String) -> Unit,
    onOpenAiSupport: () -> Unit,
    modifier: Modifier = Modifier
) {
    var ticketOrderId by remember { mutableStateOf("") }
    var selectedProblemType by remember { mutableStateOf("Diamond / UC not received") }
    var ticketMessage by remember { mutableStateOf("") }
    var attachmentAttached by remember { mutableStateOf(false) }

    val problemTypes = listOf(
        "Diamond / UC not received",
        "Payment deducted but pending",
        "Wrong UID entered",
        "Wallet deposit inquiry",
        "Other questions"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp)
    ) {
        // Title
        item {
            Text(
                text = "Support & Help Desk",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "24/7 dedicated gamer assistance in Bengali & English",
                color = TextMuted,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Live Support Channels (WhatsApp, Telegram, AI Chat)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SupportChannelCard(
                    icon = "💬",
                    title = "FS AI Bot",
                    desc = "Instant answers",
                    badge = "ACTIVE",
                    onClick = onOpenAiSupport,
                    modifier = Modifier.weight(1f)
                )
                SupportChannelCard(
                    icon = "🟢",
                    title = "WhatsApp",
                    desc = "+880 1700-000000",
                    badge = "24/7",
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
                SupportChannelCard(
                    icon = "✈️",
                    title = "Telegram",
                    desc = "@FSFahadSupport",
                    badge = "COMMUNITY",
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Section: Submit Ticket Form
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceDark)
                    .border(1.dp, Color(0x3300D2FF), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ConfirmationNumber,
                        contentDescription = null,
                        tint = ElectricBlue,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "SUBMIT SUPPORT TICKET",
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Order ID Field
                OutlinedTextField(
                    value = ticketOrderId,
                    onValueChange = { ticketOrderId = it },
                    label = { Text("Order ID (optional)") },
                    placeholder = { Text("e.g. FS-92810", color = TextSubtle) },
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

                // Problem Type Selector
                Text(text = "Problem Category", color = TextMuted, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(6.dp))

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    problemTypes.forEach { type ->
                        val isSelected = selectedProblemType == type
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFF162540) else Color.Transparent)
                                .clickable { selectedProblemType = type }
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedProblemType = type },
                                colors = RadioButtonDefaults.colors(selectedColor = ElectricBlue)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = type,
                                color = if (isSelected) TextWhite else TextMuted,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Message text area
                OutlinedTextField(
                    value = ticketMessage,
                    onValueChange = { ticketMessage = it },
                    label = { Text("Detailed Message") },
                    placeholder = { Text("Describe the issue, player UID or transaction ID...", color = TextSubtle) },
                    minLines = 3,
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

                // Attachment button
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF131D31))
                        .border(0.8.dp, Color(0x3300D2FF), RoundedCornerShape(8.dp))
                        .clickable { attachmentAttached = !attachmentAttached }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (attachmentAttached) Icons.Default.Check else Icons.Default.AttachFile,
                        contentDescription = null,
                        tint = if (attachmentAttached) StatusSuccess else ElectricBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (attachmentAttached) "Screenshot Attached (payment_proof.png)" else "Attach Screenshot / Receipt",
                        color = if (attachmentAttached) StatusSuccess else TextWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Submit Button
                Button(
                    onClick = {
                        if (ticketMessage.isNotBlank()) {
                            onSubmitTicket(ticketOrderId, selectedProblemType, ticketMessage)
                            ticketMessage = ""
                            ticketOrderId = ""
                            attachmentAttached = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = Color(0xFF040A14),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "SUBMIT TICKET",
                        color = Color(0xFF040A14),
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Section: FAQ Accordion
        item {
            Text(
                text = "Frequently Asked Questions",
                color = TextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Quick answers regarding payment, delivery and safety",
                color = TextMuted,
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // FAQ Items
        items(uiState.faqs) { faq ->
            FaqAccordionItem(faq = faq)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SupportChannelCard(
    icon: String,
    title: String,
    desc: String,
    badge: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(0.8.dp, Color(0x2600D2FF), RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 18.sp)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0x3300D2FF))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(text = badge, color = ElectricBlue, fontSize = 8.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = title, color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text(text = desc, color = TextMuted, fontSize = 10.sp, maxLines = 1)
    }
}

@Composable
private fun FaqAccordionItem(faq: FaqItem) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(0.8.dp, if (expanded) ElectricBlue else Color(0x1F00D2FF), RoundedCornerShape(12.dp))
            .clickable { expanded = !expanded }
            .padding(14.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = faq.question,
                    color = TextWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = ElectricBlue,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = Color(0x1F00D2FF))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = faq.answer,
                        color = TextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}
