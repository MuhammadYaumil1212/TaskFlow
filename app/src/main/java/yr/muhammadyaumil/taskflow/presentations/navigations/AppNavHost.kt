package yr.muhammadyaumil.taskflow.presentations.navigations

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.bottomNavItems
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.components.BottomNavBar
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.trackerRoute
import yr.muhammadyaumil.taskflow.presentations.ui.profile.profileRoute
import yr.muhammadyaumil.taskflow.presentations.ui.settings.settingsRoute
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.signInRoute
import yr.muhammadyaumil.taskflow.presentations.ui.signUp.signUpRoute


@Composable
fun AppNavHost(modifier: Modifier = Modifier, startDestination: NavKey) {
    val backStack = rememberNavBackStack(startDestination)
    val currentRoute = backStack.lastOrNull()
    val showBottomBar = currentRoute in bottomNavItems.map { it.route }
    Scaffold(bottomBar = {
        if (showBottomBar) {
            BottomNavBar(
                items = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = { selectedRoute ->
                    if (currentRoute != selectedRoute) {
                        backStack.clear()
                        backStack.add(selectedRoute)
                    }
                }
            )
        }
    }) { innerPadding ->
        NavDisplay(
            modifier = modifier.padding(innerPadding),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                signInRoute(backStack)
                signUpRoute(backStack)
                trackerRoute(backStack)
                profileRoute(backStack)
                settingsRoute(backStack)

            }
        )
    }
}