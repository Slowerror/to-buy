package com.slowerror.tobuy.domain.usecase.item_usecase

import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.repository.ItemRepository

class UpdateItemUseCase(private val repository: ItemRepository) {

    suspend operator fun invoke(item: Item) = repository.updateItem(item)

}