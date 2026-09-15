package yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SubHeaderAllHabit(modifier: Modifier = Modifier) {
    Text(
        text = "Semua Kegiatan",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
    )
}