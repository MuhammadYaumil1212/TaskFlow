package yr.muhammadyaumil.taskflow.core.color

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

fun String?.toComposeColor(defaultColor: Color = Color.White): Color {
    if (this.isNullOrBlank()) return defaultColor
    return try {
        val hexString = if (!this.startsWith("#")) "#$this" else this
        Color(hexString.toColorInt())
    } catch (e: Exception) {
        defaultColor
    }
}