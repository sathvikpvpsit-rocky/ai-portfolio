package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Indigo400
import com.example.ui.theme.NeonGlowCyan
import com.example.ui.theme.Slate700
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class RadarAxis(
    val label: String,
    val valuePercent: Float // 0.0f to 1.0f
)

@Composable
fun SkillRadarChart(
    axes: List<RadarAxis>,
    modifier: Modifier = Modifier,
    fillColor: Color = NeonGlowCyan,
    strokeColor: Color = Cyan400,
    gridColor: Color = Slate700,
    textColor: Color = Color.White
) {
    if (axes.size < 3) return

    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(axes) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800)
        )
    }

    Box(
        modifier = modifier.size(280.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val maxRadius = (minOf(size.width, size.height) / 2f) * 0.78f
            val count = axes.size
            val angleStep = (2 * PI / count).toFloat()

            // Draw concentric background web (4 levels: 25%, 50%, 75%, 100%)
            for (level in 1..4) {
                val radius = maxRadius * (level / 4f)
                val webPath = Path()
                for (i in 0 until count) {
                    val angle = -PI.toFloat() / 2f + (i * angleStep)
                    val x = center.x + radius * cos(angle)
                    val y = center.y + radius * sin(angle)
                    if (i == 0) webPath.moveTo(x, y) else webPath.lineTo(x, y)
                }
                webPath.close()
                drawPath(
                    path = webPath,
                    color = gridColor.copy(alpha = if (level == 4) 0.5f else 0.25f),
                    style = Stroke(width = if (level == 4) 1.5f else 1f)
                )
            }

            // Draw spoke lines from center to outer vertex
            for (i in 0 until count) {
                val angle = -PI.toFloat() / 2f + (i * angleStep)
                val outerX = center.x + maxRadius * cos(angle)
                val outerY = center.y + maxRadius * sin(angle)
                drawLine(
                    color = gridColor.copy(alpha = 0.4f),
                    start = center,
                    end = Offset(outerX, outerY),
                    strokeWidth = 1f
                )

                // Draw label at spoke tips
                val labelDistance = maxRadius + 24f
                val labelX = center.x + labelDistance * cos(angle)
                val labelY = center.y + labelDistance * sin(angle)

                drawContext.canvas.nativeCanvas.apply {
                    val paint = android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 22f
                        isAntiAlias = true
                        textAlign = when {
                            cos(angle) > 0.2f -> android.graphics.Paint.Align.LEFT
                            cos(angle) < -0.2f -> android.graphics.Paint.Align.RIGHT
                            else -> android.graphics.Paint.Align.CENTER
                        }
                    }
                    drawText(axes[i].label, labelX, labelY + 8f, paint)
                }
            }

            // Draw animated polygon for data values
            val polyPath = Path()
            val pointCoords = mutableListOf<Offset>()

            for (i in 0 until count) {
                val angle = -PI.toFloat() / 2f + (i * angleStep)
                val currentRadius = maxRadius * (axes[i].valuePercent * animatedProgress.value)
                val x = center.x + currentRadius * cos(angle)
                val y = center.y + currentRadius * sin(angle)
                pointCoords.add(Offset(x, y))
                if (i == 0) polyPath.moveTo(x, y) else polyPath.lineTo(x, y)
            }
            polyPath.close()

            // Fill polygon with neon glowing gradient
            drawPath(
                path = polyPath,
                color = fillColor,
                style = Fill
            )

            // Outline polygon
            drawPath(
                path = polyPath,
                color = strokeColor,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )

            // Draw points at vertices
            pointCoords.forEach { pt ->
                drawCircle(
                    color = Color.White,
                    radius = 4.dp.toPx(),
                    center = pt
                )
                drawCircle(
                    color = strokeColor,
                    radius = 2.dp.toPx(),
                    center = pt
                )
            }
        }
    }
}
