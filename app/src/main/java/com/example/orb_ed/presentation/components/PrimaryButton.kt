package com.example.orb_ed.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orb_ed.presentation.theme.LightPurpleBackgroundColor
import com.example.orb_ed.presentation.theme.OrbEdTheme
import com.example.orb_ed.presentation.theme.PrimaryColor

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    OrbEdTheme {
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

            PrimaryOutlinedButton(
                trailingIcon = Icons.Default.KeyboardArrowLeft,
                btnText = "fedf", modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                isLoading = false
            )

        }

    }

}

@Composable
fun PrimaryButton(
    btnText: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.titleSmall,
    onBtnClick: () -> Unit = {},
) {
    Button(
        border = BorderStroke(2.dp, PrimaryColor),
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = { if (!isLoading) onBtnClick() },
        enabled = isEnabled
    ) {
        if (isLoading)
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = LightPurpleBackgroundColor
            )
        else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(btnText, style = textStyle)
                leadingIcon?.let {
                    Icon(imageVector = leadingIcon, contentDescription = null)
                }
            }

        }

    }

}

@Composable
fun PrimaryOutlinedButton(
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.titleSmall,
    btnText: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onBtnClick: () -> Unit = {},
) {
    Button(
        border = BorderStroke(2.dp, PrimaryColor),
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = PrimaryColor
        ),
        onClick = { if (!isLoading) onBtnClick() },
        enabled = isEnabled
    ) {
        if (isLoading)
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = LightPurpleBackgroundColor
            )
        else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                trailingIcon?.let {
                    Icon(imageVector = trailingIcon, contentDescription = null)
                }
                Text(btnText, style = textStyle)
                leadingIcon?.let {
                    Icon(imageVector = leadingIcon, contentDescription = null)
                }
            }

        }

    }

}