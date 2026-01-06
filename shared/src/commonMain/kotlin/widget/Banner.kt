package widget

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToDownIgnoreConsumed
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChangeConsumed
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay

/**
 * 轮播图
 * [timeMillis] 停留时间
 * [loadImage] 加载中显示的布局
 * [indicatorAlignment] 指示点的的位置,默认是轮播图下方的中间,带一点padding
 * [onClick] 轮播图点击事件
 */
@OptIn( ExperimentalFoundationApi::class)
@Composable
fun Banner(
    list: List<BannerData>?,
    timeMillis: Long = 3000,
    indicatorAlignment: Alignment = Alignment.BottomCenter,
    onClick: (link: String, title: String) -> Unit
) {

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            //.padding(16.dp)
            .height(200.dp)
    ) {

        if (list == null) {
            //加载中的图片
            Icon(
                imageVector = Icons.Default.Download, modifier = Modifier.fillMaxSize(),
                contentDescription = null,
            )
        } else {
            val pagerState = rememberPagerState(initialPage = 0)
            //监听动画执行
            var executeChangePage by remember { mutableStateOf(false) }
            var currentPageIndex = 0

            //自动滚动
            LaunchedEffect(pagerState.currentPage, executeChangePage) {
                if (pagerState.initialPage > 0) {
                    delay(timeMillis)
                    //这里直接+1就可以循环，前提是infiniteLoop == true
                    pagerState.animateScrollToPage((pagerState.currentPage + 1) % (pagerState.targetPage))
                }
            }

            HorizontalPager(pageCount = list.size, state = pagerState, modifier = Modifier
                .pointerInput(pagerState.currentPage) {
                    awaitPointerEventScope {
                        while (true) {
                            //PointerEventPass.Initial - 本控件优先处理手势，处理后再交给子组件
                            val event = awaitPointerEvent(PointerEventPass.Initial)
                            //获取到第一根按下的手指
                            val dragEvent = event.changes.firstOrNull()
                            when {
                                //当前移动手势是否已被消费
                                dragEvent!!.isConsumed -> {
                                    return@awaitPointerEventScope
                                }
                                //是否已经按下(忽略按下手势已消费标记)
                                dragEvent.changedToDownIgnoreConsumed() -> {
                                    //记录下当前的页面索引值
                                    currentPageIndex = pagerState.currentPage
                                }
                                //是否已经抬起(忽略按下手势已消费标记)
                                dragEvent.changedToUpIgnoreConsumed() -> {
                                    //当页面没有任何滚动/动画的时候pagerState.targetPage为null，这个时候是单击事件
                                    if (pagerState.targetPage == null) return@awaitPointerEventScope
                                    //当pageCount大于1，且手指抬起时如果页面没有改变，就手动触发动画
                                    if (currentPageIndex == pagerState.currentPage && pagerState.targetPage > 1) {
                                        executeChangePage = !executeChangePage
                                    }
                                }
                            }
                        }
                    }
                }
                .clickable {
                    with(list[pagerState.currentPage]) {
                        onClick.invoke(linkUrl, title)
                    }
                }
                .fillMaxSize()) { page ->
                KamelImage(
                    asyncPainterResource(list[page].imageUrl),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    onLoading = { _ ->
                        // 加载中状态，可以显示占位符
                    },
                    onFailure = { _ ->
                        // 加载失败状态，可以显示错误占位符
                    }
                )
            }
            Box(
                modifier = Modifier
                    .align(indicatorAlignment)
                    .padding(bottom = 6.dp, start = 6.dp, end = 6.dp)
            ) {

                //指示点
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in list.indices) {
                        //大小
                        var size by remember { mutableStateOf(5.dp) }
                        size = if (pagerState.currentPage == i) 7.dp else 5.dp

                        //颜色
                        val color =
                            if (pagerState.currentPage == i) MaterialTheme.colorScheme.primary else Color.Gray

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(color)
                                //当size改变的时候以动画的形式改变
                                .animateContentSize()
                                .size(size)
                        )
                        //指示点间的间隔
                        if (i != list.lastIndex) Spacer(
                            modifier = Modifier
                                .height(0.dp)
                                .width(4.dp)
                        )
                    }
                }

            }
        }
    }
}

/**
 * 轮播图数据
 */
data class BannerData(
    val title: String,
    val imageUrl: String,
    val linkUrl: String
)