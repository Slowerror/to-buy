package com.slowerror.tobuy.di

import com.slowerror.tobuy.domain.usecase.category_usecase.AddCategoryUseCase
import com.slowerror.tobuy.domain.usecase.category_usecase.GetAllCategoryUseCase
import com.slowerror.tobuy.domain.usecase.category_usecase.RemoveCategoryUseCase
import com.slowerror.tobuy.domain.usecase.category_usecase.UpdateCategoryUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.AddItemUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.UpdateItemUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.GetAllItemUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.GetAllItemWithCategoryUseCase
import com.slowerror.tobuy.domain.usecase.item_usecase.RemoveItemUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetAllItemUseCase)
    factoryOf(::AddItemUseCase)
    factoryOf(::RemoveItemUseCase)
    factoryOf(::UpdateItemUseCase)
    factoryOf(::GetAllItemWithCategoryUseCase)

    factoryOf(::GetAllCategoryUseCase)
    factoryOf(::AddCategoryUseCase)
    factoryOf(::RemoveCategoryUseCase)
    factoryOf(::UpdateCategoryUseCase)



}