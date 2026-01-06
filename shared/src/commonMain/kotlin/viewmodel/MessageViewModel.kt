package viewmodel

import action.StateAction
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import bean.MessageItem
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import service.Api
import state.UIState

class MessageViewModel: ScreenModel {

    val currentPage by lazy {
        mutableStateOf<Int>(1)
    }

    val messageTitle = listOf("未读消息", "已读消息")
    val messageReadList by lazy { mutableStateListOf<MessageItem>() }
    val messageUnReadList by lazy { mutableStateListOf<MessageItem>() }

    private val api = Api.instance
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState

    init {
        //dispatch(StateAction.FetchData)
    }

    fun dispatch(action: StateAction) {
        when (action) {
            is StateAction.FetchData -> {
                 getMessageUnread()
            }
        }
    }
    fun getMessageUnread(){
        kotlin.runCatching {
            screenModelScope.launch {
                val messageUnreadEntity = api.getMessageUnRead()
                messageUnReadList.addAll(messageUnreadEntity.data?.datas ?: emptyList())
                //print(messageUnReadList)

                _uiState.value = UIState.Success("Success")
            }
        }.onFailure {
            _uiState.value = UIState.Error("数据加载出错，请点击重试")
        }
    }
    fun getMessageRead(value: Int) {
        kotlin.runCatching {
            screenModelScope.launch {
                val messageReadEntity = api.getMessageRead()
                messageReadList.addAll(messageReadEntity.data?.datas ?: emptyList())
                //print(messageUnReadList)

                _uiState.value = UIState.Success("Success")
            }
        }.onFailure {
            _uiState.value = UIState.Error("数据加载出错，请点击重试")
        }
    }

}