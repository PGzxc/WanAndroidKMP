package ui.widget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.NativeViewHost
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.QuartzCore.CALayer
import platform.QuartzCore.CATransaction
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKPreferences
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.WKWebViewNavigationType
import platform.WebKit.WKWebViewNavigationTypeLinkActivated
import platform.WebKit.WKWebViewNavigationTypeFormSubmitted
import platform.WebKit.WKWebViewNavigationTypeBackForward
import platform.WebKit.WKWebViewNavigationTypeReload
import platform.WebKit.WKWebViewNavigationTypeFormResubmitted
import platform.WebKit.WKWebViewNavigationTypeOther
import platform.WebKit.WKWebsiteDataStore
import platform.objc.sel_registerName
import platform.objc.objc_getAssociatedObject
import platform.objc.objc_setAssociatedObject
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
    val webView = remember {
        val configuration = WKWebViewConfiguration().apply {
            preferences = WKPreferences().apply {
                setJavaScriptEnabled(true)
            }
            websiteDataStore = WKWebsiteDataStore.defaultDataStore()
        }
        WKWebView(frame = platform.CoreGraphics.CGRectZero(), configuration = configuration)
    }

    // Set up navigation delegate
    webView.navigationDelegate = object : WKNavigationDelegateProtocol {
        override fun webView(
            webView: WKWebView,
            didStartProvisionalNavigation: platform.WebKit.WKNavigation?
        ) {
            onPageStarted?.invoke()
        }

        override fun webView(
            webView: WKWebView,
            didFinishNavigation: platform.WebKit.WKNavigation?
        ) {
            onPageFinished?.invoke()
            onTitleChanged?.invoke(webView.title ?: "")
        }

        override fun webView(
            webView: WKWebView,
            didReceiveServerRedirectForProvisionalNavigation: platform.WebKit.WKNavigation?
        ) {
            // Not implemented
        }

        override fun webView(
            webView: WKWebView,
            didFailProvisionalNavigation: platform.WebKit.WKNavigation?,
            withError: platform.WebKit.WKError
        ) {
            onPageFinished?.invoke()
        }

        override fun webView(
            webView: WKWebView,
            didFailNavigation: platform.WebKit.WKNavigation?,
            withError: platform.WebKit.WKError
        ) {
            onPageFinished?.invoke()
        }

        override fun webView(
            webView: WKWebView,
            decidePolicyForNavigationAction: platform.WebKit.WKNavigationAction,
            decisionHandler: (platform.WebKit.WKNavigationActionPolicy) -> Unit
        ) {
            decisionHandler(platform.WebKit.WKNavigationActionPolicyAllow)
        }

        override fun webView(
            webView: WKWebView,
            decidePolicyForNavigationResponse: platform.WebKit.WKNavigationResponse,
            decisionHandler: (platform.WebKit.WKNavigationResponsePolicy) -> Unit
        ) {
            decisionHandler(platform.WebKit.WKNavigationResponsePolicyAllow)
        }
    }
    
    // Create navigator and call onWebViewCreated
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

    // Update URL when it changes
    DisposableEffect(url) {
        val nsUrl = NSURL.URLWithString(url)
        val request = NSURLRequest.requestWithURL(nsUrl)
        webView.loadRequest(request)

        onDispose {}
    }

    NativeViewHost(
        viewBlock = { webView },
        modifier = modifier.fillMaxSize()
    )
}


