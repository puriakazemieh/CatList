package com.example.cat.presentation.home

import androidx.paging.compose.LazyPagingItems
import com.example.cat.domain.model.Cat
import com.example.cat.presentation.base.UiEffect
import com.example.cat.presentation.base.UiEvent
import com.example.cat.presentation.base.UiState

class HomeContract {

    sealed class Event : UiEvent {
        object GetCats : Event()
        data class OnCollectCatList(var catList: LazyPagingItems<Cat>) : Event()
    }

    data class State(
        val catList: LazyPagingItems<Cat>? = null,
        val isLoading: Boolean = false,
//        val catListState: CatListState
    ) : UiState

    sealed class CatListState {
        object Loading : CatListState()
        data class Success(var catList: LazyPagingItems<Cat>) : CatListState()
    }

    sealed class Effect : UiEffect {

        object ShowToast : Effect()

    }
}