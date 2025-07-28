package com.example.orb_ed.presentation.screens.auth.otp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orb_ed.R
import com.example.orb_ed.presentation.components.AnnotatedLinkText
import com.example.orb_ed.presentation.components.ClickableTextPart
import com.example.orb_ed.presentation.components.PrimaryButton
import com.example.orb_ed.presentation.theme.GreyBorderColor
import com.example.orb_ed.presentation.theme.GreyHintColor
import com.example.orb_ed.presentation.theme.GreySubtitleColor
import com.example.orb_ed.presentation.theme.LightPurpleBackgroundColor
import com.example.orb_ed.presentation.theme.OrbEdTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    uiState: OtpState,
    effect: Flow<OtpEffect>,
    onIntent: (OtpIntent) -> Unit,
    onNavigateBackToLogin: () -> Unit,
    onNavigateToNext: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    // Handle effects
    LaunchedEffect(Unit) {
        effect.collect { effect ->
            when (effect) {
                is OtpEffect.NavigateToNextScreen -> onNavigateToNext()
                is OtpEffect.ShowLoading -> { /* Handle loading state if needed */
                }

                is OtpEffect.HideLoading -> { /* Handle loading state if needed */
                }

                is OtpEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }

                is OtpEffect.ShowResendSuccess -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(snackbarHost = {
        Box(modifier = Modifier.fillMaxSize()) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.TopCenter)
            )
        }

    }, content = { padding ->
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
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 88.dp),
                    text = "Verify Your OTP",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = "Enter the code we sent to your email to continue.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = GreySubtitleColor
                )

                Spacer(modifier = Modifier.height(48.dp))

                RegistrationCodeInput(
                    codeLength = 6,
                    initialCode = uiState.otp,
                    onTextChanged = { onIntent(OtpIntent.OtpChanged(it.take(6))) }
                )


                Spacer(modifier = Modifier.height(48.dp))

                // Timer
                Text(
                    text = uiState.timeRemaining,
                    style = MaterialTheme.typography.labelLarge,
                    color = GreyHintColor
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Resend OTP
                if (uiState.isResendEnabled) {

                    AnnotatedLinkText(
                        fullText = "Don't Receive Any Code? Resend Code",
                        clickableParts = listOf(
                            ClickableTextPart(
                                text = "Resend Code",
                                tag = "resend_code",
                                onClick = {
                                    onIntent(OtpIntent.ResendOtp)
                                },
                            )
                        )
                    )
                }

                // Continue Button
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    btnText = "Continue",
                    isLoading = uiState.isLoading,
                    isEnabled = uiState.otp.length == 6
                ) {
                    onIntent(OtpIntent.VerifyOtp)
                }

                AnnotatedLinkText(
                    fullText = "Remember? Back To Login",
                    clickableParts = listOf(
                        ClickableTextPart(
                            text = "Back To Login",
                            tag = "login",
                            onClick = {
                                onNavigateBackToLogin()
                            },
                        )
                    )
                )
            }
        }
    })
}

@Composable
fun RegistrationCodeInput(codeLength: Int, initialCode: String, onTextChanged: (String) -> Unit) {
    val code = remember(initialCode) {
        mutableStateOf(TextFieldValue(initialCode, TextRange(initialCode.length)))
    }
    val focusRequester = FocusRequester()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        BasicTextField(
            value = code.value,
            onValueChange = { onTextChanged(it.text) },
            modifier = Modifier.focusRequester(focusRequester = focusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            decorationBox = {
                CodeInputDecoration(code.value.text, codeLength)
            }
        )
    }
}

@Composable
private fun CodeInputDecoration(code: String, length: Int) {
    Box(modifier = Modifier) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (i in 0 until length) {
                val text = if (i < code.length) code[i].toString() else ""
                CodeEntry(text)
            }
        }
    }
}

@Composable
private fun CodeEntry(text: String) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, GreyBorderColor, RoundedCornerShape(12.dp))

    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OtpScreenPreview() {
    OrbEdTheme {
        OtpScreen(
            uiState = OtpState(
                otp = "12332",
                isOtpValid = false,
                isResendEnabled = true,
                timeRemaining = "04:30",
                isLoading = false,
                email = "user@example.com"
            ),
            effect = emptyFlow(),
            onIntent = {},
            onNavigateBackToLogin = {},
            onNavigateToNext = {}
        )
    }
}
