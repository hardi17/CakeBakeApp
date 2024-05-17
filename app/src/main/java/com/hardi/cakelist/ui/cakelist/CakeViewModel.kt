package com.hardi.cakelist.ui.cakelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.cakelist.data.model.Cake
import com.hardi.cakelist.data.repository.CakeListRepository
import com.hardi.cakelist.utils.DispatcherProvider
import com.hardi.cakelist.utils.UIState
import com.hardi.cakelist.utils.internetcheck.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CakeViewModel @Inject constructor(
    private val cakeListRepository: CakeListRepository,
    private val dispatcherProvider: DispatcherProvider,
    networkHelper: NetworkHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<Cake>>>(UIState.Loading)

    val uiState: StateFlow<UIState<List<Cake>>> = _uiState

    init {
        if (networkHelper.isInternetConnected()) {
            fetchCakeList()
        } else {
            _uiState.value = UIState.Error("No internet connection")
        }
    }

    fun fetchCakeList() {
        viewModelScope.launch(dispatcherProvider.main) {
            cakeListRepository.getCakeList()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UIState.Error(e.toString())
                }.collect {
                    if (it.isEmpty()) {
                        _uiState.value = UIState.Error("No data available")
                    } else {
                        _uiState.value = UIState.Success(it)
                    }
                }
        }
    }

}