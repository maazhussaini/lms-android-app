package com.example.orb_ed.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.orb_ed.presentation.auth.AuthViewModel
import com.example.orb_ed.presentation.auth.navigateToLogin
import com.example.orb_ed.presentation.navigation.AppNavGraph
import com.example.orb_ed.presentation.theme.OrbEdTheme
import com.example.orb_ed.util.AuthEvent
import com.example.orb_ed.util.AuthEventBus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrbEdApp()
        }
    }
}

@Composable
fun OrbEdApp(
    navController: NavHostController = rememberNavController(),
    viewModel: AuthViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Handle authentication events
    LaunchedEffect(Unit) {
        AuthEventBus.authEvents.collect { event ->
            when (event) {
                is AuthEvent.AuthenticationFailed -> {
                    coroutineScope.launch {// Navigate to login screen and clear back stack
                        navController.navigateToLogin()
                        viewModel.clearAuthState()
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    OrbEdTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            content = { contentPadding ->
                AppNavGraph(navController = navController)
            }
        )
    }
}
