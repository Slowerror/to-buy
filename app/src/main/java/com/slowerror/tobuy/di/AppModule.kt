package com.slowerror.tobuy.di

import com.slowerror.tobuy.presentation.base.BaseViewModel
import com.slowerror.tobuy.presentation.screens.color_picker.CustomColorPickerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::BaseViewModel)
    viewModelOf(::CustomColorPickerViewModel)
}