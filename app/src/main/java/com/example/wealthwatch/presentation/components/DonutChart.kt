package com.example.wealthwatch.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.R
import com.example.wealthwatch.ui.theme.AppTheme
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    selectedSliceIndex: Int?,
    formattedTotalBalance: String,
    slices: List<PieSlice>,
    onSliceSelect: (Int?) -> Unit,
) {
    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(slices) {
        animationProgress.animateTo(1f, animationSpec = tween(1000))
    }

    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier.fillMaxHeight(), contentAlignment = Alignment.Center
    ) {
        val chartSize: Dp = minOf(maxWidth, maxHeight)
        // 1. Sizing: Add safer padding to prevent clipping of "lifted" slices
        // Stroke is approx 35.dp. Lift is 6.dp. So we need at least stroke/2 + lift padding.
        val strokeWidthDp = 35.dp
        val strokeWidthPx = with(density) { strokeWidthDp.toPx() }
        val liftPx = with(density) { 6.dp.toPx() }

        // Effective padding needed = Half stroke (to keep stroke inside) + Lift (for animation)
        val safePadding = strokeWidthPx / 2 + liftPx + 8f // Extra buffer

        // The diameter we can actually draw in
        val availableDiameter = with(density) { chartSize.toPx() - (safePadding * 2) }

        val radius = availableDiameter / 2

        Canvas(
            modifier = Modifier
                .size(chartSize)
                .pointerInput(slices, selectedSliceIndex) {
                    detectTapGestures { offset ->
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val dx = offset.x - center.x
                        val dy = offset.y - center.y

                        // 2. Selection - Distance Check (Donut Logic)
                        val dist = sqrt((dx * dx + dy * dy).toDouble()).toFloat()

                        // Inner radius = Radius - Stroke. 
                        // Outer radius = Radius + Stroke/2.
                        // Add liftPx to outer radius to ensure lifted slices catch taps.

                        val r = radius // center of the ring
                        val innerR = r - (strokeWidthPx / 1.5f)
                        val outerR = r + (strokeWidthPx / 1.5f) + liftPx // Account for lift

                        if (dist < innerR || dist > outerR) {
                            // Clicked center hole or outside - Deselect
                            onSliceSelect(null)
                            return@detectTapGestures
                        }

                        // 3. Angle Check
                        // Normalize touch angle to standard 0..360 (Where 0 is 3 o'clock, 90 is 6 o'clock)
                        var angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
                        if (angle < 0) angle += 360f

                        val found = slices.indexOfFirst { slice ->
                            // Check if angle falls within [start, start + sweep]
                            // We need to handle the cyclic nature.
                            // Easiest is to normalize the difference.

                            // 1. Shift angle so start is at 0
                            val relativeAngle = (angle - slice.startAngle + 360) % 360

                            // 2. Check if it falls in sweep (with small buffer for precision)
                            relativeAngle <= slice.sweepAngle
                        }

                        if (found != -1) {
                            // Toggle: If duplicate click, deselect. Else select.
                            onSliceSelect(if (selectedSliceIndex == found) null else found)
                        } else {
                            // Valid distance but no angle matched (rare/impossible?) -> Deselect
                            onSliceSelect(null)
                        }
                    }
                }) {
            val stroke = Stroke(width = strokeWidthPx)

            // Center the drawing in the available space
            // Canvas size is chartSize. 
            // We want to draw at center with radius 'availableDiameter/2'
            // TopLeft of the arc rect should be:
            // (Width - Diameter) / 2

            val offsetToCenter = with(density) { (chartSize.toPx() - availableDiameter) / 2 }

            slices.forEachIndexed { index, slice ->
                val isSelected = selectedSliceIndex == index
                val sweep = slice.sweepAngle * animationProgress.value

                // Lift effect
                val midRad = Math.toRadians((slice.startAngle + slice.sweepAngle / 2).toDouble())
                val shift = if (isSelected) liftPx else 0f

                val shiftX = (shift * cos(midRad)).toFloat()
                val shiftY = (shift * sin(midRad)).toFloat()

                drawArc(
                    color = slice.color.copy(alpha = if (selectedSliceIndex == null || isSelected) 1f else 0.3f),
                    startAngle = slice.startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = Offset(offsetToCenter + shiftX, offsetToCenter + shiftY),
                    size = Size(availableDiameter, availableDiameter),
                    style = stroke
                )
            }
        }

        // Center Text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(AppTheme.spacing.spaceMedium)
        ) {
            val selected = selectedSliceIndex?.let { slices[it] }

            if (selected != null) {
                // Selected Slice State
                WWText(
                    text = if (selected.titleResId != null) stringResource(selected.titleResId) else selected.label,
                    style = AppTheme.typography.labelSmall,
                    color = AppTheme.colors.onSurfaceVariant
                )
                WWText(
                    text = selected.formattedPercentage, // Use pre-formatted percentage
                    style = AppTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurface
                )
                WWText(
                    text = selected.formattedValue, // Use pre-formatted value
                    style = AppTheme.typography.labelMedium,
                    color = AppTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            } else {
                // Default State
                WWText(
                    text = stringResource(R.string.total_balance),
                    style = AppTheme.typography.labelSmall,
                    color = AppTheme.colors.onSurfaceVariant
                )
                WWText(
                    text = formattedTotalBalance, // Use pre-formatted total
                    style = AppTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurface
                )
            }
        }
    }
}
