package com.slowerror.tobuy.presentation.base

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.activityViewModel

abstract class BaseFragment : Fragment() {

    protected val sharedViewModel by activityViewModel<BaseViewModel>()

    protected fun navigateTo(actionId: Int) {
        findNavController().navigate(actionId)
    }

    protected fun navigateBack() {
        findNavController().popBackStack()
    }
}