package com.example.services

import com.example.model.*
import kotlinx.coroutines.delay

/**
 * FS FAHAD API Service Layer
 *
 * SECURITY NOTICE:
 * In compliance with gaming commerce security standards, this frontend service layer
 * never contains provider API secrets, reseller keys, or payment gateway secrets.
 * All sensitive top-up fulfillment and payment reconciliation operations are architected
 * to be processed securely through the backend server proxy.
 */
interface ApiService {
    suspend fun getGames(): List<Game>
    suspend fun getPackages(gameId: String): List<GamePackage>
    suspend fun createOrder(orderRequest: CreateOrderRequest): OrderResult
    suspend fun getOrder(orderId: String): Order?
    suspend fun getWallet(): Double
    suspend fun createPayment(orderId: String, method: String, accountNo: String): PaymentResult
    suspend fun getTopUpStatus(orderId: String): TopUpStatusResult
}

data class CreateOrderRequest(
    val gameId: String,
    val uid: String,
    val serverId: String?,
    val packageId: String,
    val paymentMethod: String,
    val promoCode: String? = null
)

data class OrderResult(
    val success: Boolean,
    val order: Order?,
    val message: String
)

data class PaymentResult(
    val success: Boolean,
    val transactionId: String,
    val paymentStatus: PaymentStatus,
    val message: String
)

data class TopUpStatusResult(
    val orderId: String,
    val status: TopUpStatus,
    val timelineStep: Int,
    val message: String
)

data class ApiLogEntry(
    val id: String,
    val endpoint: String,
    val method: String,
    val statusCode: Int,
    val timestamp: String,
    val durationMs: Long
)
