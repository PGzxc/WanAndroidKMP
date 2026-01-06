package viewmodel

import action.StateAction
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import bean.Article
import bean.ProjectBean
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import service.Api
import state.UIState

class ProjectViewModel : ScreenModel {
    private val api = Api.instance
    val projectListData by lazy { mutableStateListOf<ProjectBean>() }
    val projectItemListData by lazy { mutableStateListOf<Article>() }
    val currentPage by lazy { mutableStateOf<Int>(0) }
    private val currentCid by lazy { mutableStateOf<Long>(0) }
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState

    fun dispatch(action: StateAction) {
        when (action) {
            is StateAction.FetchData -> {
                kotlin.runCatching {
                    screenModelScope.launch {
                        getProjectListData()
                    }
                }.onFailure {
                    _uiState.value = UIState.Error("数据加载出错，请点击重试")
                }
            }
        }
    }

    fun getProjectListData() {
        kotlin.runCatching {
            screenModelScope.launch {
                val navigatorEntity = api.getProject()
                projectListData.addAll(navigatorEntity.data ?: emptyList())
                _uiState.value = UIState.Success("Success")
            }
        }.onFailure {
            _uiState.value = UIState.Error("数据加载出错，请点击重试")
        }
    }

    fun getProjectItemListData(page: Int, cid: Long) {
        kotlin.runCatching {
            screenModelScope.launch {
                val requestCid = cid
                if (page == 0 || currentCid.value != cid) {
                    projectItemListData.clear()
                    currentCid.value = cid
                }
                val navigatorItemListEntity = api.getProjectItem(page, cid)
                if (currentCid.value == requestCid) {
                    val newItems = navigatorItemListEntity.data?.datas ?: emptyList()
                    projectItemListData.addAll(newItems)
                }
                _uiState.value = UIState.Success("Success")
            }
        }.onFailure {
            _uiState.value = UIState.Error("数据加载出错，请点击重试")
        }
    }
}