package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.FsFahadViewModel
import com.example.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                FsFahadApp()
            }
        }
    }
}

@Composable
fun FsFahadApp(
    viewModel: FsFahadViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAuthDialog by remember { mutableStateOf(false) }
    var showAddMoneyDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Sticky Glassmorphism Header Bar
            HeaderBar(
                walletBalance = uiState.currentUser.walletBalance,
                onLogoClick = { viewModel.navigateTo(Screen.Home) },
                onWalletClick = { viewModel.navigateTo(Screen.Wallet) },
                onProfileClick = { viewModel.navigateTo(Screen.Profile) },
                onNotificationClick = { viewModel.showToast("🔔 No new alerts. All systems operational.") }
            )

            // Animated Screen Switching Container
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                AnimatedContent(
                    targetState = uiState.currentScreen,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "screenTransition"
                ) { screen ->
                    when (screen) {
                        is Screen.Home -> {
                            HomeScreen(
                                uiState = uiState,
                                onNavigate = { viewModel.navigateTo(it) },
                                onSelectGame = { viewModel.selectGame(it) }
                            )
                        }
                        is Screen.Games -> {
                            GamesScreen(
                                uiState = uiState,
                                onSelectGame = { viewModel.selectGame(it) },
                                onSearchChange = { viewModel.setSearchQuery(it) },
                                onCategoryChange = { viewModel.setCategory(it) }
                            )
                        }
                        is Screen.GameTopUp -> {
                            val game = uiState.selectedGame ?: uiState.games.first()
                            val gamePkgs = uiState.packages.filter { it.gameId == game.id }
                            GameTopUpScreen(
                                game = game,
                                packages = gamePkgs,
                                uiState = uiState,
                                onBack = { viewModel.navigateTo(Screen.Games) },
                                onUidChange = { viewModel.setPlayerUid(it) },
                                onServerIdChange = { viewModel.setServerId(it) },
                                onPackageSelect = { viewModel.selectPackage(it) },
                                onContinue = { viewModel.proceedToCheckout() }
                            )
                        }
                        is Screen.Checkout -> {
                            CheckoutScreen(
                                uiState = uiState,
                                onBack = { 
                                    val gId = uiState.selectedGame?.id ?: uiState.games.first().id
                                    viewModel.navigateTo(Screen.GameTopUp(gId)) 
                                },
                                onSelectPayment = { viewModel.selectPaymentMethod(it) },
                                onApplyPromo = { viewModel.applyPromoCode(it) },
                                onConfirmDemoPayment = { viewModel.confirmDemoPayment() }
                            )
                        }
                        is Screen.OrderSuccess -> {
                            val order = uiState.currentOrder ?: uiState.orders.first()
                            OrderSuccessScreen(
                                order = order,
                                onTrackOrder = { viewModel.viewOrderTracking(it) },
                                onBackHome = { viewModel.navigateTo(Screen.Home) }
                            )
                        }
                        is Screen.OrderTracking -> {
                            val order = uiState.currentOrder ?: uiState.orders.firstOrNull()
                            OrderTrackingScreen(
                                order = order,
                                onBack = { viewModel.navigateTo(Screen.Orders) },
                                onAdvanceSimulation = { viewModel.simulateOrderProgress(it) },
                                onContactSupport = { viewModel.navigateTo(Screen.Support) }
                            )
                        }
                        is Screen.Orders -> {
                            OrdersScreen(
                                uiState = uiState,
                                onViewOrder = { viewModel.viewOrderTracking(it) },
                                onFilterChange = { viewModel.setOrderStatusFilter(it) },
                                onSearchChange = { viewModel.setOrderSearchQuery(it) }
                            )
                        }
                        is Screen.Offers -> {
                            OffersScreen(
                                uiState = uiState,
                                onApplyCode = { code ->
                                    viewModel.applyPromoCode(code)
                                    viewModel.navigateTo(Screen.Games)
                                },
                                onNavigate = { viewModel.navigateTo(it) }
                            )
                        }
                        is Screen.Wallet -> {
                            WalletScreen(
                                uiState = uiState,
                                onOpenAddMoney = { showAddMoneyDialog = true },
                                onBack = { viewModel.navigateTo(Screen.Home) }
                            )
                        }
                        is Screen.Profile -> {
                            ProfileScreen(
                                uiState = uiState,
                                onNavigate = { viewModel.navigateTo(it) },
                                onSwitchRole = { viewModel.switchRole(it) },
                                onOpenAuth = { showAuthDialog = true },
                                onLogout = { showAuthDialog = true }
                            )
                        }
                        is Screen.Support -> {
                            SupportScreen(
                                uiState = uiState,
                                onSubmitTicket = { oid, problem, msg ->
                                    viewModel.submitSupportTicket(oid, problem, msg)
                                },
                                onOpenAiSupport = { viewModel.toggleAiSupport(true) }
                            )
                        }
                        is Screen.AdminDashboard -> {
                            AdminDashboardScreen(
                                uiState = uiState,
                                onNavigate = { viewModel.navigateTo(it) },
                                onToggleGame = { viewModel.toggleGameVisibility(it) },
                                onUpdateOrderStatus = { id, st -> viewModel.updateOrderStatus(id, st) },
                                onToggleBanUser = { viewModel.toggleBanUser(it) }
                            )
                        }
                        is Screen.DeveloperApiPanel -> {
                            DeveloperApiPanelScreen(
                                uiState = uiState,
                                onBack = { viewModel.navigateTo(Screen.AdminDashboard) },
                                onToggleEnvironment = { viewModel.toggleApiEnvironment() },
                                onTriggerTestRequest = { viewModel.triggerTestApiRequest() }
                            )
                        }
                    }
                }
            }
        }

        // Floating FS AI Button (Bottom-Right, above bottom nav)
        if (!uiState.isAiSupportOpen && uiState.currentScreen !is Screen.GameTopUp && uiState.currentScreen !is Screen.Checkout) {
            AiFloatingButton(
                onClick = { viewModel.toggleAiSupport(true) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 78.dp)
            )
        }

        // Mobile Bottom Navigation Bar (Persistent on main views)
        val showBottomNav = uiState.currentScreen !is Screen.Checkout &&
                            uiState.currentScreen !is Screen.OrderSuccess &&
                            uiState.currentScreen !is Screen.DeveloperApiPanel

        if (showBottomNav) {
            BottomNavBar(
                currentScreen = uiState.currentScreen,
                onNavigate = { viewModel.navigateTo(it) },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        // AI Assistant Modal BottomSheet
        if (uiState.isAiSupportOpen) {
            AiSupportSheet(
                messages = uiState.aiMessages,
                onSendMessage = { viewModel.sendAiMessage(it) },
                onDismiss = { viewModel.toggleAiSupport(false) }
            )
        }

        // Authentication Modal Dialog (Login / Signup)
        if (showAuthDialog) {
            AuthDialog(
                isLogin = true,
                onDismiss = { showAuthDialog = false },
                onSuccess = { name, emailOrPhone, role ->
                    viewModel.login(name, emailOrPhone, role)
                }
            )
        }

        // Add Money to Wallet Modal Dialog
        if (showAddMoneyDialog) {
            AddMoneyDialog(
                onDismiss = { showAddMoneyDialog = false },
                onAddMoney = { amt, method ->
                    viewModel.addWalletMoney(amt, method)
                    showAddMoneyDialog = false
                }
            )
        }

        // Floating Toast Notification
        if (uiState.toastMessage != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 64.dp, start = 20.dp, end = 20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F1B36))
                    .border(1.dp, ElectricBlue, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = uiState.toastMessage ?: "",
                    color = TextWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}
