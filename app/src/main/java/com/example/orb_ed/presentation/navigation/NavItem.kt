package com.example.orb_ed.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.orb_ed.presentation.theme.LightPurpleBackgroundColor

@Composable
fun NavItem(imageVector: ImageVector, title: String, onItemClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(top = 24.dp)
                .clickable {
                    onItemClick()
                }, imageVector = imageVector, contentDescription = null
        )
        Text(
            title, color = LightPurpleBackgroundColor, style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

}