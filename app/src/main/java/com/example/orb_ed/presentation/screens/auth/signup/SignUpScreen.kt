package com.example.orb_ed.presentation.screens.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.orb_ed.R
import com.example.orb_ed.presentation.components.CustomTextField
import com.example.orb_ed.presentation.components.PrimaryButton
import com.example.orb_ed.ui.theme.GreyBorderColor
import com.example.orb_ed.ui.theme.GreySubtitleColor
import com.example.orb_ed.ui.theme.LightPurpleBackgroundColor
import com.example.orb_ed.ui.theme.OrbEdTheme
import com.example.orb_ed.ui.theme.PrimaryColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen(uiState = SignUpState(), effect = emptyFlow(), onIntent = {}, {}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    uiState: SignUpState,
    effect: Flow<SignUpEffect>,
    onIntent: (SignUpIntent) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToNextScreen: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(effect) {
        effect.collect { effect ->
            when (effect) {
                is SignUpEffect.NavigateToNextScreen -> {
                    onNavigateToNextScreen()
                }

                SignUpEffect.ShowLoading -> {
                    isLoading = true
                }

                SignUpEffect.HideLoading -> {
                    isLoading = false
                }

                is SignUpEffect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short,
                        actionLabel = "Dismiss"
                    )
                }

                SignUpEffect.NavigateToLogin -> {
                    onNavigateToLogin()
                }
            }
        }
    }

    OrbEdTheme {
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
                Modifier
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
                        .padding(top = 72.dp)
                        .padding(horizontal = 25.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.sign_up_to_your_account),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(
                            text = stringResource(R.string.your_learning_anywhere_anytime),
                            style = MaterialTheme.typography.bodyLarge,
                            color = GreySubtitleColor
                        )
                        Spacer(Modifier.height(33.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            CustomTextField(
                                value = uiState.email,
                                onValueChange = { onIntent(SignUpIntent.EmailChanged(it)) },
                                label = "Email",
                                hint = "Enter Your Email",
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Next
                                )
                            )

                            CustomTextField(
                                value = uiState.phoneNumber,
                                onValueChange = { onIntent(SignUpIntent.PhoneNumberChanged(it)) },
                                label = "Phone Number",
                                hint = "(305) 123-4567",
                                showCountryPicker = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone,
                                    imeAction = ImeAction.Next
                                ),
                            )

                            CustomTextField(
                                value = uiState.password,
                                onValueChange = { onIntent(SignUpIntent.PasswordChanged(it)) },
                                label = "Password",
                                hint = "Enter Your Password",
                                trailingIcon = {
                                    Icon(
                                        modifier = Modifier.size(15.dp).clickable {
                                            onIntent(
                                                SignUpIntent.TogglePasswordVisibility(
                                                    !uiState.isPasswordVisible
                                                )
                                            )
                                        },
                                        imageVector = if (uiState.isPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                        contentDescription = if (uiState.isPasswordVisible) "Hide password" else "Show password"
                                    )
                                },
                                visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Next
                                ),
                            )

                            CustomTextField(
                                value = uiState.confirmPassword,
                                onValueChange = {
                                    onIntent(
                                        SignUpIntent.ConfirmPasswordChanged(
                                            it
                                        )
                                    )
                                },
                                label = "Confirm Password",
                                hint = "Confirm Your Password",
                                trailingIcon = {
                                    Icon(
                                        modifier = Modifier.size(15.dp).clickable {
                                            onIntent(
                                                SignUpIntent.ToggleConfirmPasswordVisibility(
                                                    !uiState.isConfirmPasswordVisible
                                                )
                                            )
                                        },
                                        imageVector =
                                            if (uiState.isConfirmPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                        contentDescription = if (uiState.isConfirmPasswordVisible) "Hide password" else "Show password"
                                    )
                                },
                                visualTransformation = if (uiState.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done
                                ),
                            )

                            val instituteOptions =
                                listOf("Institute 1", "Institute 2", "Institute 3")
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                ExposedDropdownMenuBox(
                                    expanded = uiState.isInstituteDropdownExpanded,
                                    onExpandedChange = {
                                        if (!uiState.isCheckboxChecked) onIntent(
                                            SignUpIntent.ToggleInstituteDropdown
                                        )
                                    },
                                ) {
                                    CustomTextField(
                                        enabled = !uiState.isCheckboxChecked,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .menuAnchor(),
                                        value = uiState.selectedInstitute,
                                        onValueChange = {},
                                        readOnly = true,
                                        label = "Institute",
                                        hint = "Select Your Institute",
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.isInstituteDropdownExpanded)
                                        },
                                    )

                                    ExposedDropdownMenu(
                                        expanded = uiState.isInstituteDropdownExpanded,
                                        onDismissRequest = { onIntent(SignUpIntent.ToggleInstituteDropdown) }
                                    ) {
                                        instituteOptions.forEach { selectionOption ->
                                            DropdownMenuItem(
                                                text = { Text(selectionOption) },
                                                onClick = {
                                                    onIntent(
                                                        SignUpIntent.SelectInstitute(
                                                            selectionOption
                                                        )
                                                    )
                                                    onIntent(SignUpIntent.ToggleInstituteDropdown)
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                                    Checkbox(
                                        checked = uiState.isCheckboxChecked,
                                        onCheckedChange = { onIntent(SignUpIntent.ToggleCheckbox(it)) },
                                        colors = CheckboxDefaults.colors(
                                            uncheckedColor = GreyBorderColor
                                        )
                                    )
                                }

                                Text(
                                    "Others",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        letterSpacing = 0.5.sp,
                                        color = PrimaryColor
                                    )
                                )
                            }

                            if (uiState.isCheckboxChecked) {
                                CustomTextField(
                                    value = uiState.instituteName,
                                    onValueChange = { onIntent(SignUpIntent.InstituteNameChanged(it)) },
                                    label = "Other Institute",
                                    hint = "Enter Your Institute"
                                )
                            }
                        }
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        PrimaryButton(
                            modifier = Modifier.fillMaxWidth(),
                            btnText = "Sign Up",
                            isLoading = isLoading
                        ) {
                            onIntent(SignUpIntent.SignUp)
                        }
                        LoginPrompt(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                        ) {
                            onIntent(SignUpIntent.NavigateToLogin)
                        }


                    }
                }

            }
        })

    }
}


@Composable
fun LoginPrompt(modifier: Modifier = Modifier, onLoginClick: () -> Unit) {
    val loginTag = "login"

    val linkListener = LinkInteractionListener { link ->
        if (link is LinkAnnotation.Clickable && link.tag == loginTag) {
            onLoginClick()
        }
    }

    val annotatedText = buildAnnotatedString {
        append("Already Have An Account? ")

        val loginText = "Log In"
        val start = length
        append(loginText)

        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.W700,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 11.sp
            ),
            start = start,
            end = start + loginText.length
        )

        addLink(
            LinkAnnotation.Clickable(
                tag = loginTag,
                linkInteractionListener = linkListener,
                styles = null // Optional: You can define `TextLinkStyles` here
            ),
            start = start,
            end = start + loginText.length
        )
    }

    BasicText(
        text = annotatedText,
        modifier = modifier,
        style = MaterialTheme.typography.labelLarge
    )
}


