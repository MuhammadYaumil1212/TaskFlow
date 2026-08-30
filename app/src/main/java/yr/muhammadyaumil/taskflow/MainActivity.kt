package yr.muhammadyaumil.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import yr.muhammadyaumil.taskflow.presentations.navigations.AppNavHost
import yr.muhammadyaumil.taskflow.presentations.theme.TaskFlowTheme
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.TrackerRoute
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.SignInRoute
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.SignInViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskFlowTheme {
                val viewModel: SignInViewModel = hiltViewModel()
                val isLoggedIn by viewModel.isSessionActive.collectAsStateWithLifecycle()
                val initialRoute = if (isLoggedIn) TrackerRoute else SignInRoute
                AppNavHost(
                    modifier = Modifier.systemBarsPadding(),
                    startDestination = initialRoute
                )
            }
        }
    }
}