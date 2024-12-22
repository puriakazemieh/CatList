package com.example.cat.ui.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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

    }

    private fun getCatDetail(imageId: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            useCase.getCatDetailUseCase(imageId).collect {
                setState { copy(cat = it, isLoading = false) }
            }
        }
    }
}