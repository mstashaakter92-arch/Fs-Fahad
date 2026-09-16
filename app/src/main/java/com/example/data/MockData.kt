package com.example.data

import com.example.model.*
import com.example.services.ApiLogEntry

object MockData {

    val games: List<Game> = listOf(
        Game(
            id = "free-fire",
            name = "Free Fire",
            category = "Battle Royale",
            subtitle = "Diamonds & Memberships",
            iconEmoji = "🔥",
            description = "Fast diamond top-up for Garena Free Fire. Instant delivery to your player UID within minutes.",
            idLabel = "Player ID (UID)",
            idPlaceholder = "e.g. 294829104",
            hasServerId = false,
            badge = "HOT",
            active = true
        ),
        Game(
            id = "pubg-mobile",
            name = "PUBG Mobile",
            category = "Battle Royale",
            subtitle = "Unknown Cash (UC)",
            iconEmoji = "🎯",
            description = "Official PUBG Mobile UC top-up. Global server instant credit using Player ID.",
            idLabel = "Character ID",
            idPlaceholder = "e.g. 512398472",
            hasServerId = false,
            badge = "POPULAR",
            active = true
        ),
        Game(
            id = "mlbb",
            name = "Mobile Legends: Bang Bang",
            category = "MOBA",
            subtitle = "Diamonds & Twilight Pass",
            iconEmoji = "⚔️",
            description = "Direct top-up for MLBB diamonds and weekly diamond pass. Requires User ID and Zone ID.",
            idLabel = "User ID",
            idPlaceholder = "e.g. 84920194",
            hasServerId = true,
            serverIdLabel = "Zone ID",
            serverIdPlaceholder = "e.g. (2140)",
            badge = "BESTSELLER",
            active = true
        ),
        Game(
            id = "efootball",
            name = "eFootball 2026",
            category = "Sports",
            subtitle = "eFootball Coins",
            iconEmoji = "⚽",
            description = "Top up eFootball coins instantly for player signing and special manager packs.",
            idLabel = "Konami / User ID",
            idPlaceholder = "e.g. 748-294-102",
            hasServerId = false,
            badge = null,
            active = true
        ),
        Game(
            id = "codm",
            name = "Call of Duty: Mobile",
            category = "FPS / Action",
            subtitle = "COD Points (CP)",
            iconEmoji = "🎮",
            description = "Get CP points for Call of Duty: Mobile battle pass, lucky draws, and weapon crates.",
            idLabel = "OpenID / Player ID",
            idPlaceholder = "e.g. 684928174029",
            hasServerId = false,
            badge = "TRENDING",
            active = true
        ),
        Game(
            id = "eight-ball-pool",
            name = "8 Ball Pool",
            category = "Sports / Arcade",
            subtitle = "Cash & Legendary Coins",
            iconEmoji = "🏎️",
            description = "Miniclip 8 Ball Pool cash, coins, and premium cue upgrades. Fast & secure.",
            idLabel = "Unique ID",
            idPlaceholder = "e.g. 394-102-849",
            hasServerId = false,
            badge = null,
            active = true
        ),
        Game(
            id = "roblox",
            name = "Roblox",
            category = "Sandbox / Metaverse",
            subtitle = "Robux & Gift Codes",
            iconEmoji = "🧱",
            description = "Roblox digital Robux credit codes for avatars, animations, and game passes.",
            idLabel = "Roblox Username",
            idPlaceholder = "e.g. GamerPro_BD",
            hasServerId = false,
            badge = "POPULAR",
            active = true
        ),
        Game(
            id = "coc",
            name = "Clash of Clans",
            category = "Strategy",
            subtitle = "Gems & Gold Pass",
            iconEmoji = "⚡",
            description = "Supercell Clash of Clans gems and monthly Gold Pass delivered straight to your Player Tag.",
            idLabel = "Player Tag",
            idPlaceholder = "e.g. #9Q2LV8PY",
            hasServerId = false,
            badge = null,
            active = true
        ),
        Game(
            id = "clash-royale",
            name = "Clash Royale",
            category = "Strategy / Cards",
            subtitle = "Gems & Pass Royale",
            iconEmoji = "👑",
            description = "Supercell Clash Royale gems, emote bundles, and Pass Royale progression.",
            idLabel = "Player Tag",
            idPlaceholder = "e.g. #2C9G0V8L",
            hasServerId = false,
            badge = null,
            active = true
        ),
        Game(
            id = "valorant",
            name = "Valorant",
            category = "Tactical FPS",
            subtitle = "Valorant Points (VP)",
            iconEmoji = "🎯",
            description = "Riot Games Valorant Points (VP) for weapon skins, battle pass, and radiantite.",
            idLabel = "Riot ID",
            idPlaceholder = "e.g. Fahad#BD1",
            hasServerId = false,
            badge = "ESPORTS",
            active = true
        )
    )

