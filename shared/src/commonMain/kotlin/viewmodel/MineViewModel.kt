package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import bean.CoinInfo
import bean.MeToolBean
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import service.Api
import state.UIState

class MineViewModel: ScreenModel {

    private val api = Api.instance
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState
    val meToolsData by lazy {
        mutableStateListOf<MeToolBean>(
            MeToolBean(1, "工具", Color.Blue),
            MeToolBean(2, "问答", Color.Cyan),
            MeToolBean(3, "消息", Color.Green),
            MeToolBean(4, "课程", Color.Magenta),
            MeToolBean(5, "待办清单", Color.LightGray),
            MeToolBean(6, "分享文章", Color.Red),
        )
    }
    var coinInfo by mutableStateOf(CoinInfo())

    fun getCoinInfo() {
        kotlin.runCatching {
            coroutineScope.launch {
                val coinInfoDeferred = async { api.getCoinUserInfo() }
                val coinInfoEntity = coinInfoDeferred.await()
                coinInfoEntity.data?.let { coinInfo = coinInfoEntity.data }
                print(coinInfoEntity)
                _uiState.value = UIState.Success("Success")
            }
        }.onFailure {
            _uiState.value = UIState.Error("数据加载出错，请点击重试")
        }
    }




}