package com.example.cat.ui.presentation.detail

import com.example.cat.domain.model.Cat
import com.example.cat.ui.presentation.base.UiEffect
import com.example.cat.ui.presentation.base.UiEvent
import com.example.cat.ui.presentation.base.UiState

class DetailContract {

    sealed class Event : UiEvent {
        object GetCatDetail : Event()
        object NavigateBack : Event()
        data class OnCollectCatDetail(var cat: Cat) : Event()
    }

    data class State(
        val cat: Cat? = null,
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {

    }
}