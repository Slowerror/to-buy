package com.slowerror.tobuy.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowerror.tobuy.domain.model.Category
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.model.ItemWithCategory
import com.slowerror.tobuy.domain.usecase.category_usecase.AddCategoryUseCase
import com.slowerror.tobuy.domain.usecase.category_usecase.GetAllCategoryUseCase
import com.slowerror.tobuy.domain.usecase.category_usecase.RemoveCategoryUseCase
import com.slowerror.tobuy.domain.usecase.category_usecase.UpdateCategoryUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.AddItemUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.UpdateItemUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.GetAllItemUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.GetAllItemWithCategoryUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.RemoveItemUseCase
import kotlinx.coroutines.launch

class BaseViewModel(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase,
    private val getAllItemWithCategoryUseCase: GetAllItemWithCategoryUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val removeCategoryUseCase: RemoveCategoryUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase
) : ViewModel() {

    /*private val _itemListLiveData = MutableLiveData<List<Item>>()
    val itemListLiveData: LiveData<List<Item>>
        get() = _itemListLiveData*/

    private val _categoryListLiveData = MutableLiveData<List<Category>>()
    val categoryListLiveData: LiveData<List<Category>>
        get() = _categoryListLiveData

    private val _itemListWithCategoryLiveData = MutableLiveData<List<ItemWithCategory>>()
    val itemListWithCategoryLiveData: LiveData<List<ItemWithCategory>>
        get() = _itemListWithCategoryLiveData

    private val _categoriesViewStateLiveData =
        MutableLiveData(CategoriesViewState())
    val categoriesViewStateLiveData: LiveData<CategoriesViewState>
        get() = _categoriesViewStateLiveData

    private val _transactionCompletedLiveData = MutableLiveData<Event<Boolean>>()
    val transactionCompletedLiveData: LiveData<Event<Boolean>>
        get() = _transactionCompletedLiveData

    init {
//        getAllItems()
        getAllCategories()
        getAllItemWithCategory()
    }

    //region ItemEntity
    private fun getAllItems() = viewModelScope.launch {
        getAllItemUseCase().collect { items ->
//            _itemListLiveData.postValue(items)
        }
    }

    private fun getAllItemWithCategory() = viewModelScope.launch {
        getAllItemWithCategoryUseCase().collect { map ->
            _itemListWithCategoryLiveData.postValue(map)
        }
    }

    fun addItem(item: Item) = viewModelScope.launch {
        addItemUseCase(item)

        _transactionCompletedLiveData.postValue(Event(true))

    }

    fun removeItem(item: Item) = viewModelScope.launch {
        removeItemUseCase(item)
    }

    fun updateItem(item: Item) = viewModelScope.launch {
        updateItemUseCase(item)

        _transactionCompletedLiveData.postValue(Event(true))
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
        _transactionCompletedLiveData.postValue(Event(true))
    }

    fun removeCategory(category: Category) = viewModelScope.launch {
        removeCategoryUseCase(category)
        _transactionCompletedLiveData.postValue(Event(true))
    }

    fun updateCategory(category: Category) = viewModelScope.launch {
        updateCategoryUseCase(category)
        _transactionCompletedLiveData.postValue(Event(true))
    }
    //endregion CategoryEntity

    fun onCategorySelected(categoryId: String, isShowLoading: Boolean = false) {
        if (isShowLoading) {
            val loadingViewState = CategoriesViewState(isLoading = true)
            _categoriesViewStateLiveData.value = loadingViewState
        }

        val categories = _categoryListLiveData.value ?: return
        val viewStateItemList = ArrayList<CategoriesViewState.CategoryItem>()

        viewStateItemList.add(
            CategoriesViewState.CategoryItem(
                category = Category.getDefaultCategory(),
                isSelected = categoryId == Category.DEFAULT_CATEGORY_ID
            )
        )

        categories.forEach {
            viewStateItemList.add(
                CategoriesViewState.CategoryItem(
                    category = it,
                    isSelected = it.id == categoryId
                )
            )
        }

        val viewState = CategoriesViewState(categoryItemList = viewStateItemList)
        _categoriesViewStateLiveData.postValue(viewState)
    }
}