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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.services.ApiLogEntry
import com.example.ui.theme.*
import com.example.viewmodel.UiState

@Composable
fun DeveloperApiPanelScreen(
    uiState: UiState,
    onBack: () -> Unit,
    onToggleEnvironment: () -> Unit,
    onTriggerTestRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp)
    ) {
        // Top Header
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
                        text = "Developer & API Gateway",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Provider webhook & distribution endpoints",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Provider Status & Environment Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceDark)
                    .border(1.dp, Color(0x3300D2FF), RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "PROVIDER GATEWAY",
                            color = TextSubtle,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Garena & SmileOne TopUp API",
                            color = TextWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(StatusSuccess)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "HEALTHY",
                            color = StatusSuccess,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Environment Mode:", color = TextMuted, fontSize = 12.sp)

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F172A))
                            .border(0.8.dp, ElectricBlueGlow, RoundedCornerShape(8.dp))
                            .clickable { onToggleEnvironment() }
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = uiState.apiEnvironment,
                            color = if (uiState.apiEnvironment == "Production") RoyalGold else ElectricBlue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = "Switch",
                            tint = TextSubtle,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Last Synchronized:", color = TextMuted, fontSize = 12.sp)
                    Text(text = uiState.lastSyncTime, color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // Masked Credentials & Architecture Notice
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF0D1424))
                    .border(1.dp, Color(0x2600D2FF), RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "API Credentials (Frontend Masked)", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0x3310B981))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = "ZERO SECRETS LEAK", color = StatusSuccess, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                CredentialRow(label = "API_KEY", value = "fs_live_••••••••••••••••3829")
                CredentialRow(label = "WEBHOOK_SECRET", value = "whsec_••••••••••••••••9102")
                CredentialRow(label = "MERCHANT_ID", value = "FS_DHAKA_88190")

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "🛡️ Architecture Security: Real credentials must remain on backend server proxy. The frontend communicates exclusively via ApiService interface.",
                    color = TextSubtle,
                    fontSize = 10.sp,
                    lineHeight = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // Error Code & Request Test Tool
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceDark)
                    .border(1.dp, Color(0x2600D2FF), RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Text(text = "API Integration Test Tool", color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(text = "Simulate provider HTTP requests and webhooks", color = TextMuted, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onTriggerTestRequest,
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color(0xFF040A14),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "TRIGGER TEST RECHARGE PING",
                        color = Color(0xFF040A14),
                        fontWeight = FontWeight.Black,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Request Logs Title
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Request & Response Logs", color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(text = "${uiState.apiLogs.size} events", color = TextSubtle, fontSize = 11.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Request logs items
        items(uiState.apiLogs) { log ->
            ApiLogItemCard(log = log)
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun CredentialRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
        Text(text = value, color = ElectricBlue, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
    }
}

@Composable
private fun ApiLogItemCard(log: ApiLogEntry) {
    val is2xx = log.statusCode in 200..299
    val statusColor = if (is2xx) StatusSuccess else StatusFailed

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF090D17))
            .border(0.8.dp, Color(0x1F00D2FF), RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = log.method,
                        color = RoyalGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = log.endpoint,
                        color = TextWhite,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${log.timestamp} • Latency: ${log.durationMs}ms",
                    color = TextSubtle,
                    fontSize = 10.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(statusColor.copy(alpha = 0.2f))
                    .border(0.8.dp, statusColor, RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "${log.statusCode}",
                    color = statusColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}
