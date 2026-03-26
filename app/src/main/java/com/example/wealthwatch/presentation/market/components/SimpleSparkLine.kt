package com.example.wealthwatch.presentation.market.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun SimpleSparkLine(
    modifier: Modifier = Modifier,
    priceChangePercent: String,
    color: Color,
    isFilled: Boolean = false
) {
    val percentage = remember(priceChangePercent) {
        priceChangePercent.replace("%", "").replace(",", ".").toDoubleOrNull() ?: 0.0
    }

    Canvas(modifier = modifier) {
        val path = Path()
        val width = size.width
        val height = size.height

        // 0% check for perfectly flat line
        if (kotlin.math.abs(percentage) < 0.01) {
            val centerY = height / 2f
            path.moveTo(0f, centerY)
            path.lineTo(width, centerY)
        } else {
            // Define mapping parameters
            val maxPercentage = 10.0 // Assume 10% is the "max" visualized change height
            
            // Normalize change: 5% change -> 0.5 factor
            val normalizedChange = (percentage / maxPercentage).coerceIn(-1.0, 1.0).toFloat()
            
            // Dynamic Start Y Position
            // If going UP (positive): Start lower to allow room to go up.
            // If going DOWN (negative): Start higher to allow room to go down.
            // This minimizes the empty vertical space.
            val startY = if (percentage > 0) {
                 height * 0.7f 
            } else {
                 height * 0.3f 
            }
            
            // Calculate End Y based on the change magnitude
            // Visual scale factor: how much vertical space the change consumes.
            // We use 40% of height as the max variation from start point.
            val scaleFactor = height * 0.4f
            val endY = startY - (normalizedChange / kotlin.math.abs(normalizedChange) * (kotlin.math.abs(normalizedChange) * scaleFactor)) 
            // Simplified: endY = startY - deltaY.
            // If positive (up), deltaY should be positive so endY is smaller (higher).
            // normalizedChange is positive.
            // endY = startY - (normalizedChange * scaleFactor)
            // If negative (down), normalizedChange is negative.
            // endY = startY - (-val) = startY + val. Lower (larger Y).
            // Wait, normalizedChange logic:
            // percentage=5 -> norm=0.5. scale=40. start=70. end=70 - (0.5*40) = 50. Moves UP 20 units.
            // percentage=-5 -> norm=-0.5. scale=40. start=30. end=30 - (-0.5*40) = 50. Moves DOWN 20 units.
            // This looks correct. But let's verify visual "Goes Up" means smaller Y.
            
            // Correction on bounds?
            // If normalizedChange is 1.0 (10%).
            // Up: start 70. end 70 - 40 = 30. Good.
            // Down: start 30. end 30 - (-40) = 70. Good.
            
            // Wait, my endY calculation:
            // val endY = startY - (normalizedChange * scaleFactor)
            // is effectively:
            // startY - ( (percentage/max) * height*0.4 )
            
            // Let's use a dynamic scaleFactor relative to 10% max?
            // If normalizedChange is 1.0, we want to use max space.
            // If normalizedChange is small, we use small space.
            
            // Revised logic:
            // Use specific target Ys for endpoints to maximize usage?
            // No, slope matters.
            
            val targetEndY = startY - (normalizedChange * (height * 0.5f)) // Use slightly more height (50%)

            val points = mutableListOf<Offset>()
            val segments = 10

            points.add(Offset(0f, startY))

            // Randomness seed based on percentage to be deterministic but look random
            val randomSeed = (percentage * 100).toInt().hashCode()
            val random = kotlin.random.Random(randomSeed)

            for (i in 1 until segments) {
                val progress = i.toFloat() / segments
                val x = width * progress

                // Linear path baseline
                val baselineY = startY + (targetEndY - startY) * progress

                // Add noise/volatility
                // Noise should be smaller if percentage is close to 0
                val volatilityFactor = 0.15f
                val noise = (random.nextFloat() - 0.5f) * height * volatilityFactor

                points.add(Offset(x, (baselineY + noise).coerceIn(0f, height)))
            }

            points.add(Offset(width, targetEndY))

            path.moveTo(points[0].x, points[0].y)
            for (i in 1 until points.size) {
                val p0 = points[i - 1]
                val p1 = points[i]
                val controlPoint1 = Offset((p0.x + p1.x) / 2, p0.y)
                val controlPoint2 = Offset((p0.x + p1.x) / 2, p1.y)
                path.cubicTo(
                    controlPoint1.x,
                    controlPoint1.y,
                    controlPoint2.x,
                    controlPoint2.y,
                    p1.x,
                    p1.y
                )
            }
        }

        drawPath(
            path = path, color = color, style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )

        if (isFilled) {
            val fillPath = Path()
            fillPath.addPath(path)
            fillPath.lineTo(width, height)
            fillPath.lineTo(0f, height)
            fillPath.close()

            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        color.copy(alpha = 0.5f), // Increased visibility
                        color.copy(alpha = 0.05f) // Subtle fade out
                    ),
                    startY = 0f,
                    endY = height
                )
            )
        }
    }
}
