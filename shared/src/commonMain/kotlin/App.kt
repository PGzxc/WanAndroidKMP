import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.main.NaviMainScreen
import ui.theme.AppTheme

@Composable
fun App(darkTheme:Boolean,dynamicColor:Boolean) {
    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
    ) {
        Navigator(
            screen = NaviMainScreen
        )
    }

//    MaterialTheme {
//        Navigator(screen = NaviMainScreen) { navigator ->
//            FadeTransition(navigator)
//            //SlideTransition(navigator)
//        }
//    }
}

expect fun getPlatformName(): String