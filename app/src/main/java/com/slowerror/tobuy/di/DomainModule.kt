package com.slowerror.tobuy.di

import com.slowerror.tobuy.domain.usecase.AddItemUseCase
import com.slowerror.tobuy.domain.usecase.GetAllItemUseCase
import com.slowerror.tobuy.domain.usecase.RemoveItemUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetAllItemUseCase)
    factoryOf(::AddItemUseCase)
    factoryOf(::RemoveItemUseCase)
}