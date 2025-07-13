package com.example.orb_ed.presentation.screens.auth.resetpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orb_ed.R
import com.example.orb_ed.presentation.components.AnnotatedLinkText
import com.example.orb_ed.presentation.components.ClickableTextPart
import com.example.orb_ed.presentation.components.CustomTextField
import com.example.orb_ed.presentation.components.PrimaryButton
import com.example.orb_ed.presentation.screens.auth.login.LoginIntent
import com.example.orb_ed.presentation.theme.GreySubtitleColor
import com.example.orb_ed.presentation.theme.LightPurpleBackgroundColor
import com.example.orb_ed.presentation.theme.OrbEdTheme
import com.example.orb_ed.presentation.theme.PrimaryColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    uiState: ResetPasswordState,
    effect: Flow<ResetPasswordEffect>,
    onIntent: (ResetPasswordIntent) -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isLoading by remember { mutableStateOf(false) }

    // Handle effects
    LaunchedEffect(Unit) {
        effect.collect { effect ->
            when (effect) {
                is ResetPasswordEffect.NavigateToLogin -> {
                    onNavigateToLogin()
                }
                is ResetPasswordEffect.ShowLoading -> {
                    isLoading = true
                }
                is ResetPasswordEffect.HideLoading -> {
                    isLoading = false
                }
                is ResetPasswordEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is ResetPasswordEffect.ShowSuccess -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            Box(modifier = Modifier.fillMaxSize()) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.TopCenter)
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LightPurpleBackgroundColor)
                .padding(padding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth_bg),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 48.dp),
                    text = "Reset Your Password?",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = "Create a new password to continue.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = GreySubtitleColor
                )


                Spacer(modifier = Modifier.height(48.dp))

                // New Password field
                CustomTextField(
                    value = uiState.password,
                    onValueChange = { onIntent(ResetPasswordIntent.PasswordChanged(it)) },
                    label = "Password",
                    hint = "Enter Your Password",
                    trailingIcon = {
                        Icon(
                            modifier = Modifier
                                .size(15.dp)
                                .clickable { onIntent(ResetPasswordIntent.TogglePasswordVisibility) },
                            imageVector = if (uiState.isPasswordVisible) Icons.Outlined.VisibilityOff
                            else Icons.Outlined.Visibility,
                            contentDescription = if (uiState.isPasswordVisible) "Hide password"
                            else "Show password"
                        )
                    },
                    visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password field
                CustomTextField(
                    value = uiState.confirmPassword,
                    onValueChange = { onIntent(ResetPasswordIntent.ConfirmPasswordChanged(it)) },
                    label = "Confirm Password",
                    hint = "Confirm Your Password",
                    trailingIcon = {
                        Icon(
                            modifier = Modifier
                                .size(15.dp)
                                .clickable { onIntent(ResetPasswordIntent.ToggleConfirmPasswordVisibility) },
                            imageVector = if (uiState.isConfirmPasswordVisible) Icons.Outlined.VisibilityOff
                            else Icons.Outlined.Visibility,
                            contentDescription = if (uiState.isConfirmPasswordVisible) "Hide password"
                            else "Show password"
                        )
                    },
                    visualTransformation = if (uiState.isConfirmPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Reset Password button
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    btnText = "Reset Password",
                    isLoading = isLoading,
                ) {
                    onIntent(ResetPasswordIntent.ResetPassword)
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnnotatedLinkText(
                    fullText = "Remember? Back To Login",
                    clickableParts = listOf(
                        ClickableTextPart(
                            text = "Back To Login",
                            tag = "login",
                            onClick = {
                                onIntent(ResetPasswordIntent.NavigateToLogin)
                            },
                        )
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
    OrbEdTheme {
        ResetPasswordScreen(
            uiState = ResetPasswordState(),
            effect = emptyFlow(),
            onIntent = {},
            onNavigateToLogin = {}
        )
    }
}
