package yr.muhammadyaumil.taskflow.presentations.ui.manageHabits.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Header(modifier: Modifier = Modifier) {
    Text(
        text = "Habit Anda",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}