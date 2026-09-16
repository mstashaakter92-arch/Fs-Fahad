package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.UserRole
import com.example.ui.theme.*

@Composable
fun AuthDialog(
    isLogin: Boolean,
    onDismiss: () -> Unit,
    onSuccess: (name: String, emailOrPhone: String, role: UserRole) -> Unit
) {
    var isLoginTab by remember { mutableStateOf(isLogin) }
    var nameInput by remember { mutableStateOf("") }
    var identifierInput by remember { mutableStateOf("fahad.gamer@fsfahad.com") }
    var passwordInput by remember { mutableStateOf("••••••••") }
    var confirmPasswordInput by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(true) }
    var selectedRole by remember { mutableStateOf(UserRole.USER) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(BgDarkElevated)
                .border(1.dp, Color(0x4000D2FF), RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Header with close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FsLogo(compact = true)
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Tab Switcher: Login vs Signup
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceDark)
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isLoginTab) ElectricBlue else Color.Transparent)
                            .clickable { isLoginTab = true }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "LOGIN",
                            color = if (isLoginTab) Color(0xFF040A14) else TextMuted,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (!isLoginTab) ElectricBlue else Color.Transparent)
                            .clickable { isLoginTab = false }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "SIGN UP",
                            color = if (!isLoginTab) Color(0xFF040A14) else TextMuted,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (!isLoginTab) {
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Full Name") },
                        colors = outlinedFieldColors(),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                OutlinedTextField(
                    value = identifierInput,
                    onValueChange = { identifierInput = it },
                    label = { Text("Phone or Email") },
                    colors = outlinedFieldColors(),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = passwordInput,
                    onValueChange = { passwordInput = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = outlinedFieldColors(),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                if (!isLoginTab) {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = confirmPasswordInput,
                        onValueChange = { confirmPasswordInput = it },
                        label = { Text("Confirm Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = outlinedFieldColors(),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (isLoginTab) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(checkedColor = ElectricBlue)
                            )
                            Text(text = "Remember me", color = TextMuted, fontSize = 11.sp)
                        }

                        Text(
                            text = "Forgot password?",
                            color = ElectricBlue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable { }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Role selector for development / demo convenience
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = "Login as:", color = TextSubtle, fontSize = 10.sp)
                    UserRole.values().forEach { role ->
                        val isRoleSelected = selectedRole == role
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isRoleSelected) RoyalGold else SurfaceDark)
                                .clickable { selectedRole = role }
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = role.name,
                                color = if (isRoleSelected) Color.Black else TextMuted,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val name = if (isLoginTab) "Fahad Rahman" else nameInput.ifBlank { "New Gamer" }
                        onSuccess(name, identifierInput, selectedRole)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (isLoginTab) "LOGIN" else "CREATE ACCOUNT",
                        color = Color(0xFF040A14),
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "🔒 Frontend Mock Auth. Ready for backend integration.",
                    color = TextSubtle,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun outlinedFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = ElectricBlue,
    unfocusedBorderColor = Color(0x3300D2FF),
    focusedLabelColor = ElectricBlue,
    unfocusedLabelColor = TextMuted,
    focusedTextColor = TextWhite,
    unfocusedTextColor = TextWhite
)
