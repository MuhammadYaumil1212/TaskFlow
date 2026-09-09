package yr.muhammadyaumil.taskflow.presentations.navigations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.AddTrackerRoute
import yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.addTrackerRoute
import yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits.habitsRoute
import yr.muhammadyaumil.taskflow.presentations.ui.ManageTasks.tasksRoute
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
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    modifier = Modifier.navigationBarsPadding(),
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
        },
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    onClick = {
                        backStack.add(AddTrackerRoute)
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah"
                    )
                }
            }
        },
    ) { _ ->
        NavDisplay(
            modifier = modifier.fillMaxSize(),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(500)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { -it / 4 },
                    animationSpec = tween(500),
                )
            },
            popTransitionSpec = {
                fadeIn(tween(500)) + slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(500),
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(500),
                ) + fadeOut(tween(500))
            },
            predictivePopTransitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(500),
                    initialOffsetX = { -it / 4 },
                ) togetherWith slideOutHorizontally(
                    animationSpec = tween(500),
                    targetOffsetX = { it },
                )
            },
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
                addTrackerRoute(backStack)
                tasksRoute(backStack)
                habitsRoute(backStack)
            }
        )
    }
}