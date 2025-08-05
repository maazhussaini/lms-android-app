package com.example.orb_ed.presentation.auth

import androidx.navigation.NavController
import com.example.orb_ed.presentation.navigation.Login

/**
 * Navigates to the login screen and clears the back stack.
 */
fun NavController.navigateToLogin() {
    navigate(Login) {
        // Clear the back stack
        popUpTo(0) { inclusive = true }
        // Prevent multiple instances of the login screen
        launchSingleTop = true
    }
}
