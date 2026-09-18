package yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun FloatingBubbleItem(
    modifier: Modifier = Modifier,
    containerSize: IntSize,
    otherPosition: FloatingPosition?,
    bottomPadding: androidx.compose.ui.unit.Dp,
    onPositionChanged: (FloatingPosition) -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val density = LocalDensity.current
    val edgePaddingPx = with(density) { 20.dp.roundToPx() }
    val spacingPx = with(density) { 20.dp.roundToPx() }
    val bottomPaddingPx = with(density) { bottomPadding.roundToPx() }
    var itemSize by remember { mutableStateOf(IntSize.Zero) }
    var position by remember { mutableStateOf(IntOffset.Zero) }
    var initialized by remember { mutableStateOf(false) }

    val animatedX by animateFloatAsState(
        targetValue = position.x.toFloat(),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "floatingX"
    )

    val animatedY by animateFloatAsState(
        targetValue = position.y.toFloat(),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "floatingY"
    )

    if (!initialized && containerSize != IntSize.Zero && itemSize != IntSize.Zero) {
        val defaultPosition =
            CalculateFloating.getSafeDefaultPosition(
                containerSize = containerSize,
                itemSize = itemSize,
                edgePaddingPx = edgePaddingPx,
                bottomPaddingPx = bottomPaddingPx,
                spacingPx = spacingPx,
                otherPosition = otherPosition
            )
        position = defaultPosition
        initialized = true
        onPositionChanged(
            CalculateFloating.createPosition(
                x = defaultPosition.x,
                y = defaultPosition.y,
                itemSize = itemSize
            )
        )
    }


    Box(
        modifier = modifier
            .offset {
                IntOffset(
                    animatedX.roundToInt(),
                    animatedY.roundToInt()
                )
            }
            .onGloballyPositioned { itemSize = it.size }
            .pointerInput(
                containerSize,
                itemSize,
                otherPosition
            ) {
                detectDragGestures(
                    onDragEnd = {
                        val snapped =
                            CalculateFloating.snapPosition(
                                currentPosition = position,
                                containerSize = containerSize,
                                itemSize = itemSize,
                                edgePaddingPx = edgePaddingPx,
                                bottomPaddingPx = bottomPaddingPx,
                                spacingPx = spacingPx,
                                otherPosition = otherPosition
                            )
                        position = snapped
                        onPositionChanged(
                            CalculateFloating.createPosition(
                                x = snapped.x,
                                y = snapped.y,
                                itemSize = itemSize
                            )
                        )
                    }
                ) { change, dragAmount ->
                    change.consume()
                    val newPosition =
                        CalculateFloating.calculateDragPosition(
                            currentPosition = position,
                            dragX = dragAmount.x,
                            dragY = dragAmount.y,
                            containerSize = containerSize,
                            itemSize = itemSize,
                            edgePaddingPx = edgePaddingPx,
                            spacingPx = spacingPx,
                            otherPosition = otherPosition
                        )

                    if (newPosition != position) {
                        position = newPosition
                        onPositionChanged(
                            CalculateFloating.createPosition(
                                x = newPosition.x,
                                y = newPosition.y,
                                itemSize = itemSize
                            )
                        )
                    }
                }
            }
    ) {
        content()
    }
}
