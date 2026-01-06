package ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface WebViewNavigator {
    fun goBack()
    fun canGoBack(): Boolean
}

@Composable
expect fun WebView(
    url: String,
    modifier: Modifier = Modifier,
    onPageStarted: (() -> Unit)? = null,
    onPageFinished: (() -> Unit)? = null,
    onProgressChanged: ((progress: Int) -> Unit)? = null,
    onTitleChanged: ((title: String) -> Unit)? = null,
    onWebViewCreated: ((WebViewNavigator) -> Unit)? = null
)