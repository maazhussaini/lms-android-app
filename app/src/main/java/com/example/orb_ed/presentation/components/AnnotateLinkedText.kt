package com.example.orb_ed.presentation.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.orb_ed.R
import com.example.orb_ed.presentation.theme.GreyHintColor

@Composable
fun AnnotatedLinkText(
    fullText: String,
    clickableParts: List<ClickableTextPart>,
    modifier: Modifier = Modifier,
    defaultTextStyle: TextStyle = MaterialTheme.typography.labelLarge.copy(color = GreyHintColor),
    defaultClickableStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.W700,
        fontFamily = FontFamily(Font(R.font.poppins_bold)),
        fontSize = 11.sp
    )
) {
    val linkListener = LinkInteractionListener { link ->
        if (link is LinkAnnotation.Clickable) {
            clickableParts.find { it.tag == link.tag }?.onClick?.invoke()
        }
    }

    val annotatedText = buildAnnotatedString {
        append(fullText)

        clickableParts.forEach { part ->
            val start = fullText.indexOf(part.text)
            if (start >= 0) {
                val end = start + part.text.length

                addStyle(
                    style = part.style ?: defaultClickableStyle,
                    start = start,
                    end = end
                )

                addLink(
                    LinkAnnotation.Clickable(
                        tag = part.tag,
                        linkInteractionListener = linkListener,
                        styles = null
                    ),
                    start = start,
                    end = end
                )
            }
        }
    }

    BasicText(
        text = annotatedText,
        modifier = modifier,
        style = defaultTextStyle
    )
}

data class ClickableTextPart(
    val text: String,
    val tag: String,
    val onClick: () -> Unit,
    val style: SpanStyle? = null
)
