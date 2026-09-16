package com.example.model

enum class UserRole {
    USER,
    ADMIN,
    DEVELOPER
}

data class User(
    val id: String = "usr_001",
    val name: String = "Fahad Rahman",
    val email: String = "fahad.gamer@fsfahad.com",
    val phone: String = "+880 1712-345678",
    val role: UserRole = UserRole.USER,
    val walletBalance: Double = 1450.0,
    val isBanned: Boolean = false
)

data class Game(
    val id: String,
    val name: String,
    val category: String,
    val subtitle: String,
    val iconEmoji: String,
    val description: String,
    val idLabel: String = "Player ID / UID",
    val idPlaceholder: String = "e.g. 1234567890",
    val hasServerId: Boolean = false,
    val serverIdLabel: String = "Server / Zone ID",
    val serverIdPlaceholder: String = "e.g. 2001",
    val badge: String? = null,
    val active: Boolean = true
)

data class GamePackage(
    val id: String,
    val gameId: String,
    val name: String,
    val amount: String,
    val bonus: String = "",
    val price: Double,
    val isPopular: Boolean = false,
    val active: Boolean = true
)

enum class PaymentStatus {
    PENDING,
    VERIFIED,
    COMPLETED,
    FAILED
}

enum class TopUpStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED
}

data class Order(
    val id: String,
    val gameId: String,
    val gameName: String,
    val gameEmoji: String,
    val uid: String,
    val serverId: String? = null,
    val packageId: String,
    val packageName: String,
    val amount: Double,
    val discount: Double = 0.0,
    val total: Double = amount - discount,
    val paymentMethod: String,
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val topupStatus: TopUpStatus = TopUpStatus.PENDING,
    val createdAt: String = "Just now",
    val timelineStep: Int = 1 // 1: Created, 2: Payment Verified, 3: Processing, 4: Top-Up Completed
)

enum class TransactionType {
    DEPOSIT,
    TOPUP,
    REFUND,
    BONUS
}

data class WalletTransaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    val description: String,
    val timestamp: String,
    val status: String = "Completed"
)

data class Offer(
    val id: String,
    val title: String,
    val discountTag: String,
    val description: String,
    val promoCode: String,
    val gameTarget: String,
    val expiresAt: String,
    val isFeatured: Boolean = false
)

data class SupportTicket(
    val id: String,
    val orderId: String,
    val problemType: String,
    val message: String,
    val status: String = "Open",
    val createdAt: String = "Today, 10:20 AM"
)

data class AiMessage(
    val id: String,
    val sender: String, // "FS AI" or "User"
    val text: String,
    val timestamp: String,
    val isFromAi: Boolean
) {
    val isFromUser: Boolean get() = !isFromAi
}

data class FaqItem(
    val question: String,
    val answer: String
)
