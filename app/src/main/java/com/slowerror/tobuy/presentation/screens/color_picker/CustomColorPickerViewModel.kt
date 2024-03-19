package com.slowerror.tobuy.presentation.screens.color_picker

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slowerror.tobuy.utils.SharedPrefUtil
import kotlin.math.roundToInt

class CustomColorPickerViewModel : ViewModel() {

    private lateinit var priorityName: String

    private val _viewStateLiveData = MutableLiveData<ColorPickerViewState>()
    val viewStateLiveData: LiveData<ColorPickerViewState>
        get() = _viewStateLiveData

    fun setPriority(priorityName: String, colorCallback: (Int, Int, Int) -> Unit) {
        this.priorityName = priorityName

        val colorInt = when (priorityName.lowercase()) {
            "low" -> SharedPrefUtil.getLowPriorityColor()
            "medium" -> SharedPrefUtil.getMediumPriorityColor()
            "high" -> SharedPrefUtil.getHighPriorityColor()
            else -> 0
        }

        val color = Color.valueOf(colorInt)

        val redValue = (color.red() * 255).roundToInt()
        val greenValue = (color.green() * 255).roundToInt()
        val blueValue = (color.blue() * 255).roundToInt()

        colorCallback(redValue, greenValue, blueValue)

        _viewStateLiveData.postValue(ColorPickerViewState(
            red = redValue,
            green = greenValue,
            blue = blueValue,
            priorityName = priorityName
        ))
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