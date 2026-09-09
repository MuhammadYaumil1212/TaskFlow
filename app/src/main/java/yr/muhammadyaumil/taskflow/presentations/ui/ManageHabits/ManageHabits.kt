package yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ManageHabits(modifier: Modifier = Modifier) {
    Scaffold { innerPadding ->
        LazyColumn(modifier = modifier.padding(innerPadding)) {
            item {
                Text("Habits Manage")
            }
        }
    }
}