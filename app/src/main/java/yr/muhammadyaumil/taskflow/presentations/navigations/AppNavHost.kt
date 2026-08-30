package yr.muhammadyaumil.taskflow.presentations.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.trackerRoute
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.signInRoute
import yr.muhammadyaumil.taskflow.presentations.ui.signUp.signUpRoute


@Composable
fun AppNavHost(modifier: Modifier = Modifier, startDestination: NavKey) {
    val backStack = rememberNavBackStack(startDestination)
    NavDisplay(
        modifier = modifier,
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
        }
    )
}