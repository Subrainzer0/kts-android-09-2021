package ru.shanin.madarareddit.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.shanin.madarareddit.networking.UserRepository
import ru.shanin.madarareddit.ui.main.mapper.TopToUiMapper
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer

class MainViewModel : ViewModel() {

    private val repository = UserRepository()

    private val topListLiveData = MutableLiveData<List<UiModelsContainer>>(emptyList())
    private val isLoadingLiveData = MutableLiveData(false)
    private val errorLiveData = MutableLiveData(false)
    private val loadedTopListLiveData = MutableLiveData<List<UiModelsContainer>>(emptyList())

    private var startedLoadingItemsJob: Job? = null

    private var loadingMoreItemsJob: Job? = null

    val topList: LiveData<List<UiModelsContainer>>
        get() = topListLiveData

    val loadedTopList: LiveData<List<UiModelsContainer>>
        get() = loadedTopListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val isError: LiveData<Boolean>
        get() = errorLiveData

    fun getTop() {
        startedLoadingItemsJob = viewModelScope.launch {
            errorLiveData.postValue(false)
            isLoadingLiveData.postValue(true)
            runCatching {
                val childList = repository.getTop().map { it.data }
                TopToUiMapper.topListToUiModel(childList)
            }.onSuccess {
                isLoadingLiveData.postValue(false)
                topListLiveData.postValue(it)
            }.onFailure {
                isLoadingLiveData.postValue(false)
                errorLiveData.postValue(true)
            }
        }
    }

    fun getTopWithIndex(after: String) {
        loadingMoreItemsJob?.cancel()
        loadingMoreItemsJob = viewModelScope.launch {
            errorLiveData.postValue(false)
            isLoadingLiveData.postValue(true)
            delay(2000)
            runCatching {
                val childList = repository.getTopWithIndex(after).map { it.data }
                TopToUiMapper.topListToUiModel(childList)
            }.onSuccess {
                isLoadingLiveData.postValue(false)
                loadedTopListLiveData.postValue(it)
            }.onFailure {
                isLoadingLiveData.postValue(false)
                errorLiveData.postValue(true)
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
}
