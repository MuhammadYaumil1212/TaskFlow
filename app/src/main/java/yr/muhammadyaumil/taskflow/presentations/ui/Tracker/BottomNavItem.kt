package yr.muhammadyaumil.taskflow.presentations.ui.Tracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import yr.muhammadyaumil.taskflow.presentations.ui.profile.ProfileRoute
import yr.muhammadyaumil.taskflow.presentations.ui.settings.SettingsRoute

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: NavKey
)

val bottomNavItems = listOf(
    BottomNavItem("Tracker", Icons.Default.Home, TrackerRoute),
    BottomNavItem("Settings", Icons.Default.Settings, SettingsRoute),
    BottomNavItem("Profile", Icons.Default.Person, ProfileRoute)
)
