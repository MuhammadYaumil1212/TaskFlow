package yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

class FloatingBubbleScope(
    val containerSize: IntSize,
    val firstPosition: FloatingPosition?,
    val secondPosition: FloatingPosition?,
    val onFirstPositionChanged: (FloatingPosition) -> Unit,
    val onSecondPositionChanged: (FloatingPosition) -> Unit
) {
    @Composable
    fun First(
        modifier: Modifier = Modifier,
        content: @Composable BoxScope.() -> Unit
    ) {
        FloatingBubbleItem(
            modifier = modifier,
            containerSize = containerSize,
            otherPosition = secondPosition,
            bottomPadding = 220.dp,
            onPositionChanged = onFirstPositionChanged,
            content = content
        )
    }

    @Composable
    fun Second(
        modifier: Modifier = Modifier,
        content: @Composable BoxScope.() -> Unit
    ) {
        FloatingBubbleItem(
            modifier = modifier,
            containerSize = containerSize,
            otherPosition = firstPosition,
            bottomPadding = 140.dp,
            onPositionChanged = onSecondPositionChanged,
            content = content
        )
    }
}