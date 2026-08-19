package yr.muhammadyaumil.taskflow.presentations.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.SignInRoute
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.signInRoute
import yr.muhammadyaumil.taskflow.presentations.ui.signUp.signUpRoute

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(SignInRoute)
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            signInRoute(backStack)
            signUpRoute(backStack)
        }
    )
}