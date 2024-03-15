package com.slowerror.tobuy.presentation.base

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.activityViewModel

abstract class BaseFragment : Fragment() {

    protected val sharedViewModel by activityViewModel<BaseViewModel>()

    protected fun navigateTo(actionId: Int) {
        findNavController().navigate(actionId)
    }

    protected fun navigateTo(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }

    protected fun navigateBack() {
        findNavController().popBackStack()
    }

    protected fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(InputMethodManager::class.java)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    protected fun showKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = requireContext().getSystemService(InputMethodManager::class.java)
            imm.showSoftInput(view, 0)
        }
    }

}