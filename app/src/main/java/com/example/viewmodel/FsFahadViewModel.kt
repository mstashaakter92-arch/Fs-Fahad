package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MockData
import com.example.model.*
import com.example.services.ApiService
import com.example.services.CreateOrderRequest
import com.example.services.MockApiService
import com.example.services.ApiLogEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

sealed class Screen {
    object Home : Screen()
    object Games : Screen()
    data class GameTopUp(val gameId: String) : Screen()
    object Checkout : Screen()
    data class OrderSuccess(val orderId: String) : Screen()
    data class OrderTracking(val orderId: String) : Screen()
    object Orders : Screen()
    object Offers : Screen()
    object Wallet : Screen()
    object Profile : Screen()
    object Support : Screen()
    object AdminDashboard : Screen()
    object DeveloperApiPanel : Screen()
}

data class UiState(
    val currentScreen: Screen = Screen.Home,
    val previousScreen: Screen? = null,
    val currentUser: User = User(),
    val users: List<User> = MockData.users,
    val games: List<Game> = MockData.games,
    val packages: List<GamePackage> = MockData.allPackages,
    val packagesByGame: Map<String, List<GamePackage>> = MockData.packages,
    val orders: List<Order> = MockData.initialOrders,
    val transactions: List<WalletTransaction> = MockData.initialTransactions,
    val offers: List<Offer> = MockData.offers,
    val faqs: List<FaqItem> = MockData.faqs,
    val tickets: List<SupportTicket> = emptyList(),

    // Game Top-up Flow
    val selectedGame: Game? = null,
    val selectedPackage: GamePackage? = null,
    val playerUid: String = "",
    val serverId: String = "",
    val uidError: String? = null,

    // Checkout
    val selectedPaymentMethod: String = "bKash",
    val promoCodeInput: String = "",
    val appliedDiscount: Double = 0.0,
    val promoMessage: String? = null,
    val lastCreatedOrder: Order? = null,
    val currentOrder: Order? = MockData.initialOrders.firstOrNull(),
    val isProcessingPayment: Boolean = false,

    // Search and Filters
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val orderStatusFilter: String = "All",
    val orderSearchQuery: String = "",

    // Dialogs & Sheets
    val showAuthDialog: Boolean = false,
    val isLoginTab: Boolean = true,
    val isAiSupportOpen: Boolean = false,
    val showAddMoneyDialog: Boolean = false,
    val toastMessage: String? = null,

    // AI Support Chat
    val aiMessages: List<AiMessage> = listOf(
        AiMessage("1", "FS AI", "Hi! I'm FS AI 👋 Welcome to FS FAHAD Game Top-Up. How can I power up your gaming today?", "Just now", true)
    ),

    // Developer API
    val apiLogs: List<ApiLogEntry> = MockData.initialApiLogs,
    val apiEnvironment: String = "Sandbox",
    val apiProviderStatus: String = "Operational (99.98%)",
    val lastSyncTime: String = "1 min ago"
)