    val packages: Map<String, List<GamePackage>> = mapOf(
        "free-fire" to listOf(
            GamePackage("ff-100", "free-fire", "100 Diamonds", "100 💎", "+10 Bonus", 90.0, false),
            GamePackage("ff-310", "free-fire", "310 Diamonds", "310 💎", "+35 Bonus", 250.0, true),
            GamePackage("ff-520", "free-fire", "520 Diamonds", "520 💎", "+60 Bonus", 400.0, false),
            GamePackage("ff-1060", "free-fire", "1060 Diamonds", "1060 💎", "+120 Bonus", 780.0, true),
            GamePackage("ff-2180", "free-fire", "2180 Diamonds", "2180 💎", "+260 Bonus", 1580.0, false),
            GamePackage("ff-weekly", "free-fire", "Weekly Membership", "450 💎 Total", "7-Day Claim", 190.0, true),
            GamePackage("ff-monthly", "free-fire", "Monthly Membership", "2600 💎 Total", "30-Day Claim", 890.0, false)
        ),
        "pubg-mobile" to listOf(
            GamePackage("pubg-60", "pubg-mobile", "60 UC", "60 UC", "", 95.0, false),
            GamePackage("pubg-325", "pubg-mobile", "325 UC", "300 + 25 UC", "Bonus 25", 480.0, true),
            GamePackage("pubg-660", "pubg-mobile", "660 UC", "600 + 60 UC", "Royale Pass", 950.0, true),
            GamePackage("pubg-1800", "pubg-mobile", "1800 UC", "1500 + 300 UC", "Bonus 300", 2450.0, false),
            GamePackage("pubg-3850", "pubg-mobile", "3850 UC", "3000 + 850 UC", "Elite Pass Plus", 4900.0, false)
        ),
        "mlbb" to listOf(
            GamePackage("mlbb-86", "mlbb", "86 Diamonds", "78 + 8 💎", "", 130.0, false),
            GamePackage("mlbb-172", "mlbb", "172 Diamonds", "156 + 16 💎", "Popular", 260.0, false),
            GamePackage("mlbb-257", "mlbb", "257 Diamonds", "234 + 23 💎", "Bonus 23", 380.0, true),
            GamePackage("mlbb-706", "mlbb", "706 Diamonds", "625 + 81 💎", "Super Value", 990.0, true),
            GamePackage("mlbb-weekly", "mlbb", "Weekly Diamond Pass", "210 💎 Total", "Daily Rewards", 220.0, true)
        ),
        "efootball" to listOf(
            GamePackage("ef-130", "efootball", "130 Coins", "130 Coins", "", 120.0, false),
            GamePackage("ef-550", "efootball", "550 Coins", "500 + 50", "Bonus 50", 490.0, true),
            GamePackage("ef-1050", "efootball", "1050 Coins", "1000 + 50", "Special Pack", 920.0, true),
            GamePackage("ef-2150", "efootball", "2150 Coins", "2000 + 150", "Pro Deal", 1850.0, false)
        ),
        "codm" to listOf(
            GamePackage("cod-80", "codm", "80 CP", "80 Points", "", 95.0, false),
            GamePackage("cod-420", "codm", "420 CP", "400 + 20", "Battle Pass", 480.0, true),
            GamePackage("cod-880", "codm", "880 CP", "800 + 80", "Bonus 80", 960.0, true),
            GamePackage("cod-2400", "codm", "2400 CP", "2000 + 400", "Lucky Draw", 2500.0, false)
        ),
        "eight-ball-pool" to listOf(
            GamePackage("pool-cash-25", "eight-ball-pool", "25 Cash", "25 Cash", "", 110.0, false),
            GamePackage("pool-cash-110", "eight-ball-pool", "110 Cash", "100 + 10 Cash", "Popular", 450.0, true),
            GamePackage("pool-pass", "eight-ball-pool", "Pool Pass Pro", "Tier Unlocks", "Season Pass", 550.0, true)
        ),
        "roblox" to listOf(
            GamePackage("rbx-400", "roblox", "400 Robux", "400 R$", "", 490.0, false),
            GamePackage("rbx-800", "roblox", "800 Robux", "800 R$", "Popular", 950.0, true),
            GamePackage("rbx-1700", "roblox", "1,700 Robux", "1,700 R$", "Best Deal", 1950.0, true)
        ),
        "coc" to listOf(
            GamePackage("coc-80", "coc", "80 Gems", "80 💎", "", 95.0, false),
            GamePackage("coc-500", "coc", "500 Gems", "500 💎", "Builder Hut", 490.0, true),
            GamePackage("coc-pass", "coc", "Gold Pass", "Season Perks", "1-Month Pass", 650.0, true)
        ),
        "clash-royale" to listOf(
            GamePackage("cr-80", "clash-royale", "80 Gems", "80 💎", "", 95.0, false),
            GamePackage("cr-500", "clash-royale", "500 Gems", "500 💎", "Popular", 490.0, true),
            GamePackage("cr-pass", "clash-royale", "Pass Royale Diamond", "Premium Tier", "Season Pass", 1150.0, true)
        ),
        "valorant" to listOf(
            GamePackage("val-475", "valorant", "475 VP", "475 VP", "", 520.0, false),
            GamePackage("val-1000", "valorant", "1000 VP", "1000 VP", "Battle Pass", 1050.0, true),
            GamePackage("val-2050", "valorant", "2050 VP", "1900 + 150 VP", "Bonus 150", 2100.0, true)
        )
    )

