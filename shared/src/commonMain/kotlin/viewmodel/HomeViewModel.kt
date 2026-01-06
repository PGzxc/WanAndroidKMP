package viewmodel

import action.StateAction
import androidx.compose.runtime.mutableStateListOf
import bean.Article
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import service.Api
import state.UIState
import widget.BannerData

class HomeViewModel : ScreenModel {

    private val api = Api.instance
    val bannerList by lazy { mutableStateListOf<BannerData>() }
    val articleList by lazy { mutableStateListOf<Article>() }

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState

    init {
        dispatch(StateAction.FetchData)
    }

    fun dispatch(action: StateAction) {
        when (action) {
            is StateAction.FetchData -> {
                kotlin.runCatching {
                    screenModelScope.launch {
                        val homeBannerEntity = api.getHomeBanner()
                        val map = homeBannerEntity.data?.map {
                            BannerData(title = it.title, imageUrl = it.imagePath, linkUrl = it.url)
                        } ?: emptyList()

                        bannerList.addAll(map)
                        print(bannerList)

                        val homeArticleEntity = api.getHomeArticleList(page = 0)
                        articleList.addAll(homeArticleEntity.data?.datas?: emptyList() )
                        print(articleList)
                        _uiState.value = UIState.Success("Success")
                    }
                }.onFailure {
                    _uiState.value = UIState.Error("数据加载出错，请点击重试")
                }
            }
        }
    }
}