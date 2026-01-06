package widget

import action.StateAction
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bean.UserInfo
import data.store
import state.UIState
import viewmodel.MessageViewModel

@Composable
fun MessageList(viewModel: MessageViewModel, index: Int) {

    val uiState by viewModel.uiState.collectAsState()
    val userInfo: UserInfo? by store.updates.collectAsState(UserInfo())

    if (index == 0) {
        viewModel.getMessageUnread()
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
                    viewModel.dispatch(StateAction.FetchData)
                })
            }

            is UIState.Success -> {
                if (viewModel.messageUnReadList.isNullOrEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize().clickable {
                        }
                    ) {
                        Text(text = if (userInfo != null) "没有未读消息" else "用户未登录", textAlign = TextAlign.Center)
                    }

                } else {
                    LazyColumn() {
                        itemsIndexed(viewModel.messageUnReadList, key = { index, item -> "${item.id}_${index}" }) { index, messageItem ->
                            MessageView(messageItem = messageItem)
                        }
                    }
                }

            }
        }

    } else if (index == 1) {
        viewModel.getMessageRead(viewModel.currentPage.value)
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
                    viewModel.dispatch(StateAction.FetchData)
                })
            }

            is UIState.Success -> {
                if (viewModel.messageReadList.isNullOrEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize().clickable {

                        }
                    ) {
                        Text(
                            text = if (userInfo != null) "没有已读消息" else "用户未登录",
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn {
                        itemsIndexed(viewModel.messageReadList, key = { index, item -> "${item.id}_${index}" }) { index, messageItem ->
                            MessageView(messageItem = messageItem)
                        }
                    }
                }

            }
        }

    }
}