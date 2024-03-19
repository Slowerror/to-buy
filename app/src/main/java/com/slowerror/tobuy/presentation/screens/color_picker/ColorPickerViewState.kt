package com.slowerror.tobuy.presentation.screens.color_picker

data class ColorPickerViewState(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0,
    private val priorityName: String = ""
) {
    fun getFormattedTitle(): String = "$priorityName ($red, $green, $blue)"
}