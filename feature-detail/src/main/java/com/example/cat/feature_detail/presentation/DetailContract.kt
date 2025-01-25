package com.example.cat.feature_detail.presentation

import com.example.cat.domain.model.Cat
import com.example.cat.core.ui.presentration.base.UiEffect
import com.example.cat.core.ui.presentration.base.UiEvent
import com.example.cat.core.ui.presentration.base.UiState

class DetailContract {

    sealed class Event : UiEvent {
        data class  OnFavClicked(val id: String)  : Event()
    }

    data class State(
        val cat: Cat? = null,
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {

    }
}