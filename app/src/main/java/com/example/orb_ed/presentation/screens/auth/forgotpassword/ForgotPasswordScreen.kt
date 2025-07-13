package com.example.orb_ed.presentation.screens.auth.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orb_ed.R
import com.example.orb_ed.presentation.components.AnnotatedLinkText
import com.example.orb_ed.presentation.components.ClickableTextPart
import com.example.orb_ed.presentation.components.CustomTextField
import com.example.orb_ed.presentation.components.PrimaryButton
import com.example.orb_ed.presentation.theme.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    uiState: ForgotPasswordState,
    onNavigateBack: () -> Unit,
    onSendOtp: (String) -> Unit,
    effect: Flow<ForgotPasswordEffect>,
    onIntent: (ForgotPasswordIntent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isLoading by remember { mutableStateOf(false) }

    // Handle effects
    LaunchedEffect(Unit) {
        effect.collect { effect ->
            when (effect) {
                is ForgotPasswordEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }

                is ForgotPasswordEffect.ShowSuccess -> {
                    snackbarHostState.showSnackbar(effect.message)
                    onSendOtp(uiState.email)
                }

                is ForgotPasswordEffect.NavigateToLogin -> {
                    onNavigateBack()
                }

                ForgotPasswordEffect.HideLoading -> isLoading = false
                ForgotPasswordEffect.ShowLoading -> isLoading = true
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
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightPurpleBackgroundColor)
                    .padding(padding)
            ) {

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.auth_bg),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 25.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp),
                            text = "Forgot Your Password?",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(
                            text = "Don't worry, it happens. Let's reset it",
                            style = MaterialTheme.typography.bodyLarge,
                            color = GreySubtitleColor
                        )
                    }


                    // Email field
                    CustomTextField(
                        value = uiState.email,
                        onValueChange = { onIntent(ForgotPasswordIntent.EmailChanged(it)) },
                        label = "Email",
                        hint = "Enter Your Email To Receive OTP",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        )
                    )



                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Send OTP button
                        PrimaryButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            btnText = "Send",
                            isLoading = isLoading
                        ) {
                            onIntent(ForgotPasswordIntent.SendOtp)
                        }

                        // Back to login link
                        AnnotatedLinkText(
                            fullText = "Remember? Back to Login",
                            clickableParts = listOf(
                                ClickableTextPart(
                                    text = "Back to Login",
                                    tag = "login",
                                    onClick = { onIntent(ForgotPasswordIntent.NavigateToLogin) },
                                )
                            )
                        )
                    }

                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    OrbEdTheme {
        ForgotPasswordScreen(
            onNavigateBack = {},
            onSendOtp = {},
            uiState = ForgotPasswordState(),
            effect = emptyFlow(),
            onIntent = {}
        )
    }
}
