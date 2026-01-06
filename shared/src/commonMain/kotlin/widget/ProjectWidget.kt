package widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bean.ProjectBean
import kotlinx.coroutines.launch
import viewmodel.ProjectViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectWidget(viewModel: ProjectViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val projectBeanList: List<ProjectBean> = viewModel.projectListData
    val pagerState = rememberPagerState { if (projectBeanList.isNotEmpty()) projectBeanList.size else 1 }

    Column(modifier = Modifier.fillMaxSize()) {
        if (projectBeanList.isNotEmpty()) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ) {
                projectBeanList.forEachIndexed { index, title ->
                    Tab(text = { Text(text = title.name, style = MaterialTheme.typography.titleMedium) },
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(0.dp), modifier = Modifier.weight(1f).fillMaxWidth(),
                key = { "project_page_${projectBeanList[it].id}" }
            ) { pageIndex ->
                ProjectChileScreen(viewModel = viewModel, cid = projectBeanList[pageIndex].id)
            }
        }else{
            Card {
                Box(Modifier.fillMaxSize()) {
                    Text(text = "暂无数据", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
                }
            }
        }
    }
}

