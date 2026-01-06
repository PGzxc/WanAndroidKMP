package ui.widget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
actual fun WebView(
    url: String,
    modifier: Modifier,
    onPageStarted: (() -> Unit)?,
    onPageFinished: (() -> Unit)?,
    onProgressChanged: ((progress: Int) -> Unit)?,
    onTitleChanged: ((title: String) -> Unit)?,
    onWebViewCreated: ((WebViewNavigator) -> Unit)?
) {
    // Create navigator and call onWebViewCreated
    val navigator = object : WebViewNavigator {
        override fun goBack() {
            // Desktop placeholder - no navigation history
        }
        
        override fun canGoBack(): Boolean {
            // Desktop placeholder - no navigation history
            return false
        }
    }
    
    onWebViewCreated?.invoke(navigator)
    
    // Simulate loading
    onPageStarted?.invoke()
    onProgressChanged?.invoke(100)
    onTitleChanged?.invoke("WebView - Desktop")
    onPageFinished?.invoke()
    
    Text(
        text = "WebView (Desktop) will load: $url",
        modifier = modifier.fillMaxSize(),
        color = Color.White
    )
}