    val initialOrders: List<Order> = listOf(
        Order(
            id = "FS-92810",
            gameId = "free-fire",
            gameName = "Free Fire",
            gameEmoji = "🔥",
            uid = "294829104",
            packageId = "ff-310",
            packageName = "310 Diamonds (Bonus 35)",
            amount = 250.0,
            discount = 0.0,
            total = 250.0,
            paymentMethod = "bKash",
            paymentStatus = PaymentStatus.COMPLETED,
            topupStatus = TopUpStatus.COMPLETED,
            createdAt = "Today, 09:15 AM",
            timelineStep = 4
        ),
        Order(
            id = "FS-84920",
            gameId = "pubg-mobile",
            gameName = "PUBG Mobile",
            gameEmoji = "🎯",
            uid = "512398472",
            packageId = "pubg-660",
            packageName = "660 UC (Royale Pass)",
            amount = 950.0,
            discount = 50.0,
            total = 900.0,
            paymentMethod = "Nagad",
            paymentStatus = PaymentStatus.VERIFIED,
            topupStatus = TopUpStatus.PROCESSING,
            createdAt = "Today, 09:42 AM",
            timelineStep = 3
        ),
        Order(
            id = "FS-76219",
            gameId = "mlbb",
            gameName = "Mobile Legends: Bang Bang",
            gameEmoji = "⚔️",
            uid = "84920194",
            serverId = "2140",
            packageId = "mlbb-257",
            packageName = "257 Diamonds",
            amount = 380.0,
            discount = 0.0,
            total = 380.0,
            paymentMethod = "Rocket",
            paymentStatus = PaymentStatus.PENDING,
            topupStatus = TopUpStatus.PENDING,
            createdAt = "Today, 08:30 AM",
            timelineStep = 1
        ),
        Order(
            id = "FS-63810",
            gameId = "valorant",
            gameName = "Valorant",
            gameEmoji = "🎯",
            uid = "Fahad#BD1",
            packageId = "val-1000",
            packageName = "1000 VP (Battle Pass)",
            amount = 1050.0,
            discount = 0.0,
            total = 1050.0,
            paymentMethod = "Card",
            paymentStatus = PaymentStatus.FAILED,
            topupStatus = TopUpStatus.FAILED,
            createdAt = "Yesterday, 11:20 PM",
            timelineStep = 1
        )
    )

    val initialTransactions: List<WalletTransaction> = listOf(
        WalletTransaction("TX-10921", TransactionType.DEPOSIT, 1000.0, "Wallet Top-Up via bKash", "16 Sep 2026, 08:00 AM"),
        WalletTransaction("TX-10920", TransactionType.BONUS, 50.0, "New User Registration Welcome Bonus", "15 Sep 2026, 10:00 PM"),
        WalletTransaction("TX-10842", TransactionType.TOPUP, -250.0, "Free Fire 310 Diamonds #FS-92810", "15 Sep 2026, 04:30 PM"),
        WalletTransaction("TX-10701", TransactionType.REFUND, 400.0, "Refund for cancelled order #FS-51928", "14 Sep 2026, 01:15 PM"),
        WalletTransaction("TX-10650", TransactionType.DEPOSIT, 500.0, "Instant Deposit via Nagad", "12 Sep 2026, 06:45 PM")
    )

