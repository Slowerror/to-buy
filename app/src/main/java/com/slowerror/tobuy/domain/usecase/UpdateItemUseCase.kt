package com.slowerror.tobuy.domain.usecase

import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.repository.ToBuyRepository

class UpdateItemUseCase(private val repository: ToBuyRepository) {

    suspend operator fun invoke(item: Item) = repository.updateItem(item)

}