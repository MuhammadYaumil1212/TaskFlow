package yr.muhammadyaumil.taskflow.presentations.ui.Tracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits.HabitsRoute
import yr.muhammadyaumil.taskflow.presentations.ui.ManageTasks.TasksRoute
import yr.muhammadyaumil.taskflow.presentations.ui.settings.SettingsRoute

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: NavKey
)

val bottomNavItems = listOf(
    BottomNavItem("Hari ini", Icons.Default.Home, TrackerRoute),
    BottomNavItem("Habits", Icons.Default.TrackChanges, HabitsRoute),
    BottomNavItem("Tasks", Icons.Default.Checklist, TasksRoute),
    BottomNavItem("Settings", Icons.Default.Settings, SettingsRoute),
)
