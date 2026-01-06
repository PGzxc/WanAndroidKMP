package ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch
import viewmodel.MessageViewModel
import widget.MessageList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageScreen(viewModel: MessageViewModel = MessageViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val messageList = viewModel.messageTitle
    val pagerState = rememberPagerState(pageCount = { messageList.size })

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        //TitleBar(title = "消息")
        TabRow(
            //modifier =  Modifier.fillMaxWidth().height(54.dp),
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Blue,
            contentColor = Color.Red
        ) {
            messageList.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = if (pagerState.currentPage == index) Color.Red else Color.White
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            key = { messageList[it] }) { pageIndex ->
            MessageList(viewModel = viewModel, index = pageIndex)
        }
    }
}