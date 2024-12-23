package com.example.cat.ui.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.map
import com.example.cat.domain.model.Cat
import com.example.cat.domain.usecase.UseCase
import com.example.cat.ui.presentation.base.BaseViewModel
import com.example.cat.ui.presentation.home.HomeContract.Effect.*
import kotlinx.coroutines.delay
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
            is HomeContract.Event.OnGoToDetail -> setEffect { GoToDetail(event.id) }
           is HomeContract.Event.OnFavClicked -> onFavClicked(event.id)
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

    private fun onFavClicked(id: String) {
        viewModelScope.launch {
            useCase.setFavUseCase(id).collect {isFav->
                _catResult.emit(
                    _catResult.value.map {
                        if (it.id == id)
                            it.copy(isCatFav = isFav)
                        else it
                    }
                )
            }
        }
    }
}