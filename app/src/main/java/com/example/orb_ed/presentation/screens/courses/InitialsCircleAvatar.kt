package com.example.orb_ed.presentation.screens.courses


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun InitialsCircleAvatar(
    name: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Gray,
    contentColor: Color = Color.White,
) {
    BoxWithConstraints(
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        val fontSize = maxWidth.value * 0.5 // 50% of width in sp

        Text(
            text = getInitials(name),
            color = contentColor,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp,
            maxLines = 1,
        )
    }
}

fun getInitials(name: String): String {
    val words = name.trim().split("\\s+".toRegex())
    return when {
        words.size >= 2 -> "${words.first().first()}${words.last().first()}"
        words.size == 1 -> words.first().take(2)
        else -> ""
    }.uppercase()
}