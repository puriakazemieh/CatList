package com.example.cat.ui.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.example.cat.domain.usecase.UseCase
import com.example.cat.ui.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewmodel(
    savedStateHandle: SavedStateHandle,
    private val useCase: UseCase
) :
    BaseViewModel<DetailContract.Event, DetailContract.State, DetailContract.Effect>() {

    val idCat = savedStateHandle.get<String>(DetailNavigation.ID_CAT) ?: ""

    init {
        getCatDetail(idCat)
    }

    override fun createInitialState(): DetailContract.State {
        return DetailContract.State()
    }

    override fun handleEvent(event: DetailContract.Event) {
        when (event) {
            is DetailContract.Event.OnFavClicked -> onFavClicked(event.id)
        }
    }

    private fun getCatDetail(imageId: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            useCase.getCatDetailUseCase(imageId).collect {
                setState { copy(cat = it, isLoading = false) }
            }
        }
    }


    private fun onFavClicked(id: String) {
        viewModelScope.launch {
            useCase.setFavUseCase(id).collect { isFav ->
                setState { copy(cat = cat?.copy(isCatFav = isFav), isLoading = false) }
            }
        }
    }
}