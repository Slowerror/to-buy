package com.slowerror.tobuy.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.usecase.AddItemUseCase
import com.slowerror.tobuy.domain.usecase.GetAllItemUseCase
import com.slowerror.tobuy.domain.usecase.RemoveItemUseCase
import kotlinx.coroutines.launch

class BaseViewModel(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val addItemUseCase: AddItemUseCase
) : ViewModel() {

    private val _itemListLiveData = MutableLiveData<List<Item>>()
    val itemListLiveData get() = _itemListLiveData

    init {
        viewModelScope.launch {
            getAllItems()
        }
    }

    private suspend fun getAllItems() {
        getAllItemUseCase().collect { items ->
            _itemListLiveData.postValue(items)
        }
    }

    fun addItem(item: Item) = viewModelScope.launch {
        addItemUseCase(item)
    }

    fun removeItem(item: Item) = viewModelScope.launch {
        removeItemUseCase(item)
    }
}