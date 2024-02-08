package com.slowerror.tobuy.domain.usecase

import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.repository.ToBuyRepository
import kotlinx.coroutines.flow.Flow

class GetAllItemUseCase(private val toBuyRepository: ToBuyRepository) {

    operator fun invoke(): Flow<List<Item>> = toBuyRepository.getAllItem()

}