package com.slowerror.tobuy.data.repository.di

import com.slowerror.tobuy.presentation.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::BaseViewModel)
}