package yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yr.muhammadyaumil.taskflow.core.color.toComposeColor
import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto
import yr.muhammadyaumil.taskflow.presentations.components.LoadingSpinner
import yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits.components.HabitItem
import yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits.components.Header
import yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits.components.SubHeaderAllHabit
import yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits.components.SubHeaderNowHabit

@Composable
fun ManageHabits(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    todayHabitList: List<HabitDto>,
    allHabitList: List<HabitDto>
) {
    Scaffold(modifier = modifier) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 100.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Header()
                Spacer(modifier = Modifier.height(15.dp))
            }
            if (todayHabitList.isNotEmpty()) {
                item {
                    SubHeaderNowHabit()
                    Spacer(modifier = Modifier.height(15.dp))
                }

                items(
                    items = todayHabitList,
                    key = { habit -> "today_${habit.id}" }
                ) { habit ->
                    HabitItem(
                        title = habit.name,
                        subtitle = habit.notes,
                        color = habit.categoryHex.toComposeColor(
                            defaultColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }

                item { Spacer(modifier = Modifier.height(15.dp)) }
            }

            item {
                SubHeaderAllHabit()
                Spacer(modifier = Modifier.height(15.dp))
            }

            items(
                items = allHabitList,
                key = { habit -> "all_${habit.id}" }) { habit ->
                HabitItem(
                    title = habit.name,
                    subtitle = habit.notes,
                    color = habit.categoryHex.toComposeColor(
                        defaultColor = MaterialTheme.colorScheme.surface
                    )
                )
            }

        }
    }
    if (isLoading) {
        LoadingSpinner()
    }
}


