package widget

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.screen.WebViewScreen
import viewmodel.ProjectViewModel

@Composable
internal fun ProjectChileScreen(
    viewModel: ProjectViewModel,
    cid: Long,
) {
    val navigator = LocalNavigator.current

    LaunchedEffect(cid) {
        // 确保只处理当前页面的数据，避免多页面数据混合
        viewModel.currentPage.value = 0
        viewModel.getProjectItemListData(page = viewModel.currentPage.value, cid = cid)
    }
    val projectItemListBean = viewModel.projectItemListData
    // 调试：检查是否有重复id
    LaunchedEffect(projectItemListBean, cid) {
        println("Current cid: $cid, Item count: ${projectItemListBean.size}")
        val ids = projectItemListBean.map { it.id }
        println("All IDs: $ids")
        val uniqueIds = ids.distinct()
        if (ids.size != uniqueIds.size) {
            val duplicateIds = ids.groupBy { it }.filter { it.value.size > 1 }.keys
            println("Duplicate IDs found for cid $cid: $duplicateIds")
        }
    }

    LazyColumn {
        itemsIndexed(projectItemListBean, key = { index, item -> "${item.id}_${index}" }) { index, item ->
            ArticleListItem(item = item, itemClick = {
                navigator?.push(WebViewScreen(item.link, item.title))
            }) {

            }
        }
    }

}