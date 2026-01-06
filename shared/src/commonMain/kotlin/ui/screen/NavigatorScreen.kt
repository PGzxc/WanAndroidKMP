package ui.screen

import action.StateAction
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import state.UIState
import viewmodel.NavigatorViewModel
import widget.ListTitle
import widget.NavigatorItem
import widget.TitleBar


//object NavigatorScreen :Screen{
//    @Composable
//    override fun Content() {
//       val navigatorViewModel: NavigatorViewModel = NavigatorViewModel()
//    }
//
//}

@Composable
fun NavigatorScreen(navigatorViewModel: NavigatorViewModel = NavigatorViewModel()) {
    val uiState by navigatorViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {

    }

    when (uiState) {
        is UIState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is UIState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text((uiState as UIState.Error).message, modifier = Modifier.clickable {
                navigatorViewModel.dispatch(StateAction.FetchData)
            })
        }
        is UIState.Success -> {
            LazyColumn {
                item {
                    TitleBar("导航")
                }
                navigatorViewModel.treeItemList.forEachIndexed {
                    index, treeBean ->
                    item(key = "title_${treeBean.id}_${index}") {
                        ListTitle(title = treeBean.name ?: "标题")
                    }
                    item(key = "navigator_${treeBean.id}_${index}") {
                        NavigatorItem(treeBean, onSelect = { parent -> })
                    }
                }
            }
        }
        else -> {}
    }
}