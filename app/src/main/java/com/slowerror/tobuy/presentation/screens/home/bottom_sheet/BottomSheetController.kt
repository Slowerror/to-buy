package com.slowerror.tobuy.presentation.screens.home.bottom_sheet

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.ModelSortOrderItemBinding
import com.slowerror.tobuy.presentation.epoxy.ViewBindingKotlinModel
import com.slowerror.tobuy.presentation.screens.home.HomeViewState

class BottomSheetController(
    private val sortTypeList: List<HomeViewState.Sort>,
    private val selectedSortTypeCallback: (HomeViewState.Sort) -> Unit
) : EpoxyController() {


    override fun buildModels() {

        sortTypeList.forEach {
            SortItemEpoxyModel(it, selectedSortTypeCallback).id(it.displayName).addTo(this)
        }

    }

    data class SortItemEpoxyModel(
        val sortType: HomeViewState.Sort,
        val selectedSortTypeCallback: (HomeViewState.Sort) -> Unit
    ) : ViewBindingKotlinModel<ModelSortOrderItemBinding>(R.layout.model_sort_order_item) {
        override fun ModelSortOrderItemBinding.bind() {
            Log.i("BottomSheetController", "\n")
            Log.i("BottomSheetController", "$sortType")
            sortOrderTextView.text = sortType.toString()
            root.setOnClickListener {
                selectedSortTypeCallback(sortType)
            }
        }

    }
}