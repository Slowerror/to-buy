package com.slowerror.tobuy.di

import com.slowerror.tobuy.presentation.base.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::BaseViewModel)
}