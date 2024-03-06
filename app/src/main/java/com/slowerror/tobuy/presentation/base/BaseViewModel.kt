package com.slowerror.tobuy.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.usecase.AddItemUseCase
import com.slowerror.tobuy.domain.usecase.UpdateItemUseCase
import com.slowerror.tobuy.domain.usecase.GetAllItemUseCase
import com.slowerror.tobuy.domain.usecase.RemoveItemUseCase
import kotlinx.coroutines.launch

class BaseViewModel(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase
) : ViewModel() {

    private val _itemListLiveData = MutableLiveData<List<Item>>()
    val itemListLiveData get() = _itemListLiveData as LiveData<List<Item>>

    private val _transactionCompletedLiveData = MutableLiveData<Boolean>()
    val transactionCompletedLiveData get() = _transactionCompletedLiveData as LiveData<Boolean>

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

        _transactionCompletedLiveData.postValue(true)

    }

    fun removeItem(item: Item) = viewModelScope.launch {
        removeItemUseCase(item)
    }

    fun onBumpPriority(item: Item) = viewModelScope.launch {
        val currentPriority = item.priority
        var newPriority = currentPriority + 1

        if (newPriority > 3) {
            newPriority = 1
        }

        val updateItem = item.copy(priority = newPriority)
        updateItemUseCase(updateItem)
    }

    fun updateItem(item: Item) = viewModelScope.launch {
        updateItemUseCase(item)

        _transactionCompletedLiveData.postValue(true)
    }

    fun setFalseTransactionCompleted() {
        _transactionCompletedLiveData.postValue(false)
    }
}