package com.slowerror.tobuy.domain.usecase.item_usecase

import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class GetAllItemUseCase(private val itemRepository: ItemRepository) {

    operator fun invoke(): Flow<List<Item>> = itemRepository.getAllItem()

}