package com.example.wealthwatch.presentation.portfolio.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun ExpandablePortfolioSection(
    title: String,
    expanded: Boolean,
    onExpandClick: () -> Unit,
    color: androidx.compose.ui.graphics.Color = AppTheme.colors.primary,
    content: @Composable () -> Unit
) {
    val spacing = AppTheme.spacing

    // Modern rotation animation
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "ArrowRotation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(spacing.containerCornerRadius))
            .background(AppTheme.colors.surface) // Use surface or card background
            //.padding(bottom = spacing.spaceSmall)
    ) {
        // --- HEADER ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpandClick() }
                .padding(all = spacing.spaceSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Title + Indicator
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(4.dp, 24.dp)
                        .background(color, RoundedCornerShape(2.dp))
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                WWText(
                    text = title,
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurface
                )
            }

            // Right: Chevron
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = stringResource(R.string.desc_expand),
                modifier = Modifier
                    .size(spacing.defaultIcon)
                    .rotate(rotationState),
                tint = AppTheme.colors.onSurfaceVariant
            )
        }

        // --- CONTENT ---
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column {
                if(expanded){
                     HorizontalDivider(
                        modifier = Modifier
                            .padding(horizontal = spacing.spaceMedium)
                            .padding(bottom = spacing.spaceSmall),
                        color = AppTheme.colors.outline.copy(alpha = 0.1f),
                        thickness = 1.dp
                    )
                }
               
                content()
            }
        }
    }
}
