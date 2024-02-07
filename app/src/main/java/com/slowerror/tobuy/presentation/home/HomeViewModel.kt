package com.slowerror.tobuy.presentation.home

import androidx.lifecycle.ViewModel
import com.slowerror.tobuy.domain.usecase.AddItemUseCase
import com.slowerror.tobuy.domain.usecase.GetAllItemUseCase
import com.slowerror.tobuy.domain.usecase.RemoveItemUseCase

class HomeViewModel(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val addItemUseCase: AddItemUseCase
) : ViewModel() {

}