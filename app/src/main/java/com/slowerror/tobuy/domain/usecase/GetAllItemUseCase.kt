package com.slowerror.tobuy.domain.usecase

import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.repository.ToBuyRepository

class GetAllItemUseCase(private val toBuyRepository: ToBuyRepository) {

    operator fun invoke(): List<Item> = toBuyRepository.getAllItem()

}