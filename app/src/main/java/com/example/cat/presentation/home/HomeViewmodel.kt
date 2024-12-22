package com.example.cat.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.example.cat.domain.model.Cat
import com.example.cat.domain.usecase.UseCase
import com.example.cat.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HomeViewmodel(
    private val useCase: UseCase
) :
    BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    private val _catResult: MutableStateFlow<PagingData<Cat>> =
        MutableStateFlow(PagingData.empty())
    val catResult: StateFlow<PagingData<Cat>> = _catResult


    override fun createInitialState(): HomeContract.State {
        return HomeContract.State()
    }

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnCollectCatList -> collectCatList(event.catList)
            HomeContract.Event.GetCats -> getCats()

        }
    }

    fun getCats() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            useCase.getCatsUseCase().cachedIn(viewModelScope).collect {
                _catResult.emit(it)
            }
        }
    }

    private fun collectCatList(eventCatList: LazyPagingItems<Cat>) {
        viewModelScope.launch {
            setState { copy(catList = eventCatList, isLoading = false) }
        }
    }
}