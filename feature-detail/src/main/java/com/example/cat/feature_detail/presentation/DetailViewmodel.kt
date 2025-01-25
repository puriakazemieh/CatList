package com.example.cat.feature_detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.cat.core.constant.ID_CAT
import com.example.cat.domain.usecase.UseCase
import com.example.cat.core.ui.presentration.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewmodel(
    savedStateHandle: SavedStateHandle,
    private val useCase: UseCase
) :
    BaseViewModel<DetailContract.Event, DetailContract.State, DetailContract.Effect>() {

    val idCat = savedStateHandle.get<String>(ID_CAT) ?: ""

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