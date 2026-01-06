package ui.screen

import action.StateAction
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import viewmodel.HomeViewModel
import widget.ArticleListItem
import widget.Banner
import widget.TitleBar


@Composable
fun HomeScreen(homeViewModel: HomeViewModel = HomeViewModel()) {
    val uiState by homeViewModel.uiState.collectAsState()

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
                homeViewModel.dispatch(StateAction.FetchData)
            })
        }

        is UIState.Success -> {

            LazyColumn {
                item(key = "title_bar") {
                    TitleBar("首页", rightIcon = Icons.Default.Search, rightCallBack = {

                    })
                }
                item(key = "banner") {
                    Banner(list = homeViewModel.bannerList.toList(), onClick = { link, title ->

                    })
                }
                itemsIndexed(homeViewModel.articleList, key = { index, item -> "${item.id}_${index}" }) { index, item ->
                    ArticleListItem(item = item, itemClick = {

                    }) {

                    }
                }
            }
        }
    }

}