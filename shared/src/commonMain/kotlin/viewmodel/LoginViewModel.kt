package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import data.store

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import service.Api
import state.UIState

class LoginViewModel : ScreenModel {

    //此处的修改无法改变，放在LoginPage中
    var isLogin by mutableStateOf(true)
    var account by mutableStateOf("")
    var password by mutableStateOf("")
    var rePassword by mutableStateOf("")

    private val api = Api.instance
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState


    fun login(username: String, password: String,navigator: Navigator) {
        kotlin.runCatching {
            screenModelScope.launch {
                val userInfoEntity = api.login(username, password)
                if (userInfoEntity.data != null) {
                    store.set(userInfoEntity.data)
                    val user = store.get()
                }
                _uiState.value = UIState.Success("Success")
                navigator.pop()
            }
        }.onFailure {
            _uiState.value = UIState.Error("数据加载出错，请点击重试")
        }
    }

    fun register(username: String, password: String, repassword: String,navigator: Navigator) {
        kotlin.runCatching {
            screenModelScope.launch {
                val userInfoEntity = api.register(username, password, repassword)
                store.set(userInfoEntity.data)
                val user = store.get()
                _uiState.value = UIState.Success("Success")
                navigator.pop()
            }
        }.onFailure {
            _uiState.value = UIState.Error("数据加载出错，请点击重试")
        }
    }

}