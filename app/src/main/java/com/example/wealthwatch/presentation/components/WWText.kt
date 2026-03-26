package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun WWText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.text,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily = FontFamily.Default,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    // If color is unspecified, use our WWTheme text color,
    // otherwise fallback to style's color or standard black via takeOrElse if needed.
    // However, typical Compose pattern is: if explicit color is passed, use it.
    // If not, use style's color. If style's color is unspecified, use default.

    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun WWTextPreview() {
    com.example.wealthwatch.ui.theme.WealthWatchTheme {
        androidx.compose.foundation.layout.Column(modifier = Modifier.padding(16.dp)) {
            WWText(text = "Default WWText")
            WWText(text = "Styled WWText", style = AppTheme.typography.titleLarge)
            WWText(text = "Colored WWText", color = AppTheme.colors.primary)
        }
    }
}