    val offers: List<Offer> = listOf(
        Offer(
            id = "off-1",
            title = "Free Fire Mega Flash Deal",
            discountTag = "10% EXTRA DIAMONDS",
            description = "Get an extra 10% bonus diamonds on all orders above 520 diamonds. Limited time offer!",
            promoCode = "FFBONUS10",
            gameTarget = "Free Fire",
            expiresAt = "Expires in 2 days",
            isFeatured = true
        ),
        Offer(
            id = "off-2",
            title = "PUBG Royale Pass Cashback",
            discountTag = "৳50 CASHBACK",
            description = "Top up 660 UC or above with Nagad and get instant ৳50 cashback added to your FS wallet.",
            promoCode = "PUBGNAGAD",
            gameTarget = "PUBG Mobile",
            expiresAt = "Expires in 5 days",
            isFeatured = true
        ),
        Offer(
            id = "off-3",
            title = "First Top-Up Welcome Bonus",
            discountTag = "৳50 OFF",
            description = "New to FS FAHAD? Apply code on checkout for instant ৳50 discount on minimum order ৳300.",
            promoCode = "WELCOMEFS",
            gameTarget = "All Games",
            expiresAt = "Ongoing",
            isFeatured = false
        ),
        Offer(
            id = "off-4",
            title = "MLBB Weekly Diamond Pass Special",
            discountTag = "৳20 OFF",
            description = "Subscribe to your weekly diamond pass at only ৳200 instead of ৳220 with Rocket payment.",
            promoCode = "MLBBWEEKLY",
            gameTarget = "Mobile Legends",
            expiresAt = "Expires in 4 days",
            isFeatured = false
        )
    )

    val allPackages: List<GamePackage> = packages.values.flatten()

    val users: List<User> = listOf(
        User("usr_001", "Fahad Rahman", "fahad.gamer@fsfahad.com", "+880 1712-345678", UserRole.USER, 1450.0, false),
        User("usr_002", "Tanvir Ahmed", "tanvir.esports@gmail.com", "+880 1819-223344", UserRole.USER, 820.0, false),
        User("usr_003", "Shakil Hossain", "shakil_pubg@yahoo.com", "+880 1911-556677", UserRole.USER, 120.0, false),
        User("usr_004", "ProGamer_BD", "progamer.bd@gmail.com", "+880 1610-998877", UserRole.USER, 450.0, true)
    )

    val faqs: List<FaqItem> = listOf(
        FaqItem("How do I top up my game on FS FAHAD?", "1. Select your game from the home or games list.\n2. Enter your Player ID (UID) and Server ID if required.\n3. Choose your desired package.\n4. Proceed to checkout and select your preferred payment method (bKash, Nagad, Rocket, Card).\n5. Complete demo payment and receive instant top-up!"),
        FaqItem("Where can I find my Player ID / UID?", "Open your game, tap on your profile avatar in the top-left or top-right corner. Your Player ID / Character UID is displayed under your player name. Tap copy to paste it directly into FS FAHAD."),
        FaqItem("How fast is the delivery?", "Most top-ups are processed automatically within 1 to 5 minutes after payment verification."),
        FaqItem("What payment methods are supported in Bangladesh?", "We support all major Bangladeshi mobile financial services: bKash (বিকাশ), Nagad (নগদ), Rocket (রকেট), and Visa/Mastercard debit and credit cards."),
        FaqItem("What should I do if my top-up is delayed?", "Check your Order Tracking status using your Order ID. If the status remains pending beyond 10 minutes, contact FS AI or submit a support ticket with your Order ID for immediate resolution."),
        FaqItem("Is my account safe?", "100% safe. We only require your public Player ID/UID. We NEVER ask for your game password or login credentials.")
    )

    val initialApiLogs: List<ApiLogEntry> = listOf(
        ApiLogEntry("LOG-901", "/api/v1/topup/orders", "POST", 201, "09:54:12", 142),
        ApiLogEntry("LOG-900", "/api/v1/payments/verify", "POST", 200, "09:53:40", 88),
        ApiLogEntry("LOG-899", "/api/v1/games/catalog", "GET", 200, "09:52:10", 34),
        ApiLogEntry("LOG-898", "/api/v1/users/wallet", "GET", 200, "09:50:02", 28),
        ApiLogEntry("LOG-897", "/api/v1/topup/status/FS-92810", "GET", 200, "09:48:15", 52)
    )
}
