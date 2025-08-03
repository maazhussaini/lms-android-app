package com.example.orb_ed.presentation.screens.courses

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.orb_ed.presentation.theme.GreySubtitleColor


@Composable
fun SubjectCategory(
    id: Int,
    color: Long, @DrawableRes image: Int, subject: String, onItemClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onItemClick(id)
        },
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .background(Color(color), shape = RoundedCornerShape(6.dp)),
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )

        Text(
            subject, style = MaterialTheme.typography.labelSmall.copy(color = GreySubtitleColor)
        )

    }

}