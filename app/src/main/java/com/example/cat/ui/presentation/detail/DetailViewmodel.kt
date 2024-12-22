package com.example.cat.ui.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.example.cat.ui.presentation.base.BaseViewModel

class DetailViewmodel (
    savedStateHandle: SavedStateHandle,
):
    BaseViewModel<DetailContract.Event, DetailContract.State, DetailContract.Effect>() {

    val idCat = savedStateHandle.get<String>(DetailNavigation.ID_CAT) ?: ""

    override fun createInitialState(): DetailContract.State {
        return DetailContract.State()
    }

    override fun handleEvent(event: DetailContract.Event) {

    }
}