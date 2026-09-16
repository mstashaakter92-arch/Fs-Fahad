package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.Screen

@Composable
fun Footer(
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF060911))
            .border(
                width = 0.8.dp,
                color = Color(0x1F00D2FF),
                shape = RoundedCornerShape(0.dp)
            )
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo and Brand statement
        FsLogo(compact = false, onClick = { onNavigate(Screen.Home) })

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Game Top-Up Platform • FAST • SAFE • EASY",
            color = RoyalGold,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Bangladesh's premier esports top-up destination for Free Fire, PUBG Mobile, MLBB and more.",
            color = TextMuted,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Supported Payment Brands
        Text(
            text = "PAYMENT PARTNERS",
            color = TextSubtle,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PaymentPill("bKash", BkashPink)
            PaymentPill("Nagad", NagadOrange)
            PaymentPill("Rocket", RocketPurple)
            PaymentPill("VISA/Mastercard", CardNavy)
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Quick Navigation Links
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Home",
                color = TextWhite,
                fontSize = 12.sp,
                modifier = Modifier.clickable { onNavigate(Screen.Home) }
            )
            Text(
                text = "Games",
                color = TextWhite,
                fontSize = 12.sp,
                modifier = Modifier.clickable { onNavigate(Screen.Games) }
            )
            Text(
                text = "Offers",
                color = TextWhite,
                fontSize = 12.sp,
                modifier = Modifier.clickable { onNavigate(Screen.Offers) }
            )
            Text(
                text = "Support",
                color = TextWhite,
                fontSize = 12.sp,
                modifier = Modifier.clickable { onNavigate(Screen.Support) }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Privacy Policy",
                color = TextSubtle,
                fontSize = 11.sp,
                modifier = Modifier.clickable { }
            )
            Text(text = " • ", color = TextSubtle, fontSize = 11.sp)
            Text(
                text = "Terms & Conditions",
                color = TextSubtle,
                fontSize = 11.sp,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Social Media Icons / Badges
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SocialBadge("Facebook")
            SocialBadge("Telegram")
            SocialBadge("YouTube")
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(color = Color(0x1F1E293B))

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "© 2026 FS FAHAD. All Rights Reserved.\nDemo Production Frontend Prototype.",
            color = TextSubtle,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PaymentPill(name: String, brandColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(brandColor.copy(alpha = 0.2f))
            .border(0.8.dp, brandColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = name,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SocialBadge(name: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceDark)
            .border(0.8.dp, Color(0x3300D2FF), RoundedCornerShape(8.dp))
            .clickable { }
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = name,
            color = ElectricBlue,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
