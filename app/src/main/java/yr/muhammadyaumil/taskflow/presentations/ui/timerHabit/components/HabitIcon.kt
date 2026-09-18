package yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import yr.muhammadyaumil.taskflow.core.color.toComposeColor

@Composable
fun HabitIcon(
    modifier: Modifier = Modifier,
    colorIcons: String,
    isRunning: Boolean,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "habitRipple")
    val rippleProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1800,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rippleProgress"
    )
    val circleScale by animateFloatAsState(
        targetValue = if (isRunning) 0f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "circleScale"
    )

    Box(
        modifier = modifier.size(360.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isRunning) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val maxRadius = size.minDimension / 2f
                val radius = maxRadius * rippleProgress
                val alpha = (1f - rippleProgress) * 0.55f
                drawCircle(
                    color = colorIcons.toComposeColor(),
                    radius = radius,
                    alpha = alpha,
                    style = Stroke(
                        width = 50.dp.toPx()
                    )
                )
            }

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val maxRadius = size.minDimension / 2f
                val delayedProgress = (rippleProgress + 0.5f) % 1f
                val radius = maxRadius * delayedProgress
                val alpha = (1f - delayedProgress) * 0.45f
                drawCircle(
                    color = colorIcons.toComposeColor(),
                    radius = radius,
                    alpha = alpha,
                    style = Stroke(
                        width = 50.dp.toPx()
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .size(230.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                if (!isRunning) {
                    drawCircle(
                        color = colorIcons.toComposeColor().copy(
                            alpha = 0.5f
                        ),
                        radius = size.minDimension / 2f * circleScale
                    )
                }
                drawCircle(
                    color = colorIcons.toComposeColor(),
                    radius = size.minDimension * 0.39f
                )
            }

            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = "Check Icon",
                modifier = Modifier.size(92.dp),
                tint = Color.White
            )
        }
    }
}