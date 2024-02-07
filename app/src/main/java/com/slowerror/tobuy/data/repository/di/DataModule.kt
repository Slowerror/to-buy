package com.slowerror.tobuy.data.repository.di

import com.slowerror.tobuy.data.local.AppDatabase
import com.slowerror.tobuy.data.mapper.ItemMapperImpl
import com.slowerror.tobuy.data.repository.ToBuyRepositoryImpl
import com.slowerror.tobuy.domain.repository.ToBuyRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {

    single<AppDatabase> {
        AppDatabase.getInstance(context = get())
    }

    singleOf(::ToBuyRepositoryImpl) { bind<ToBuyRepository>() }
    singleOf(::ItemMapperImpl)
}