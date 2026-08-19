package yr.muhammadyaumil.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import yr.muhammadyaumil.taskflow.presentations.navigations.AppNavHost
import yr.muhammadyaumil.taskflow.presentations.theme.TaskFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { TaskFlowTheme { AppNavHost(modifier = Modifier.systemBarsPadding()) } }
    }
}