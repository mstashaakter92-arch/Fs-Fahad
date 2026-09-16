package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GamePackage
import com.example.ui.theme.*

@Composable
fun PackageCard(
    pkg: GamePackage,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = when {
        isSelected -> GamingYellow
        pkg.isPopular -> GamingYellow.copy(alpha = 0.85f)
        else -> Color(0x2E00D2FF)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.horizontalGradient(
                    colors = if (isSelected) {
                        listOf(Color(0xFF0F223D), Color(0xFF0C182B))
                    } else {
                        listOf(Color(0xFF0D1627), Color(0xFF090F1C))
                    }
                )
            )
            .border(
                width = if (isSelected || pkg.isPopular) 1.5.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable { onSelect() }
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        // Popular badge pinned at top right
        if (pkg.isPopular) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 6.dp, y = (-4).dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(GamingYellow)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "Popular",
                    color = Color(0xFF070B13),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Glowing 3D Diamond Icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x1A00D2FF)),
                    contentAlignment = Alignment.Center
                ) {
                    DiamondVector(
                        modifier = Modifier.size(34.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                // Middle: Diamond count & Bonus
                Column {
                    Text(
                        text = pkg.amount,
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black
                    )

                    if (pkg.bonus.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "+ ${pkg.bonus}",
                            color = GamingYellow,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Standard Pack",
                            color = TextSubtle,
                            fontSize = 10.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Price in BDT
                    Text(
                        text = "৳ ${pkg.price.toInt()}",
                        color = TextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            // Right: Select Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) GamingYellow else Color(0x3300D2FF))
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isSelected) "Selected" else "Select",
                    color = if (isSelected) Color(0xFF070B13) else ElectricBlue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

/**
 * 3D-styled faceted diamond crystal vector matching the mockup reference
 */
@Composable
fun DiamondVector(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Outer glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(ElectricBlue.copy(alpha = 0.5f), Color.Transparent),
                center = Offset(w * 0.5f, h * 0.5f),
                radius = w * 0.6f
            ),
            radius = w * 0.6f,
            center = Offset(w * 0.5f, h * 0.5f)
        )

        // Top table facets
        val topP1 = Offset(w * 0.25f, h * 0.22f)
        val topP2 = Offset(w * 0.75f, h * 0.22f)
        val midLeft = Offset(w * 0.08f, h * 0.44f)
        val midRight = Offset(w * 0.92f, h * 0.44f)
        val bottomTip = Offset(w * 0.5f, h * 0.88f)

        val centerTop = Offset(w * 0.5f, h * 0.22f)
        val centerMid = Offset(w * 0.5f, h * 0.44f)

        // Facet: Top Table Flat
        val tablePath = Path().apply {
            moveTo(topP1.x, topP1.y)
            lineTo(topP2.x, topP2.y)
            lineTo(centerMid.x, centerMid.y)
            close()
        }
        drawPath(tablePath, color = Color(0xFF38BDF8))

        // Facet: Left Upper
        val leftUpper = Path().apply {
            moveTo(topP1.x, topP1.y)
            lineTo(midLeft.x, midLeft.y)
            lineTo(centerMid.x, centerMid.y)
            close()
        }
        drawPath(leftUpper, color = Color(0xFF0284C7))

        // Facet: Right Upper
        val rightUpper = Path().apply {
            moveTo(topP2.x, topP2.y)
            lineTo(midRight.x, midRight.y)
            lineTo(centerMid.x, centerMid.y)
            close()
        }
        drawPath(rightUpper, color = Color(0xFF00D2FF))

        // Facet: Left Lower Pavilion
        val leftLower = Path().apply {
            moveTo(midLeft.x, midLeft.y)
            lineTo(centerMid.x, centerMid.y)
            lineTo(bottomTip.x, bottomTip.y)
            close()
        }
        drawPath(leftLower, color = Color(0xFF0369A1))

        // Facet: Right Lower Pavilion
        val rightLower = Path().apply {
            moveTo(midRight.x, midRight.y)
            lineTo(centerMid.x, centerMid.y)
            lineTo(bottomTip.x, bottomTip.y)
            close()
        }
        drawPath(rightLower, color = Color(0xFF0284C7))

        // Center specular shine highlight
        drawCircle(
            color = Color.White.copy(alpha = 0.8f),
            radius = w * 0.06f,
            center = Offset(w * 0.45f, h * 0.32f)
        )
    }
}
