package yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.sqrt

object CalculateFloating {
    fun createPosition(
        x: Int,
        y: Int,
        itemSize: IntSize
    ): FloatingPosition {
        return FloatingPosition(
            x = x.toFloat(),
            y = y.toFloat(),
            width = itemSize.width,
            height = itemSize.height
        )
    }

    fun isOverlapping(
        first: FloatingPosition,
        second: FloatingPosition,
        spacingPx: Int
    ): Boolean {
        val firstLeft = first.x - spacingPx
        val firstRight = first.x + first.width + spacingPx
        val firstTop = first.y - spacingPx
        val firstBottom = first.y + first.height + spacingPx
        val secondLeft = second.x
        val secondRight = second.x + second.width
        val secondTop = second.y
        val secondBottom = second.y + second.height

        return firstLeft < secondRight
                && firstRight > secondLeft
                && firstTop < secondBottom
                && firstBottom > secondTop
    }

    fun isValidPosition(
        x: Int,
        y: Int,
        containerSize: IntSize,
        itemSize: IntSize,
        edgePaddingPx: Int,
        spacingPx: Int,
        otherPosition: FloatingPosition?
    ): Boolean {
        if (itemSize == IntSize.Zero) return false

        val maxX =
            (containerSize.width - itemSize.width - edgePaddingPx).coerceAtLeast(edgePaddingPx)

        val maxY =
            (containerSize.height - itemSize.height - edgePaddingPx).coerceAtLeast(edgePaddingPx)

        if (x !in edgePaddingPx..maxX) return false
        if (y !in edgePaddingPx..maxY) return false

        val newPosition =
            createPosition(x = x, y = y, itemSize = itemSize)

        return otherPosition == null || !isOverlapping(
            first = newPosition,
            second = otherPosition,
            spacingPx = spacingPx
        )
    }

    fun getDefaultPosition(
        containerSize: IntSize,
        itemSize: IntSize,
        edgePaddingPx: Int,
        bottomPaddingPx: Int
    ): IntOffset {

        if (containerSize == IntSize.Zero || itemSize == IntSize.Zero) return IntOffset.Zero

        val x =
            (containerSize.width - itemSize.width - edgePaddingPx).coerceAtLeast(
                edgePaddingPx
            )

        val maxY =
            (containerSize.height - itemSize.height - edgePaddingPx).coerceAtLeast(
                edgePaddingPx
            )

        val y =
            (containerSize.height - itemSize.height - bottomPaddingPx).coerceIn(
                edgePaddingPx,
                maxY
            )

        return IntOffset(x = x, y = y)
    }

    fun getSafeDefaultPosition(
        containerSize: IntSize,
        itemSize: IntSize,
        edgePaddingPx: Int,
        bottomPaddingPx: Int,
        spacingPx: Int,
        otherPosition: FloatingPosition?
    ): IntOffset {

        val defaultPosition =
            getDefaultPosition(
                containerSize = containerSize,
                itemSize = itemSize,
                edgePaddingPx = edgePaddingPx,
                bottomPaddingPx = bottomPaddingPx
            )

        if (
            isValidPosition(
                x = defaultPosition.x,
                y = defaultPosition.y,
                containerSize = containerSize,
                itemSize = itemSize,
                edgePaddingPx = edgePaddingPx,
                spacingPx = spacingPx,
                otherPosition = otherPosition
            )
        ) {
            return defaultPosition
        }

        if (otherPosition != null) {

            val aboveY =
                otherPosition.y.toInt() -
                        itemSize.height -
                        spacingPx

            if (isValidPosition(
                    x = defaultPosition.x,
                    y = aboveY,
                    containerSize = containerSize,
                    itemSize = itemSize,
                    edgePaddingPx = edgePaddingPx,
                    spacingPx = spacingPx,
                    otherPosition = otherPosition
                )
            ) {
                return IntOffset(
                    x = defaultPosition.x,
                    y = aboveY
                )
            }

            val belowY =
                otherPosition.y.toInt() +
                        otherPosition.height +
                        spacingPx

            if (
                isValidPosition(
                    x = defaultPosition.x,
                    y = belowY,
                    containerSize = containerSize,
                    itemSize = itemSize,
                    edgePaddingPx = edgePaddingPx,
                    spacingPx = spacingPx,
                    otherPosition = otherPosition
                )
            ) {
                return IntOffset(
                    x = defaultPosition.x,
                    y = belowY
                )
            }

            val leftX =
                (
                        otherPosition.x.toInt() -
                                itemSize.width -
                                spacingPx
                        ).coerceAtLeast(
                        edgePaddingPx
                    )

            if (
                isValidPosition(
                    x = leftX,
                    y = defaultPosition.y,
                    containerSize = containerSize,
                    itemSize = itemSize,
                    edgePaddingPx = edgePaddingPx,
                    spacingPx = spacingPx,
                    otherPosition = otherPosition
                )
            ) {
                return IntOffset(
                    x = leftX,
                    y = defaultPosition.y
                )
            }
        }

        return defaultPosition
    }

