package com.slowerror.tobuy.domain.usecase.category_usecase

import com.slowerror.tobuy.domain.model.Category
import com.slowerror.tobuy.domain.repository.CategoryRepository

class RemoveCategoryUseCase(private val categoryRepository: CategoryRepository) {

    suspend operator fun invoke(category: Category) = categoryRepository.deleteCategory(category)

}



