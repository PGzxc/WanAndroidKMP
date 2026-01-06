package viewmodel

import action.StateAction
import androidx.compose.runtime.mutableStateListOf
import bean.TreeBean
import bean.TreeItemBean
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import service.Api
import service.NavigatorService
import state.UIState

class NavigatorViewModel: ScreenModel {

    private val api = Api.instance
    val treeItemList by lazy { mutableStateListOf<TreeBean>() }

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
                        val navigatorEntity = api.getNavigator()
                        treeItemList.addAll(navigatorEntity.data?: emptyList())
                        _uiState.value = UIState.Success("Success")
                    }
                }.onFailure {
                    _uiState.value = UIState.Error("数据加载出错，请点击重试")
                }
            }
        }
    }

}