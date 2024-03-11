package com.slowerror.tobuy.di

import com.slowerror.tobuy.data.local.AppDatabase
import com.slowerror.tobuy.data.mapper.CategoryMapperImpl
import com.slowerror.tobuy.data.mapper.ItemMapperImpl
import com.slowerror.tobuy.data.repository.CategoryRepositoryImpl
import com.slowerror.tobuy.data.repository.ItemRepositoryImpl
import com.slowerror.tobuy.domain.repository.CategoryRepository
import com.slowerror.tobuy.domain.repository.ItemRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {

    single<AppDatabase> {
        AppDatabase.getInstance(context = get())
    }

    singleOf(::ItemRepositoryImpl) { bind<ItemRepository>() }
    singleOf(::ItemMapperImpl)

    singleOf(::CategoryRepositoryImpl) { bind<CategoryRepository>() }
    singleOf(::CategoryMapperImpl)
}