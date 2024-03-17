package com.slowerror.tobuy.presentation.screens.home

data class HomeViewState(
    val dataItemList: List<DataItem<*>> = emptyList(),
    val isLoading: Boolean = false,
    val sortedBy: Sort = Sort.NONE
) {
    data class DataItem<T>(
        val data: T,
        val isHeader: Boolean = false
    )

    enum class Sort(val displayName: String) {
        NONE("NONE"),
        CATEGORY("CATEGORY"),
        OLDEST("OLDEST"),
        NEWEST("NEWEST")
    }
}
