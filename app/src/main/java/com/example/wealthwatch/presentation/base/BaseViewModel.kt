package com.example.wealthwatch.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wealthwatch.presentation.log
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel(

) : ViewModel() {
    companion object {
        private val TAG = this::class.java.simpleName
        private const val BUFFER_CAPACITY = 10
    }

    private val job = Job()

    private val _baseEvent = Channel<BaseUiEvent>(capacity = BUFFER_CAPACITY)
    val baseEvent: Flow<BaseUiEvent> = _baseEvent.receiveAsFlow()

    private val _topBarConfig = MutableStateFlow(TopBarEvent.SetConfig(isTopbarVisible = false))
    val topBarConfig: StateFlow<TopBarEvent.SetConfig> = _topBarConfig.asStateFlow()

    protected fun setTopBarConfig(config: TopBarEvent.SetConfig) {
        _topBarConfig.update { config }
    }

    /*private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()*/

    /*protected fun sendEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }*/

    protected open fun handleError(error: Throwable) {
        if (error is CancellationException) return
        log(TAG, "Error: ${error.message ?: "Unknown error"} ")
    }

    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        onError: (Throwable) -> Unit = { handleError(it) },
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(context) {
            try {
                block()
            } catch (exception: Throwable) {
                onError(exception)
            }
        }
    }

    protected fun sendBaseEvent(event: BaseUiEvent) {
        viewModelScope.launch { _baseEvent.send(event) }
    }

    override fun onCleared() {
        super.onCleared()
        launch {
            job.cancel()
            job.join()
            _baseEvent.cancel()
        }
    }
}