    fun calculateDragPosition(
        currentPosition: IntOffset,
        dragX: Float,
        dragY: Float,
        containerSize: IntSize,
        itemSize: IntSize,
        edgePaddingPx: Int,
        spacingPx: Int,
        otherPosition: FloatingPosition?
    ): IntOffset {

        if (containerSize == IntSize.Zero || itemSize == IntSize.Zero) return currentPosition

        val maxX =
            (containerSize.width - itemSize.width - edgePaddingPx).coerceAtLeast(
                edgePaddingPx
            )

        val maxY =
            (containerSize.height - itemSize.height - edgePaddingPx).coerceAtLeast(
                edgePaddingPx
            )

        val newX =
            (currentPosition.x + dragX.toInt()).coerceIn(
                edgePaddingPx,
                maxX
            )

        val newY =
            (currentPosition.y + dragY.toInt()).coerceIn(
                edgePaddingPx,
                maxY
            )

        return if (
            isValidPosition(
                x = newX,
                y = newY,
                containerSize = containerSize,
                itemSize = itemSize,
                edgePaddingPx = edgePaddingPx,
                spacingPx = spacingPx,
                otherPosition = otherPosition
            )
        ) {
            IntOffset(
                x = newX,
                y = newY
            )
        } else {
            currentPosition
        }
    }

    fun snapPosition(
        currentPosition: IntOffset,
        containerSize: IntSize,
        itemSize: IntSize,
        edgePaddingPx: Int,
        bottomPaddingPx: Int,
        spacingPx: Int,
        otherPosition: FloatingPosition?
    ): IntOffset {
        if (containerSize == IntSize.Zero || itemSize == IntSize.Zero) return currentPosition

        val maxX = (containerSize.width - itemSize.width - edgePaddingPx)
            .coerceAtLeast(
                edgePaddingPx
            )
        val maxY = (containerSize.height - itemSize.height - edgePaddingPx)
            .coerceAtLeast(
                edgePaddingPx
            )

        val centerX = containerSize.width / 2f
        val centerY = containerSize.height / 2f
        val currentCenterX = currentPosition.x + itemSize.width / 2f
        val currentCenterY = currentPosition.y + itemSize.height / 2f
        val distanceFromCenter =
            sqrt(
                (currentCenterX - centerX) *
                        (currentCenterX - centerX) +
                        (currentCenterY - centerY) *
                        (currentCenterY - centerY)
            )

        val centerThreshold = minOf(containerSize.width, containerSize.height) * 0.25f

        if (distanceFromCenter < centerThreshold) {
            getSafeDefaultPosition(
                containerSize = containerSize,
                itemSize = itemSize,
                edgePaddingPx = edgePaddingPx,
                bottomPaddingPx = bottomPaddingPx,
                spacingPx = spacingPx,
                otherPosition = otherPosition
            )
        }

        val targetX = if (currentCenterX < centerX) edgePaddingPx else maxX
        val targetY = currentPosition.y.coerceIn(edgePaddingPx, maxY)

        if (isValidPosition(
                x = targetX,
                y = targetY,
                containerSize = containerSize,
                itemSize = itemSize,
                edgePaddingPx = edgePaddingPx,
                spacingPx = spacingPx,
                otherPosition = otherPosition
            )
        ) {
            return IntOffset(x = targetX, y = targetY)
        }

        if (otherPosition != null) {

            val aboveY = otherPosition.y.toInt() - itemSize.height - spacingPx

            if (isValidPosition(
                    x = targetX,
                    y = aboveY,
                    containerSize = containerSize,
                    itemSize = itemSize,
                    edgePaddingPx = edgePaddingPx,
                    spacingPx = spacingPx,
                    otherPosition = otherPosition
                )
            ) {
                return IntOffset(x = targetX, y = aboveY)
            }

            val belowY = otherPosition.y.toInt() + otherPosition.height + spacingPx

            if (isValidPosition(
                    x = targetX,
                    y = belowY,
                    containerSize = containerSize,
                    itemSize = itemSize,
                    edgePaddingPx = edgePaddingPx,
                    spacingPx = spacingPx,
                    otherPosition = otherPosition
                )
            ) {
                return IntOffset(x = targetX, y = belowY)
            }

            val oppositeX = if (targetX == edgePaddingPx) maxX else edgePaddingPx

            if (isValidPosition(
                    x = oppositeX,
                    y = targetY,
                    containerSize = containerSize,
                    itemSize = itemSize,
                    edgePaddingPx = edgePaddingPx,
                    spacingPx = spacingPx,
                    otherPosition = otherPosition
                )
            ) {
                return IntOffset(x = oppositeX, y = targetY)
            }
        }

        return IntOffset(
            x = currentPosition.x.coerceIn(edgePaddingPx, maxX),
            y = currentPosition.y.coerceIn(edgePaddingPx, maxY)
        )
    }
}