class FsFahadViewModel(
    private val apiService: ApiService = MockApiService()
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun navigateTo(screen: Screen) {
        _uiState.update {
            it.copy(
                previousScreen = it.currentScreen,
                currentScreen = screen
            )
        }
    }

    fun selectGame(game: Game) {
        val gamePkgs = _uiState.value.packages.filter { it.gameId == game.id }
        val defaultPkg = gamePkgs.find { it.isPopular } ?: gamePkgs.firstOrNull()
        _uiState.update {
            it.copy(
                selectedGame = game,
                selectedPackage = defaultPkg,
                playerUid = "",
                serverId = "",
                uidError = null,
                appliedDiscount = 0.0,
                promoCodeInput = "",
                promoMessage = null,
                currentScreen = Screen.GameTopUp(game.id)
            )
        }
    }

    fun selectPackage(pkg: GamePackage) {
        _uiState.update { it.copy(selectedPackage = pkg) }
    }

    fun setPlayerUid(uid: String) {
        _uiState.update { it.copy(playerUid = uid, uidError = null) }
    }

    fun setServerId(serverId: String) {
        _uiState.update { it.copy(serverId = serverId) }
    }

    fun proceedToCheckout(): Boolean {
        val uid = _uiState.value.playerUid.trim()
        val game = _uiState.value.selectedGame
        val pkg = _uiState.value.selectedPackage

        if (uid.isEmpty()) {
            _uiState.update { it.copy(uidError = "Please enter your Player ID / UID to proceed") }
            return false
        }
        if (game?.hasServerId == true && _uiState.value.serverId.trim().isEmpty()) {
            _uiState.update { it.copy(uidError = "Server / Zone ID is required for ${game.name}") }
            return false
        }
        if (pkg == null) {
            _uiState.update { it.copy(uidError = "Please select a top-up package") }
            return false
        }

        _uiState.update { it.copy(currentScreen = Screen.Checkout) }
        return true
    }

    fun selectPaymentMethod(method: String) {
        _uiState.update { it.copy(selectedPaymentMethod = method) }
    }

    fun applyPromoCode(code: String) {
        val trimmed = code.trim()
        if (trimmed.equals("WELCOMEFS", ignoreCase = true) || trimmed.equals("FFBONUS10", ignoreCase = true)) {
            _uiState.update {
                it.copy(
                    promoCodeInput = trimmed,
                    appliedDiscount = 50.0,
                    promoMessage = "Promo code applied! ৳50 discount"
                )
            }
            showToast("Voucher applied: ৳50 saved!")
        } else if (trimmed.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    promoCodeInput = trimmed,
                    appliedDiscount = 0.0,
                    promoMessage = "Invalid code. Try WELCOMEFS"
                )
            }
        }
    }

    fun confirmDemoPayment() {
        val state = _uiState.value
        val game = state.selectedGame ?: state.games.first()
        val pkg = state.selectedPackage ?: state.packages.first()

        _uiState.update { it.copy(isProcessingPayment = true) }

        viewModelScope.launch {
            val req = CreateOrderRequest(
                gameId = game.id,
                uid = state.playerUid.ifBlank { "294829104" },
                serverId = if (game.hasServerId) state.serverId.ifBlank { "2001" } else null,
                packageId = pkg.id,
                paymentMethod = state.selectedPaymentMethod,
                promoCode = state.promoCodeInput.takeIf { it.isNotBlank() }
            )

            val result = apiService.createOrder(req)
            if (result.success && result.order != null) {
                val updatedOrder = result.order.copy(
                    paymentStatus = PaymentStatus.VERIFIED,
                    topupStatus = TopUpStatus.PROCESSING,
                    timelineStep = 3
                )

                _uiState.update {
                    it.copy(
                        orders = listOf(updatedOrder) + it.orders.filter { o -> o.id != updatedOrder.id },
                        lastCreatedOrder = updatedOrder,
                        currentOrder = updatedOrder,
                        isProcessingPayment = false,
                        currentScreen = Screen.OrderSuccess(updatedOrder.id)
                    )
                }
                showToast("Demo payment verified! Order #${updatedOrder.id} created")
            } else {
                _uiState.update { it.copy(isProcessingPayment = false) }
                showToast("Failed to create order. Please try again.")
            }
        }
    }

    fun viewOrderTracking(orderId: String) {
        val target = _uiState.value.orders.find { it.id == orderId } ?: _uiState.value.orders.firstOrNull()
        _uiState.update {
            it.copy(
                currentOrder = target,
                currentScreen = Screen.OrderTracking(orderId)
            )
        }
    }

    fun simulateOrderProgress(orderId: String) {
        _uiState.update { state ->
            val updated = state.orders.map { ord ->
                if (ord.id == orderId) {
                    when (ord.timelineStep) {
                        1 -> ord.copy(timelineStep = 2, paymentStatus = PaymentStatus.VERIFIED)
                        2 -> ord.copy(timelineStep = 3, topupStatus = TopUpStatus.PROCESSING)
                        3 -> ord.copy(timelineStep = 4, topupStatus = TopUpStatus.COMPLETED)
                        else -> ord
                    }
                } else ord
            }
            val updatedCurrent = updated.find { it.id == orderId }
            state.copy(orders = updated, currentOrder = updatedCurrent)
        }
        showToast("Order timeline updated")
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setCategory(cat: String) {
        _uiState.update { it.copy(selectedCategory = cat) }
    }

    fun setOrderStatusFilter(filter: String) {
        _uiState.update { it.copy(orderStatusFilter = filter) }
    }

    fun setOrderSearchQuery(q: String) {
        _uiState.update { it.copy(orderSearchQuery = q) }
    }

    fun toggleAiSupport(open: Boolean) {
        _uiState.update { it.copy(isAiSupportOpen = open) }
    }

    fun sendAiMessage(userText: String) {
        val trimmed = userText.trim()
        if (trimmed.isEmpty()) return

        val userMsg = AiMessage(UUID.randomUUID().toString(), "User", trimmed, "Just now", false)

        val aiResponseText = when {
            trimmed.contains("top up", ignoreCase = true) || trimmed.contains("how", ignoreCase = true) ->
                "To top up on FS FAHAD:\n1. Choose your game from the home screen.\n2. Enter your Player ID (UID).\n3. Pick your desired package (Diamonds, UC, etc.).\n4. Pay via bKash, Nagad, Rocket, or Card.\nDelivery is automatic within 1-5 minutes!"

            trimmed.contains("order", ignoreCase = true) || trimmed.contains("where", ignoreCase = true) ->
                "You can track your order status anytime under the 'Orders' tab in the bottom bar, or enter your Order ID in the search bar. Real-time updates show Payment Verified, Processing, and Top-Up Completed!"

            trimmed.contains("uid", ignoreCase = true) || trimmed.contains("player id", ignoreCase = true) ->
                "Your Player ID (UID) is the unique number inside your game profile. For Free Fire or PUBG, tap your profile avatar in-game and copy the number under your nickname. We never ask for your password!"

            trimmed.contains("fail", ignoreCase = true) || trimmed.contains("payment", ignoreCase = true) ->
                "If your payment failed or was interrupted, please check your bKash/Nagad balance. If money was deducted but the order did not start, open the Support tab to submit a ticket with your transaction ID for instant refund or manual delivery."

            trimmed.contains("pending", ignoreCase = true) ->
                "Pending status means we are verifying your payment with the gateway. It usually takes 60-120 seconds. You can tap 'Simulate Next Timeline Step' in your order tracking screen."

            else ->
                "I'm FS AI, your gaming top-up specialist! You can top up Free Fire, PUBG, MLBB, eFootball, and 6 other games. What would you like assistance with?"
        }

        val aiMsg = AiMessage(UUID.randomUUID().toString(), "FS AI", aiResponseText, "Just now", true)

        _uiState.update {
            it.copy(aiMessages = it.aiMessages + userMsg + aiMsg)
        }
    }

    fun addWalletMoney(amount: Double, method: String) {
        val newTx = WalletTransaction(
            id = "TX-${(10000..99999).random()}",
            type = TransactionType.DEPOSIT,
            amount = amount,
            description = "Deposit via $method",
            timestamp = "Just now"
        )
        _uiState.update {
            it.copy(
                currentUser = it.currentUser.copy(walletBalance = it.currentUser.walletBalance + amount),
                transactions = listOf(newTx) + it.transactions
            )
        }
        showToast("৳${amount.toInt()} added to your FS Wallet!")
    }

    fun switchRole(newRole: UserRole) {
        _uiState.update {
            it.copy(
                currentUser = it.currentUser.copy(role = newRole)
            )
        }
        showToast("Role switched to: ${newRole.name}")
    }

    fun login(name: String, emailOrPhone: String, role: UserRole) {
        _uiState.update {
            it.copy(
                currentUser = it.currentUser.copy(
                    name = name,
                    email = if (emailOrPhone.contains("@")) emailOrPhone else "${emailOrPhone}@phone.user",
                    phone = if (!emailOrPhone.contains("@")) emailOrPhone else "+880 1712-345678",
                    role = role
                )
            )
        }
        showToast("Welcome back, $name!")
    }

    fun submitSupportTicket(orderId: String, problemType: String, message: String) {
        val ticket = SupportTicket(
            id = "TCK-${(1000..9999).random()}",
            orderId = orderId.ifBlank { "N/A" },
            problemType = problemType,
            message = message,
            status = "In Review",
            createdAt = "Just now"
        )
        _uiState.update {
            it.copy(tickets = listOf(ticket) + it.tickets)
        }
        showToast("Ticket #${ticket.id} submitted! Support team notified.")
    }

    fun toggleGameVisibility(gameId: String) {
        _uiState.update { state ->
            val updated = state.games.map { g ->
                if (g.id == gameId) g.copy(active = !g.active) else g
            }
            state.copy(games = updated)
        }
        showToast("Game visibility updated")
    }

    fun updateOrderStatus(orderId: String, newTopUpStatus: TopUpStatus) {
        _uiState.update { state ->
            val updated = state.orders.map { ord ->
                if (ord.id == orderId) {
                    val step = when (newTopUpStatus) {
                        TopUpStatus.PENDING -> 1
                        TopUpStatus.PROCESSING -> 3
                        TopUpStatus.COMPLETED -> 4
                        TopUpStatus.FAILED -> 1
                    }
                    ord.copy(
                        topupStatus = newTopUpStatus,
                        timelineStep = step,
                        paymentStatus = if (newTopUpStatus == TopUpStatus.COMPLETED || newTopUpStatus == TopUpStatus.PROCESSING)
                            PaymentStatus.COMPLETED else ord.paymentStatus
                    )
                } else ord
            }
            state.copy(orders = updated)
        }
        showToast("Order marked as ${newTopUpStatus.name}")
    }

    fun toggleBanUser(userId: String) {
        _uiState.update { state ->
            val updated = state.users.map { u ->
                if (u.id == userId) u.copy(isBanned = !u.isBanned) else u
            }
            state.copy(users = updated)
        }
        showToast("User status updated")
    }

    fun toggleApiEnvironment() {
        _uiState.update {
            val nextEnv = if (it.apiEnvironment == "Sandbox") "Production" else "Sandbox"
            it.copy(apiEnvironment = nextEnv)
        }
        showToast("Environment switched to ${_uiState.value.apiEnvironment}")
    }

    fun triggerTestApiRequest() {
        val newLog = ApiLogEntry(
            id = "LOG-${(910..999).random()}",
            endpoint = "/api/v1/topup/recharge",
            method = "POST",
            statusCode = 200,
            timestamp = "Just now",
            durationMs = (40..120).random().toLong()
        )
        _uiState.update {
            it.copy(apiLogs = listOf(newLog) + it.apiLogs)
        }
        showToast("Test ping returned 200 OK (${newLog.durationMs}ms)")
    }

    fun showToast(msg: String) {
        _uiState.update { it.copy(toastMessage = msg) }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}
