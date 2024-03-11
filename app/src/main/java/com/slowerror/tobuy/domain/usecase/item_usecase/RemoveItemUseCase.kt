package com.slowerror.tobuy.domain.usecase.item_usecase

import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.repository.ItemRepository

class RemoveItemUseCase(private val itemRepository: ItemRepository) {

    suspend operator fun invoke(item: Item) = itemRepository.deleteItem(item)

}