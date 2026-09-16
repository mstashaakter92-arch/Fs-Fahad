package com.example.services

import com.example.data.MockData
import com.example.model.*
import kotlinx.coroutines.delay
import java.util.UUID

class MockApiService : ApiService {

    private val gamesList = MockData.games.toMutableList()
    private val packagesMap = MockData.packages.toMutableMap()
    private val ordersList = MockData.initialOrders.toMutableList()
    private var currentWallet = 1450.0

    override suspend fun getGames(): List<Game> {
        delay(150)
        return gamesList
    }

    override suspend fun getPackages(gameId: String): List<GamePackage> {
        delay(150)
        return packagesMap[gameId] ?: emptyList()
    }

    override suspend fun createOrder(orderRequest: CreateOrderRequest): OrderResult {
        delay(300)
        val game = gamesList.find { it.id == orderRequest.gameId }
            ?: return OrderResult(false, null, "Game not found")

        val pkgs = packagesMap[orderRequest.gameId] ?: emptyList()
        val selectedPkg = pkgs.find { it.id == orderRequest.packageId }
            ?: return OrderResult(false, null, "Package not found")

        val randomIdSuffix = (10000..99999).random()
        val orderId = "FS-$randomIdSuffix"

        val discount = if (orderRequest.promoCode?.trim()?.equals("WELCOMEFS", ignoreCase = true) == true) 50.0 else 0.0

        val newOrder = Order(
            id = orderId,
            gameId = game.id,
            gameName = game.name,
            gameEmoji = game.iconEmoji,
            uid = orderRequest.uid,
            serverId = orderRequest.serverId,
            packageId = selectedPkg.id,
            packageName = "${selectedPkg.name} (${selectedPkg.amount})",
            amount = selectedPkg.price,
            discount = discount,
            total = (selectedPkg.price - discount).coerceAtLeast(0.0),
            paymentMethod = orderRequest.paymentMethod,
            paymentStatus = PaymentStatus.PENDING,
            topupStatus = TopUpStatus.PENDING,
            createdAt = "Just now",
            timelineStep = 1
        )

        ordersList.add(0, newOrder)
        return OrderResult(true, newOrder, "Order created successfully")
    }

    override suspend fun getOrder(orderId: String): Order? {
        delay(150)
        return ordersList.find { it.id.equals(orderId, ignoreCase = true) }
    }

    override suspend fun getWallet(): Double {
        delay(100)
        return currentWallet
    }

    override suspend fun createPayment(orderId: String, method: String, accountNo: String): PaymentResult {
        delay(400)
        val index = ordersList.indexOfFirst { it.id.equals(orderId, ignoreCase = true) }
        if (index == -1) {
            return PaymentResult(false, "", PaymentStatus.FAILED, "Order not found")
        }

        val existing = ordersList[index]
        val updated = existing.copy(
            paymentStatus = PaymentStatus.VERIFIED,
            topupStatus = TopUpStatus.PROCESSING,
            timelineStep = 3
        )
        ordersList[index] = updated

        return PaymentResult(
            success = true,
            transactionId = "TXN-${UUID.randomUUID().toString().take(8).uppercase()}",
            paymentStatus = PaymentStatus.VERIFIED,
            message = "Demo payment verified successfully. Top-up is now processing."
        )
    }

    override suspend fun getTopUpStatus(orderId: String): TopUpStatusResult {
        delay(200)
        val order = ordersList.find { it.id.equals(orderId, ignoreCase = true) }
        return if (order != null) {
            TopUpStatusResult(
                orderId = order.id,
                status = order.topupStatus,
                timelineStep = order.timelineStep,
                message = "Current status: ${order.topupStatus.name}"
            )
        } else {
            TopUpStatusResult(orderId, TopUpStatus.FAILED, 0, "Order not found")
        }
    }
}
