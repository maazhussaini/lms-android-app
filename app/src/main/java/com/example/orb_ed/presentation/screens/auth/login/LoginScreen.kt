package com.example.orb_ed.presentation.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import com.example.orb_ed.presentation.screens.auth.signup.SignUpIntent
import com.example.orb_ed.presentation.theme.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    uiState: LoginState,
    effect: Flow<LoginEffect>,
    onIntent: (LoginIntent) -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onLoginSuccess: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isLoading by remember { mutableStateOf(false) }

    // Handle effects
    LaunchedEffect(Unit) {
        effect.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToNextScreen -> {
                    onLoginSuccess()
                }

                is LoginEffect.ShowLoading -> {
                    isLoading = true
                }

                is LoginEffect.HideLoading -> {
                    isLoading = false
                }

                is LoginEffect.NavigateToSignup -> {
                    onNavigateToSignUp()
                }

                is LoginEffect.NavigateToForgotPassword -> {
                    onNavigateToForgotPassword()
                }

                is LoginEffect.ShowError -> {
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
                    modifier = Modifier.padding(horizontal = 32.dp),
                    text = "Sign In To Your Account",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = stringResource(R.string.your_learning_anywhere_anytime),
                    style = MaterialTheme.typography.bodyLarge,
                    color = GreySubtitleColor
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Email field
                CustomTextField(
                    value = uiState.email,
                    onValueChange = { onIntent(LoginIntent.EmailChanged(it)) },
                    label = "Email",
                    hint = "Enter Your Email",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password field
                CustomTextField(
                    value = uiState.password,
                    onValueChange = { onIntent(LoginIntent.PasswordChanged(it)) },
                    label = "Password",
                    hint = "Enter Your Password",
                    trailingIcon = {
                        Icon(
                            modifier = Modifier
                                .size(15.dp)
                                .clickable {
                                    onIntent(LoginIntent.TogglePasswordVisibility)
                                },
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
                        imeAction = ImeAction.Done
                    )
                )

                // Forgot password link
                Text(
                    text = "Forgot Password?",
                    color = PrimaryColor,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { onIntent(LoginIntent.NavigateToForgotPassword) }
                        .padding(vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Login button
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    btnText = "Login",
                    isLoading = isLoading
                ) {
                    onIntent(LoginIntent.Login)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(
                        modifier = Modifier
                            .size(50.dp, 35.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = GreyBorderColor,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_finger_print),
                            contentDescription = "finger_print_icon"
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(50.dp, 35.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = GreyBorderColor,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_face_id),
                            contentDescription = "face_id_icon"
                        )
                    }


                }

                Spacer(modifier = Modifier.height(32.dp))
                AnnotatedLinkText(
                    fullText = "Don't have an account? Sign Up",
                    clickableParts = listOf(
                        ClickableTextPart(
                            text = "Sign Up",
                            tag = "signup",
                            onClick = {
                                onIntent(LoginIntent.NavigateToSignup)
                            },
                        )
                    )
                )
            }
        }
    }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    OrbEdTheme {
        LoginScreen(
            uiState = LoginState(),
            effect = emptyFlow(),
            onIntent = {},
            onNavigateToSignUp = {},
            onNavigateToForgotPassword = {},
            onLoginSuccess = { }
        )
    }

}
