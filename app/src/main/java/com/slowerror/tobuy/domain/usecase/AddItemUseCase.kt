package com.slowerror.tobuy.domain.usecase

import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.repository.ToBuyRepository

class AddItemUseCase(private val toBuyRepository: ToBuyRepository) {

    suspend operator fun invoke(item: Item) = toBuyRepository.insertItem(item)

}