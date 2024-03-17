package com.slowerror.tobuy.presentation.screens.home.bottom_sheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.slowerror.tobuy.databinding.BottomSheetSortOrderBinding
import com.slowerror.tobuy.presentation.base.BaseViewModel
import com.slowerror.tobuy.presentation.screens.home.HomeViewState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SortOrderBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetSortOrderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BaseViewModel by activityViewModel<BaseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSortOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("BottomSheet", "${HomeViewState.Sort.entries}")
        val epoxyController = BottomSheetController(HomeViewState.Sort.entries) {
            viewModel.currentSort = it
            dismiss()
        }

        binding.bottomSheetRw.setControllerAndBuildModels(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}