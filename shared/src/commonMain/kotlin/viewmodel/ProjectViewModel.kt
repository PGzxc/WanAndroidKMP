package viewmodel

import action.StateAction
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import bean.Article
import bean.ProjectBean
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import service.Api
import state.UIState

class ProjectViewModel: ScreenModel {
    private val api = Api.instance
    val projectListData by lazy { mutableStateListOf<ProjectBean>() }
    val projectItemListData by lazy { mutableStateListOf<Article>() }
    val currentPage by lazy { mutableStateOf<Int>(0) }
    private val currentCid by lazy { mutableStateOf<Long>(0) }
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState

    // init块中的调用已移除，避免重复加载数据
    // init {
    //     dispatch(StateAction.FetchData)
    // }

    fun dispatch(action: StateAction) {
        when (action) {
            is StateAction.FetchData -> {
                kotlin.runCatching {
                    coroutineScope.launch {
                        getProjectListData()
                        //_uiState.value = UIState.Success("Success")
                    }
                }.onFailure {
                    _uiState.value = UIState.Error("数据加载出错，请点击重试")
                }
            }
        }
    }

    fun getProjectListData(){
        kotlin.runCatching {
            coroutineScope.launch {
                val projectDeferred = async { api.getProject() }
                val navigatorEntity = projectDeferred.await()

                projectListData.addAll(navigatorEntity.data?: emptyList())
                print(projectListData)

                _uiState.value = UIState.Success("Success")
            }
        }.onFailure {
            _uiState.value = UIState.Error("数据加载出错，请点击重试")
        }
    }

    fun getProjectItemListData(page: Int, cid: Long){    
        kotlin.runCatching {    
            coroutineScope.launch {    
                // 记录请求开始时的cid    
                val requestCid = cid    
                
                // 清除旧数据（仅在切换cid或第一页时）    
                if (page == 0 || currentCid.value != cid) {    
                    projectItemListData.clear()    
                    currentCid.value = cid    
                }    
                
                val projectItemListDeferred = async { api.getProjectItem(page,cid) }    
                val navigatorItemListEntity = projectItemListDeferred.await()    

                // 确保只添加与请求开始时相同cid的数据，并且过滤掉重复项    
                // 这样可以防止快速切换类别时的数据混合    
                if (currentCid.value == requestCid) {    
                    val newItems = navigatorItemListEntity.data?.datas ?: emptyList()    
                    println("Fetched ${newItems.size} items for cid: $requestCid")    
                    
                    // 检查新数据中是否有重复id    
                    val newItemIds = newItems.map { it.id }    
                    val uniqueNewItemIds = newItemIds.distinct()    
                    if (newItemIds.size != uniqueNewItemIds.size) {    
                        val duplicateNewIds = newItemIds.groupBy { it }.filter { it.value.size > 1 }.keys    
                        println("Duplicate IDs in new data: $duplicateNewIds")    
                    }    
                    
                    // 检查与现有数据的重复    
                    val existingIds = projectItemListData.map { it.id }.toSet()    
                    val duplicateWithExisting = newItems.filter { existingIds.contains(it.id) }    
                    if (duplicateWithExisting.isNotEmpty()) {    
                        val duplicateExistingIds = duplicateWithExisting.map { it.id }    
                        println("Duplicate IDs with existing data: $duplicateExistingIds")    
                    }    
                    
                    val uniqueItems = newItems.filter { !existingIds.contains(it.id) }    
                    
                    // 再次检查，确保在添加时没有重复项    
                    val finalUniqueItems = uniqueItems.filter { item ->    
                        !projectItemListData.any { existingItem -> existingItem.id == item.id }    
                    }    
                    
                    projectItemListData.addAll(finalUniqueItems)    
                    println("Added ${finalUniqueItems.size} unique items for cid: $requestCid, total items: ${projectItemListData.size}")    
                }    

                _uiState.value = UIState.Success("Success")    
            }    
        }.onFailure {    
            _uiState.value = UIState.Error("数据加载出错，请点击重试")    
        }    
    }
}