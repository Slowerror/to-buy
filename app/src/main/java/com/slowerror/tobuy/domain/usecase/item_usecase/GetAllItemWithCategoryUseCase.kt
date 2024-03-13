package com.slowerror.tobuy.domain.usecase.item_usecase

import com.slowerror.tobuy.domain.model.ItemWithCategory
import com.slowerror.tobuy.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class GetAllItemWithCategoryUseCase(private val itemRepository: ItemRepository) {

    operator fun invoke(): Flow<List<ItemWithCategory>> = itemRepository.getAllItemWithCategory()

}