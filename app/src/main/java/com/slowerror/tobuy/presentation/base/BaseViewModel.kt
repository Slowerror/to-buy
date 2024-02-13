package com.slowerror.tobuy.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.usecase.AddItemUseCase
import com.slowerror.tobuy.domain.usecase.GetAllItemUseCase
import com.slowerror.tobuy.domain.usecase.RemoveItemUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BaseViewModel(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val addItemUseCase: AddItemUseCase
) : ViewModel() {

    private val _itemListLiveData = MutableLiveData<List<Item>>()
    val itemListLiveData get() = _itemListLiveData

    init {
        getAllItems()
    }

    private fun getAllItems() = viewModelScope.launch {
        getAllItemUseCase().collect { items ->
            _itemListLiveData.postValue(items)
        }
    }

    fun addItem(item: Item) = viewModelScope.launch {
        addItemUseCase(item)
        delay(700)
    }

    fun removeItem(item: Item) = viewModelScope.launch {
        removeItemUseCase(item)
    }
}