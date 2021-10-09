package ru.shanin.madarareddit.ui.main

import android.app.Application
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.shanin.madarareddit.networking.UserRepository
import ru.shanin.madarareddit.ui.main.mapper.TopToUiMapper
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository()

    private val topListFlow = MutableStateFlow<List<UiModelsContainer>>(emptyList())
    private val isLoadingFlow = MutableStateFlow(false)
    private val errorFlow = MutableStateFlow<Throwable?>(null)
    private val loadedTopListFlow = MutableStateFlow<List<UiModelsContainer>>(emptyList())
    private val noNetworkFlow = MutableStateFlow(false)

    private var startedLoadingItemsJob: Job? = null

    private var loadingMoreItemsJob: Job? = null

    val topList: Flow<List<UiModelsContainer>>
        get() = topListFlow.asStateFlow()

    val loadedTopList: Flow<List<UiModelsContainer>>
        get() = loadedTopListFlow.asStateFlow()

    val isLoading: Flow<Boolean>
        get() = isLoadingFlow.asStateFlow()

    val isError: Flow<Throwable?>
        get() = errorFlow.asStateFlow()

    val isNoNetwork: Flow<Boolean>
    get() = noNetworkFlow.asStateFlow()

    fun getTop() {
        startedLoadingItemsJob = viewModelScope.launch {
            isLoadingFlow.value = true
            delay(2000)
            repository.getTop()
                .flowOn(Dispatchers.IO)
                .map { topList -> topList.map { it.data } }
                .catch { throwable ->
                    isLoadingFlow.value = false
                    errorFlow.value = throwable
                }
                .flowOn(Dispatchers.Main)
                .collect { childDataList ->
                    isLoadingFlow.value = false
                    val convertedList = TopToUiMapper.topListToUiModel(childDataList)
                    topListFlow.value = convertedList
                }
        }
    }

    fun getTopWithIndex(after: String) {
        loadingMoreItemsJob?.cancel()
        loadingMoreItemsJob = viewModelScope.launch {
            isLoadingFlow.value = true
            delay(2000)
            repository.getTopWithIndex(after)
                .flowOn(Dispatchers.IO)
                .map { topList -> topList.map { it.data } }
                .catch { throwable ->
                    isLoadingFlow.value = false
                    errorFlow.value = throwable
                }
                .flowOn(Dispatchers.Main)
                .collect { childDataList ->
                    isLoadingFlow.value = false
                    val convertedList = TopToUiMapper.topListToUiModel(childDataList)
                    loadedTopListFlow.value = convertedList
                }
        }
    }

    fun vote(id: String, dir: String) {
        viewModelScope.launch {
            runCatching {
                repository.vote(id, dir)
            }
        }
    }

    fun checkNetworkState() {
        val connectivityManager = getSystemService(getApplication(), ConnectivityManager::class.java)
        val networkCallback = object : NetworkCallback() {

            override fun onAvailable(network: Network) {
                noNetworkFlow.value = false
            }

            override fun onLost(network: Network) {
                noNetworkFlow.value = true
            }
        }
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            connectivityManager?.registerDefaultNetworkCallback(networkCallback)
        }
    }
}
