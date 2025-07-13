package com.example.orb_ed.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orb_ed.ui.theme.LightPurpleBackgroundColor
import com.example.orb_ed.ui.theme.OrbEdTheme

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    OrbEdTheme(dynamicColor = false) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PrimaryButton(
                "fedf", modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                isLoading = true
            )

        }

    }

}

@Composable
fun PrimaryButton(
    btnText: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onBtnClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = { if (!isLoading) onBtnClick() },
    ) {
        if (isLoading)
            CircularProgressIndicator(color = LightPurpleBackgroundColor)
        else
            Text(btnText, style = MaterialTheme.typography.titleMedium)

    }

}