package widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bean.Article
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

/**
 * 文章列表项
 * @param item Data
 */
@Composable
fun ArticleListItem(
    item: Article,
    itemClick: (String) -> Unit,
    collectClick: (() -> Unit)?
) {

    Column(modifier = Modifier
        .clickable {
            item.link?.let { itemClick(it) }
        }
        .padding(vertical = 8.dp, horizontal = 10.dp)) {
        //第一行
        Row(verticalAlignment = Alignment.CenterVertically) {
            //Tag-新
            AnimatedVisibility(visible = item.fresh) {
                Text(
                    text = if (item.fresh) "新" else "",
                    Modifier
                        .background(Color.Red, shape = RoundedCornerShape(2.dp))
                        .padding(horizontal = 5.dp),
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
            //Tag-项目
            AnimatedVisibility(visible = item.tags.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(start = if (item.fresh) 8.dp else 0.dp)
                        .background(Color.Blue, shape = RoundedCornerShape(2.dp))
                        .padding(horizontal = 5.dp),

                    text = "${item.tags.firstOrNull()?.name ?: ""}",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
            Row(modifier = Modifier.padding(start = 8.dp)) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = item.getAuthor(),
                    modifier = Modifier.padding(start = 3.dp),
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row() {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "${item.niceDate}",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 3.dp),
                )
            }
        }
        //第2行
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${item.title}",
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                AnimatedVisibility(visible = item.desc.isNotEmpty()) {
                    Text(
                        text = "${item.desc}",
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
            AnimatedVisibility(visible = item.envelopePic.isNotEmpty()) {
                KamelImage(
                    asyncPainterResource(item.envelopePic),
                    modifier = Modifier.size(width = 90.dp, height = 120.dp),
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
        }

        //第3行
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${item.superChapterName}",
                fontSize = 12.sp,
                color = Color.White, modifier = Modifier
                    .background(Color(0xff8d4bbb), shape = RoundedCornerShape(2.dp))
                    .padding(horizontal = 5.dp)
            )
            Text(
                text = "${item.chapterName}",
                fontSize = 12.sp,
                color = Color.White, modifier = Modifier
                    .padding(start = 8.dp)
                    .background(Color(0xFF6650a4), shape = RoundedCornerShape(2.dp))
                    .padding(horizontal = 5.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier) {
                Icon(
                    imageVector = if (item.collect) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (item.collect) Color.Red else Color.Gray,
                    modifier = Modifier.clickable {
                        collectClick?.invoke()
                    }
                )
            }
        }
    }
    Divider(
        modifier = Modifier.padding(top = 8.dp), thickness = 2.dp, color = Color.LightGray
    )
}