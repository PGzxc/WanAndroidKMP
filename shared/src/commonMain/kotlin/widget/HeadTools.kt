package widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import viewmodel.MineViewModel


/**
 * @desc：我的——常用功能
 */
@Composable
fun HeadTools(viewModel: MineViewModel) {

    Column() {
        Text(
            text = "常用功能",
            fontSize = 18.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 5.dp)
                .fillMaxWidth()
        )
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            itemsIndexed(viewModel.meToolsData, key = { index, item -> "${item.id}_${index}" }) { index, item ->
                Box(
                    modifier = Modifier
                        .background(item.color)
                        .height(150.dp)
                        .fillMaxWidth()
                        .clickable { print("---$item.name" ) }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.name,
                        modifier = Modifier
                            .background(item.color)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}