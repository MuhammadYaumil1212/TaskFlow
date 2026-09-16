package yr.muhammadyaumil.taskflow.presentations.ui.tracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import yr.muhammadyaumil.taskflow.presentations.ui.manageHabits.HabitsRoute
import yr.muhammadyaumil.taskflow.presentations.ui.reports.ReportsRoute
import yr.muhammadyaumil.taskflow.presentations.ui.settings.SettingsRoute

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: NavKey
)

val bottomNavItems = listOf(
    BottomNavItem("Hari ini", Icons.Default.Home, TrackerRoute),
    BottomNavItem("Habits", Icons.Default.TrackChanges, HabitsRoute),
    BottomNavItem("Reports", Icons.Default.Leaderboard, ReportsRoute),
    BottomNavItem("Settings", Icons.Default.Settings, SettingsRoute),
)
