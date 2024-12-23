package com.example.cat.ui.presentation.detail

import com.example.cat.domain.model.Cat
import com.example.cat.ui.presentation.base.UiEffect
import com.example.cat.ui.presentation.base.UiEvent
import com.example.cat.ui.presentation.base.UiState
import com.example.cat.ui.presentation.home.HomeContract.Event

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