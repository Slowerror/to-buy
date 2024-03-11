package com.slowerror.tobuy.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowerror.tobuy.domain.model.Category
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.usecase.category_usecase.AddCategoryUseCase
import com.slowerror.tobuy.domain.usecase.category_usecase.GetAllCategoryUseCase
import com.slowerror.tobuy.domain.usecase.category_usecase.RemoveCategoryUseCase
import com.slowerror.tobuy.domain.usecase.category_usecase.UpdateCategoryUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.AddItemUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.UpdateItemUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.GetAllItemUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.RemoveItemUseCase
import kotlinx.coroutines.launch

class BaseViewModel(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val removeCategoryUseCase: RemoveCategoryUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase
) : ViewModel() {

    private val _itemListLiveData = MutableLiveData<List<Item>>()
    val itemListLiveData get() = _itemListLiveData as LiveData<List<Item>>

    private val _categoryListLiveData = MutableLiveData<List<Category>>()
    val categoryListLiveData get() = _categoryListLiveData as LiveData<List<Category>>

    private val _transactionCompletedLiveData = MutableLiveData<Boolean>()
    val transactionCompletedLiveData get() = _transactionCompletedLiveData as LiveData<Boolean>

    init {
        getAllItems()
        getAllCategories()
    }

    //region ItemEntity
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

    fun updateItem(item: Item) = viewModelScope.launch {
        updateItemUseCase(item)

        _transactionCompletedLiveData.postValue(true)
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
    //endregion ItemEntity

    //region CategoryEntity
    private fun getAllCategories() = viewModelScope.launch {
        getAllCategoryUseCase().collect { categories ->
            _categoryListLiveData.postValue(categories)
        }
    }

    fun addCategory(category: Category) = viewModelScope.launch {
        addCategoryUseCase(category)
        _transactionCompletedLiveData.postValue(true)
    }

    fun removeCategory(category: Category) = viewModelScope.launch {
        removeCategoryUseCase(category)
        _transactionCompletedLiveData.postValue(true)
    }

    fun updateCategory(category: Category) = viewModelScope.launch {
        updateCategoryUseCase(category)
        _transactionCompletedLiveData.postValue(true)
    }
    //endregion CategoryEntity

    fun setFalseTransactionCompleted() {
        _transactionCompletedLiveData.postValue(false)
    }
}