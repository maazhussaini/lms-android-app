package com.example.orb_ed.presentation.screens.auth.otp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    email: String,
    onVerifySuccess: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: OtpVerificationViewModel = hiltViewModel()
) {
    var otp by remember { mutableStateOf("") }
    var isResendEnabled by remember { mutableStateOf(false) }
    var countdown by remember { mutableStateOf(30) }
    
    val uiState by viewModel.uiState.collectAsState()
    
    // Countdown timer for resend OTP
    LaunchedEffect(isResendEnabled, countdown) {
        if (countdown > 0 && !isResendEnabled) {
            kotlinx.coroutines.delay(1000)
            countdown--
        } else if (countdown == 0) {
            isResendEnabled = true
        }
    }
    
    // Handle verification success
    LaunchedEffect(uiState) {
        when (uiState) {
            is OtpVerificationUiState.Success -> {
                onVerifySuccess()
            }
            else -> {}
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verify OTP") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Enter the 6-digit code sent to\n$email",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            // OTP input field
            OutlinedTextField(
                value = otp,
                onValueChange = { 
                    if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                        otp = it
                    }
                },
                label = { Text("Verification Code") },
                placeholder = { Text("123456") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                maxLines = 1,
                modifier = Modifier.width(200.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Verify button
            Button(
                onClick = {
                    viewModel.verifyOtp(email, otp)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = otp.length == 6 && uiState !is OtpVerificationUiState.Loading
            ) {
                if (uiState is OtpVerificationUiState.Loading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Verify")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Resend OTP button
            TextButton(
                onClick = {
                    if (isResendEnabled) {
                        viewModel.resendOtp(email)
                        isResendEnabled = false
                        countdown = 30
                    }
                },
                enabled = isResendEnabled
            ) {
                if (isResendEnabled) {
                    Text("Resend Code")
                } else {
                    Text("Resend code in $countdown")
                }
            }
            
            // Show error message if any
            if (uiState is OtpVerificationUiState.Error) {
                val error = (uiState as OtpVerificationUiState.Error).message
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
