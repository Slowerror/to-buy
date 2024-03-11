package com.slowerror.tobuy.domain.usecase.category_usecase

import com.slowerror.tobuy.domain.model.Category
import com.slowerror.tobuy.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetAllCategoryUseCase(private val categoryRepository: CategoryRepository) {

    operator fun invoke(): Flow<List<Category>> = categoryRepository.getAllCategory()

}
