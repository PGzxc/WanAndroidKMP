package ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.core.screen.Screen
import ui.widget.WebView
import widget.TitleBar

class WebViewScreen(private val url: String, private val title: String = "") : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val currentTitle = remember { mutableStateOf(title) }
        val isLoading = remember { mutableStateOf(true) }
        val progress = remember { mutableStateOf(0.5f) }
        val webViewNavigator = remember { mutableStateOf<ui.widget.WebViewNavigator?>(null) }
        
        Column(modifier = Modifier.fillMaxSize()) {
            TitleBar(
                title = currentTitle.value,
                leftIcon = Icons.Default.ArrowBackIos,
                leftCallBack = {
                    if (webViewNavigator.value?.canGoBack() == true) {
                        webViewNavigator.value?.goBack()
                    } else {
                        navigator?.pop()
                    }
                }
            )
            
            if (isLoading.value) {
                LinearProgressIndicator(
                    progress = { progress.value },
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White
                )
            }
            
            WebView(
                url = url,
                modifier = Modifier.fillMaxSize(),
                onPageStarted = {
                    isLoading.value = true
                },
                onPageFinished = {
                    isLoading.value = false
                },
                onProgressChanged = {
                    progress.value = it.toFloat() / 100f
                },
                onTitleChanged = {
                    if (currentTitle.value.isEmpty()) {
                        currentTitle.value = it
                    }
                },
                onWebViewCreated = {
                    webViewNavigator.value = it
                }
            )
        }
    }
}