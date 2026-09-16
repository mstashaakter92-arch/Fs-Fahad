package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
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
import com.example.model.Game
import com.example.model.GamePackage
import com.example.ui.components.PackageCard
import com.example.ui.theme.*
import com.example.viewmodel.UiState

enum class TopUpStep {
    GAME_DETAILS,
    SELECT_PACKAGE
}

@Composable
fun GameTopUpScreen(
    game: Game,
    packages: List<GamePackage>,
    uiState: UiState,
    onBack: () -> Unit,
    onUidChange: (String) -> Unit,
    onServerIdChange: (String) -> Unit,
    onPackageSelect: (GamePackage) -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(TopUpStep.GAME_DETAILS) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        AnimatedContent(
            targetState = step,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "topUpStepTransition"
        ) { currentStep ->
            when (currentStep) {
                TopUpStep.GAME_DETAILS -> {
                    GameDetailsStepView(
                        game = game,
                        uiState = uiState,
                        onBack = onBack,
                        onUidChange = onUidChange,
                        onServerIdChange = onServerIdChange,
                        onContinue = {
                            if (uiState.playerUid.isNotBlank()) {
                                step = TopUpStep.SELECT_PACKAGE
                            }
                        }
                    )
                }
                TopUpStep.SELECT_PACKAGE -> {
                    SelectPackageStepView(
                        game = game,
                        packages = packages,
                        uiState = uiState,
                        onBack = { step = TopUpStep.GAME_DETAILS },
                        onPackageSelect = onPackageSelect,
                        onContinue = onContinue
                    )
                }
            }
        }
    }
}

/**
 * Screen 3: "Game Details" (Player ID / UID input + Server ID input)
 */
@Composable
private fun GameDetailsStepView(
    game: Game,
    uiState: UiState,
    onBack: () -> Unit,
    onUidChange: (String) -> Unit,
    onServerIdChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    var validationError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) {
        // Header Bar
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
                text = "Game Details",
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
            // Game Art Banner Card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFF132342), Color(0xFF0C1628), Color(0xFF080E1A))
                            )
                        )
                        .border(1.2.dp, Color(0x3300D2FF), RoundedCornerShape(18.dp))
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(68.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(SurfaceDark)
                                    .border(1.5.dp, ElectricBlue, RoundedCornerShape(14.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = game.iconEmoji, fontSize = 34.sp)
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = game.name,
                                        color = TextWhite,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = "👑", fontSize = 14.sp)
                                }
                                Text(
                                    text = game.category,
                                    color = GamingYellow,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Official In-Game ID Direct Top-Up",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = game.description,
                            color = TextMuted,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // Player Information Form Card
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceDark)
                        .border(1.dp, Color(0x2B00D2FF), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "PLAYER INFORMATION",
                        color = ElectricBlue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Player ID / UID *",
                        color = TextWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = uiState.playerUid,
                        onValueChange = {
                            validationError = null
                            onUidChange(it)
                        },
                        placeholder = { Text("Enter your Player ID (UID)", color = TextSubtle, fontSize = 13.sp) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Paste",
                                tint = ElectricBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricBlue,
                            unfocusedBorderColor = Color(0x3300D2FF),
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (game.hasServerId) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Server ID (Optional)",
                            color = TextWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = uiState.serverId,
                            onValueChange = onServerIdChange,
                            placeholder = { Text("e.g. 5274", color = TextSubtle, fontSize = 13.sp) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Server",
                                    tint = TextSubtle,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricBlue,
                                unfocusedBorderColor = Color(0x3300D2FF),
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = StatusSuccess,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Enter your game ID carefully before continuing.",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }

                    if (validationError != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = validationError ?: "",
                            color = StatusFailed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Sticky Bottom CONTINUE Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    if (uiState.playerUid.isBlank()) {
                        validationError = "Please enter your Player ID (UID) to proceed."
                    } else {
                        onContinue()
                    }
                },
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
                    text = "CONTINUE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

/**
 * Screen 4: "Select Package" (Mini game badge, diamond packages, and checkout button)
 */
@Composable
private fun SelectPackageStepView(
    game: Game,
    packages: List<GamePackage>,
    uiState: UiState,
    onBack: () -> Unit,
    onPackageSelect: (GamePackage) -> Unit,
    onContinue: () -> Unit
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
                text = "Select Package",
                color = TextWhite,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Mini Game Badge
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceDark)
                        .border(0.8.dp, Color(0x2B00D2FF), RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF0C1628)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = game.iconEmoji, fontSize = 24.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = game.name,
                            color = TextWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "UID: ${uiState.playerUid}",
                            color = GamingYellow,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            // List of Diamond Package Cards
            items(packages) { pkg ->
                PackageCard(
                    pkg = pkg,
                    isSelected = uiState.selectedPackage?.id == pkg.id,
                    onSelect = { onPackageSelect(pkg) }
                )
            }
        }

        // Sticky Bottom Proceed to Checkout Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val selected = uiState.selectedPackage
            Button(
                onClick = onContinue,
                enabled = selected != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GamingYellow,
                    contentColor = Color(0xFF070B13),
                    disabledContainerColor = Color(0x33FFBE18),
                    disabledContentColor = Color(0x66FFFFFF)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = if (selected != null) "CONTINUE TO CHECKOUT (৳${selected.price.toInt()})" else "SELECT A PACKAGE",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
