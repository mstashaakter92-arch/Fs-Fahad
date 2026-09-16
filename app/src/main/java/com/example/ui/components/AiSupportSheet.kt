package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SmartToy
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
import com.example.model.AiMessage
import com.example.ui.theme.*

@Composable
fun AiFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF0052D4),
                        Color(0xFF4364F7),
                        Color(0xFF6FB1FC)
                    )
                )
            )
            .border(
                width = 1.2.dp,
                color = ElectricBlue,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = "FS AI",
                    tint = TextWhite,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = "FS AI",
                color = TextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )
        }
    }
}

/**
 * Screen 10: "AI Support" Full Screen matching the reference mockup
 */
@Composable
fun AiSupportScreen(
    messages: List<AiMessage>,
    onSendMessage: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }

    val quickActions = listOf(
        "Where is my order?",
        "What is UID?",
        "My payment failed",
        "My order is pending"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
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
                text = "AI Support",
                color = TextWhite,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black
            )
        }

        // FS AI Avatar Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(SurfaceDark)
                .border(0.8.dp, Color(0x3300D2FF), RoundedCornerShape(14.dp))
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF0C203E))
                    .border(1.2.dp, ElectricBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🤖", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = "FS AI",
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Your Gaming Assistant",
                    color = ElectricBlue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Quick Prompt Pills
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quickActions) { action ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(SurfaceDark)
                        .border(0.8.dp, Color(0x3300D2FF), RoundedCornerShape(20.dp))
                        .clickable { onSendMessage(action) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = action,
                        color = ElectricBlue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Chat Message List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Initial welcoming message if empty or top
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF0F1E38))
                            .border(0.8.dp, Color(0x3300D2FF), RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Hi! I'm FS AI 👋\nWhere can I help you? Ask about your UID, order tracking, payment verification, or diamond packages!",
                            color = TextWhite,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            items(messages) { msg ->
                ChatBubble(message = msg)
            }
        }

        // Input Field + Send Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(SurfaceDark)
                .border(1.dp, Color(0x3300D2FF), RoundedCornerShape(24.dp))
                .padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("Type your message...", color = TextSubtle, fontSize = 13.sp) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                ),
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(GamingYellow)
                    .clickable {
                        if (inputText.isNotBlank()) {
                            onSendMessage(inputText)
                            inputText = ""
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = Color(0xFF070B13),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiSupportSheet(
    messages: List<AiMessage>,
    onSendMessage: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }

    val quickActions = listOf(
        "Where is my order?",
        "What is UID?",
        "My payment failed",
        "My order is pending"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = BgDarkElevated,
        dragHandle = { BottomSheetDefaults.DragHandle(color = ElectricBlue) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .navigationBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(ElectricBlue, NeonCyan))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🤖", fontSize = 18.sp)
                    }
                    Column {
                        Text(
                            text = "FS AI Assistant",
                            color = TextWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Instant 24/7 Gaming Support",
                            color = StatusSuccess,
                            fontSize = 11.sp
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Actions Pills
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(quickActions) { action ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceVariantDark)
                            .border(0.8.dp, ElectricBlueGlow, RoundedCornerShape(16.dp))
                            .clickable { onSendMessage(action) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = action,
                            color = ElectricBlue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Chat Messages List
            LazyColumn(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .heightIn(max = 280.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = "Hi! I'm FS AI 👋 How can I help you today?",
                        color = TextWhite,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF0F1E38))
                            .padding(10.dp)
                    )
                }
                items(messages) { msg ->
                    ChatBubble(message = msg)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Input Field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceDark)
                    .border(1.dp, Color(0x3300D2FF), RoundedCornerShape(14.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Type your message...", color = TextSubtle, fontSize = 12.sp) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onSendMessage(inputText)
                            inputText = ""
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = GamingYellow
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(message: AiMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                        bottomStart = if (message.isFromUser) 12.dp else 2.dp,
                        bottomEnd = if (message.isFromUser) 2.dp else 12.dp
                    )
                )
                .background(
                    if (message.isFromUser) {
                        Brush.linearGradient(listOf(Color(0xFF0052D4), Color(0xFF4364F7)))
                    } else {
                        Brush.linearGradient(listOf(Color(0xFF0E1A30), Color(0xFF0A1324)))
                    }
                )
                .border(
                    width = 0.8.dp,
                    color = if (message.isFromUser) ElectricBlue else Color(0x2B00D2FF),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            Column {
                Text(
                    text = message.text,
                    color = TextWhite,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.timestamp,
                    color = if (message.isFromUser) Color(0xCCFFFFFF) else TextSubtle,
                    fontSize = 9.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
