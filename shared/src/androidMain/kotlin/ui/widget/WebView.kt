package ui.widget

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
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
    AndroidView(
        factory = {
            val webView = WebView(it).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        onPageStarted?.invoke()
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        onPageFinished?.invoke()
                    }
                }

                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.allowContentAccess = true
                settings.allowFileAccess = true

                this.setOnProgressChangeListener(onProgressChanged, onTitleChanged)
            }
            
            val navigator = object : WebViewNavigator {
                override fun goBack() {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    }
                }
                
                override fun canGoBack(): Boolean {
                    return webView.canGoBack()
                }
            }
            
            onWebViewCreated?.invoke(navigator)
            webView
        },
        update = {
            it.loadUrl(url)
        },
        modifier = modifier.fillMaxSize()
    )
}


private fun WebView.setOnProgressChangeListener(
    progressListener: ((progress: Int) -> Unit)?,
    titleListener: ((title: String) -> Unit)?
) {
    webChromeClient = object : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressListener?.invoke(newProgress)
        }
        
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            title?.let { titleListener?.invoke(it) }
        }
    }
}