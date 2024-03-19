package com.slowerror.tobuy.presentation.screens.color_picker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomColorPickerViewModel : ViewModel() {

    private lateinit var priorityName: String

    private val _viewStateLiveData = MutableLiveData<ColorPickerViewState>()
    val viewStateLiveData: LiveData<ColorPickerViewState>
        get() = _viewStateLiveData

    fun setPriority(priorityName: String) {
        this.priorityName = priorityName
    }

    fun onRedChange(red: Int) {
        val viewState = _viewStateLiveData.value ?: ColorPickerViewState()
        _viewStateLiveData.postValue(viewState.copy(red = red))
    }

    fun onGreenChange(green: Int) {
        val viewState = _viewStateLiveData.value ?: ColorPickerViewState()
        _viewStateLiveData.postValue(viewState.copy(green = green))
    }

    fun onBlueChange(blue: Int) {
        val viewState = _viewStateLiveData.value ?: ColorPickerViewState()
        _viewStateLiveData.postValue(viewState.copy(blue = blue))
    }
}