package yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

@Composable
fun FloatingContainer(
    modifier: Modifier = Modifier,
    content: @Composable FloatingBubbleScope.() -> Unit
) {
    var containerSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    var firstPosition by remember {
        mutableStateOf<FloatingPosition?>(null)
    }

    var secondPosition by remember {
        mutableStateOf<FloatingPosition?>(null)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { containerSize = it.size }
    ) {
        FloatingBubbleScope(
            containerSize = containerSize,
            firstPosition = firstPosition,
            secondPosition = secondPosition,
            onFirstPositionChanged = { firstPosition = it },
            onSecondPositionChanged = { secondPosition = it }
        ).content()
    }
}